package com.geetion.job138.fragment.resume;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import com.liqi.job.R;
import com.geetion.job138.activity.MyResumeActivity;
import com.geetion.job138.activity.ResumeContentActivity;
import com.geetion.job138.fragment.BaseFragment;
import com.geetion.job138.model.Ability;
import com.geetion.job138.model.ResumeInfo;
import com.geetion.job138.service.PersonInfoSave;
import com.geetion.job138.service.ResumeManageService;
import com.geetion.job138.util.MyHttpException;
import com.geetion.job138.util.UIUtil;
import com.geetion.job138.widget.custom.SelectCertificatePop;
import com.geetion.job138.widget.custom.SelectCertificatePop.PopSelectInterface;

public class AbilityInfoFragment extends BaseFragment {

	public static String TAG = AbilityInfoFragment.class.getName();
	private ImageButton closeButton, saveButton;
	private TextView certificateView, developmentView, techView;
	private MyResumeActivity activity;
	private SelectCertificatePop popWindow;
	private List<String> selectList = new ArrayList<String>();
	public static boolean isGetData = false;
	public Ability updateAbility = new Ability();
	private GetAbilityInfoTask task;
	private UpdateAbilityInfoTask updateTask;
	private boolean keyType;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_resume_abilityinfo, container, false);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		activity = (MyResumeActivity) getActivity();
		init();
		super.onActivityCreated(savedInstanceState);
	}

	public void init() {
		closeButton = (ImageButton) getView().findViewById(R.id.close_button);
		closeButton.setOnClickListener(this);
		certificateView = (TextView) getView().findViewById(R.id.certificate);
		certificateView.setOnClickListener(this);
		developmentView = (TextView) getView().findViewById(R.id.development);
		developmentView.setOnClickListener(this);
		techView = (TextView) getView().findViewById(R.id.techniques);
		techView.setOnClickListener(this);
		saveButton = (ImageButton) getView().findViewById(R.id.save_button);
		saveButton.setOnClickListener(this);
		LayoutInflater inflater = activity.getLayoutInflater();
		View popView = inflater.inflate(R.layout.window_certificate, null);
		popWindow = new SelectCertificatePop(popView, activity);
		popWindow.setPopSelectInterface(new PopSelectInterface() {
			@Override
			public void onSelect(List<String> checkList) {
				selectList.clear();
				selectList.addAll(checkList);
				if (!checkList.isEmpty()) {
					String selectName = "";
					for (int i = 0; i < checkList.size(); i++) {
						if (i == checkList.size() - 1) {
							selectName += checkList.get(i);
						} else {
							selectName += checkList.get(i) + "、";
						}
						Log.e("select", checkList.get(i));
					}
					certificateView.setText(selectName);
					updateAbility.setCertificate(selectName);
				} else {
					certificateView.setText("");
				}
			}
		});
		if (PersonInfoSave.resumeInfo.getId() != 0) {
			if (!isGetData)
				getData();
			else {
				updateAbility.setCertificate(PersonInfoSave.resumeInfo.getAbility().getCertificate());
				updateAbility.setDevelopment(PersonInfoSave.resumeInfo.getAbility().getDevelopment());
				updateAbility.setId(PersonInfoSave.resumeInfo.getAbility().getId());
				updateAbility.setTechniques(PersonInfoSave.resumeInfo.getAbility().getTechniques());
				resetInfo();
			}
		}
	}

	public void getData() {
		task = new GetAbilityInfoTask();
		task.execute();
	}

	public void resetInfo() {
		certificateView.setText(PersonInfoSave.resumeInfo.getAbility().getCertificate());
		developmentView.setText(PersonInfoSave.resumeInfo.getAbility().getDevelopment());
		techView.setText(PersonInfoSave.resumeInfo.getAbility().getTechniques());
		if (!TextUtils.isEmpty(PersonInfoSave.resumeInfo.getAbility().getCertificate())) {
			String[] selectStr = PersonInfoSave.resumeInfo.getAbility().getCertificate().split("、");
			selectList.clear();
			for (int i = 0; i < selectStr.length; i++) {
				selectList.add(selectStr[i]);
			}
		}
	}

	public void getInfo() {
		String certificate = certificateView.getText().toString();
		String development = developmentView.getText().toString();
		String techniques = techView.getText().toString();
		updateAbility.setCertificate(certificate);
		updateAbility.setDevelopment(development);
		updateAbility.setTechniques(techniques);
	}

	@Override
	public void onClick(View v) {
		if (v == closeButton) {
			activity.hideSelectFragment();
		} else if (v == certificateView) {
			popWindow.setCheckList(selectList);
			popWindow.showPop(certificateView);
		} else if (v == saveButton) {
			getInfo();
			updateTask = new UpdateAbilityInfoTask();
			updateTask.execute();
		} else if (v == developmentView) {
			keyType = true;
			Intent intent = new Intent(activity, ResumeContentActivity.class);
			intent.putExtra("title", "发展方向");
			intent.putExtra("content", updateAbility.getDevelopment());
			startActivityForResult(intent, 0);
		} else if (v == techView) {
			keyType = false;
			Intent intent = new Intent(activity, ResumeContentActivity.class);
			intent.putExtra("title", "专业描述");
			intent.putExtra("content", updateAbility.getTechniques());
			startActivityForResult(intent, 0);
		}
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (resultCode) {
		case -1:
			if (keyType) {
				String content = data.getStringExtra("content");
				developmentView.setText(content);
				updateAbility.setDevelopment(content);
			} else {
				String content = data.getStringExtra("content");
				techView.setText(content);
				updateAbility.setTechniques(content);
			}
			break;
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	@Override
	public void onDestroy() {
		if (task != null)
			task.cancel(true);
		if (updateTask != null)
			updateTask.cancel(true);
		super.onDestroy();
	}

	public class GetAbilityInfoTask extends AsyncTask<Void, Integer, Ability> {
		protected void onPreExecute() {
			saveButton.setClickable(false);
			activity.showLoadiing();
		};

		@Override
		protected Ability doInBackground(Void... arg0) {
			try {
				Ability ability = ResumeManageService.getAbility(PersonInfoSave.resumeInfo.getId());
				return ability;
			} catch (MyHttpException e) {
				Log.e("MyHttpException", e.getErrorCode());
				UIUtil.toast(activity, e.getErrorMessage());
			}
			return null;
		}

		@Override
		protected void onPostExecute(Ability result) {
			activity.hideLoading();
			saveButton.setClickable(true);
			if (result != null) {
				isGetData = true;
				PersonInfoSave.resumeInfo.setAbility(result);
				updateAbility.setCertificate(result.getCertificate());
				updateAbility.setDevelopment(result.getDevelopment());
				updateAbility.setId(result.getId());
				updateAbility.setTechniques(result.getTechniques());
				resetInfo();
			}
		}
	}

	public class UpdateAbilityInfoTask extends AsyncTask<Void, Integer, Integer> {
		protected void onPreExecute() {
			saveButton.setClickable(false);
			activity.showLoadiing();
		};

		@Override
		protected Integer doInBackground(Void... arg0) {
			try {
				Integer result = ResumeManageService.updateAbility(updateAbility);
				return result;
			} catch (MyHttpException e) {
				Log.e("MyHttpException", e.getErrorCode());
				UIUtil.toast(activity, e.getErrorMessage());
			}
			return null;
		}

		@Override
		protected void onPostExecute(Integer result) {
			activity.hideLoading();
			saveButton.setClickable(true);
			if (result != null) {
				PersonInfoSave.updateSaveID(activity, result);
				PersonInfoSave.resumeInfo.setAbility(updateAbility);
				UIUtil.toast(activity, "修改成功");
				TextView lanView = (TextView) activity.findViewById(R.id.language_info_show);
				activity.changeFragmentAnOnResume(LanInfoFragment.TAG, null);
				activity.cleanAndSelect(R.id.language_info, lanView);

			}
		}
	}

}
