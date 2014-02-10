package com.geetion.job138.fragment.resume;

import java.util.Calendar;
import java.util.Date;

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
import com.geetion.job138.fragment.BaseFragment;
import com.geetion.job138.fragment.resume.TrainInfoChildragment.GetTrainInfoTask;
import com.geetion.job138.model.Lang;
import com.geetion.job138.model.TrainExperience;
import com.geetion.job138.service.CacheService;
import com.geetion.job138.service.PersonInfoSave;
import com.geetion.job138.service.ResumeManageService;
import com.geetion.job138.util.MyHttpException;
import com.geetion.job138.util.UIUtil;
import com.geetion.job138.util.UIUtil.OnSelectAlter;

public class LanInfoChildFragment extends BaseFragment {
	public static String TAG = LanInfoChildFragment.class.getName();
	private MyResumeActivity activity;
	private ImageButton btSave, btClose;
	private TextView langView, langMasterView;
	private Lang lang = new Lang();
	private GetLangInfoTask task;
	private UpdateLangInfoTask updateTask;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_resume_languge, container, false);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		activity = (MyResumeActivity) getActivity();
		init();
		getDate();
		super.onActivityCreated(savedInstanceState);
	}

	public void init() {
		btClose = (ImageButton) getView().findViewById(R.id.close_button);
		btSave = (ImageButton) getView().findViewById(R.id.save_button);
		langView = (TextView) getView().findViewById(R.id.lang);
		langView.setOnClickListener(this);
		langMasterView = (TextView) getView().findViewById(R.id.lang_master);
		langMasterView.setOnClickListener(this);
		btSave.setOnClickListener(this);
		btClose.setOnClickListener(this);
	}

	public void getDate() {
		Bundle bundle = getArguments();
		if (bundle != null) {
			lang = (Lang) bundle.getSerializable("lang");
			task = new GetLangInfoTask();
			task.execute();
		}
	}

	public boolean getInfo() {
		String langStr = langView.getText().toString();
		if (TextUtils.isEmpty(langStr)) {
			UIUtil.toast(activity, "语言类型必须选择");
			return false;
		}
		String langMaster = langMasterView.getText().toString();
		if (TextUtils.isEmpty(langMaster)) {
			UIUtil.toast(activity, "掌握程度必须选择");
			return false;
		}
		lang.setName(langStr);
		lang.setMaster(String.valueOf(CacheService.langMasterNameMap.get(langMaster)));
		lang.setUserName(PersonInfoSave.resumeInfo.getUserName());
		lang.setId(PersonInfoSave.resumeInfo.getId());
		return true;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if (v == btClose) {
			activity.changeFragmentAnOnResume(LanInfoFragment.TAG, null);
		} else if (v == btSave) {
			if (getInfo()) {
				updateTask = new UpdateLangInfoTask();
				updateTask.execute();
			}
		} else if (v == langMasterView) {
			UIUtil.showSelectAlert(activity, CacheService.langMasterKeyMap.values().toArray(new String[0]), new OnSelectAlter() {
				public void onSelect(String select) {
					langMasterView.setText(select);
					lang.setMaster(String.valueOf(CacheService.langMasterNameMap.get(select)));
				}
			});
		} else if (v == langView) {
			UIUtil.showSelectAlert(activity, CacheService.langKeyMap.values().toArray(new String[0]), new OnSelectAlter() {
				public void onSelect(String select) {
					langView.setText(select);
					lang.setName(select);
				}
			});
		}
	}

	@Override
	public void onDestroy() {
		if (task != null)
			task.cancel(true);
		if (updateTask != null)
			updateTask.cancel(true);
		super.onDestroy();
	}

	public class GetLangInfoTask extends AsyncTask<Void, Integer, Lang> {
		protected void onPreExecute() {
			activity.showLoadiing();
			btSave.setClickable(false);
		};

		@Override
		protected Lang doInBackground(Void... arg0) {
			try {
				lang = ResumeManageService.getLangDetail(lang.getId(), lang.getLangId());
				return lang;
			} catch (MyHttpException e) {
				Log.e("MyHttpException", e.getErrorCode());
				UIUtil.toast(activity, e.getErrorMessage());
			}
			return null;
		}

		@Override
		protected void onPostExecute(Lang result) {
			activity.hideLoading();
			btSave.setClickable(true);
			if (result != null) {
				langView.setText(result.getName());
				langMasterView.setText(CacheService.langMasterKeyMap.get(Integer.parseInt(result.getMaster())));
			}
		}
	}

	public class UpdateLangInfoTask extends AsyncTask<Void, Integer, Integer> {
		protected void onPreExecute() {
			activity.showLoadiing();
			btSave.setClickable(false);
		};

		@Override
		protected Integer doInBackground(Void... arg0) {
			try {
				Integer result = ResumeManageService.newOrUpdateLang(lang);
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
			btSave.setClickable(true);
			if (result != null) {
				UIUtil.toast(activity, "添加修改语言能力成功");
				PersonInfoSave.updateSaveID(activity, result);
				activity.changeFragmentAnOnResume(LanInfoFragment.TAG, null);
			}
		}
	}

}
