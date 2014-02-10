package com.geetion.job138.fragment.resume;

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
import com.geetion.job138.model.ResumeInfo;
import com.geetion.job138.service.PersonInfoSave;
import com.geetion.job138.service.ResumeManageService;
import com.geetion.job138.util.MyHttpException;
import com.geetion.job138.util.UIUtil;

public class MyContentInfoFragment extends BaseFragment {
	public static String TAG = MyContentInfoFragment.class.getName();
	private MyResumeActivity activity;
	private ImageButton closeButton, saveButton;
	private ResumeInfo updateResume = new ResumeInfo();
	private GetAppraiseTask task;
	public static boolean isGetData = false;
	private TextView sumupView, appraiseView;
	private boolean isSumup = false;
	private UpdateAppraiseTask update;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_resume_mycontentinfo, container, false);
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
		saveButton = (ImageButton) getView().findViewById(R.id.save_button);
		saveButton.setOnClickListener(this);
		updateResume.setId(PersonInfoSave.resumeInfo.getId());
		updateResume.setUserName(PersonInfoSave.resumeInfo.getUserName());
		sumupView = (TextView) getView().findViewById(R.id.sumup);
		sumupView.setOnClickListener(this);
		appraiseView = (TextView) getView().findViewById(R.id.appraise);
		appraiseView.setOnClickListener(this);
		if (PersonInfoSave.resumeInfo.getId() != 0) {
			if (!isGetData) {
				task = new GetAppraiseTask();
				task.execute();
			} else {
				updateResume.saveAppraise(PersonInfoSave.resumeInfo);
				resetResumeInfo();
			}
		}
	}

	public boolean getUpdateInfo() {
		String sump = sumupView.getText().toString();
		if (TextUtils.isEmpty(sump)) {
			UIUtil.toast(activity, "职场签名不能为空");
			return false;
		}
		String appraise = appraiseView.getText().toString();
		if (TextUtils.isEmpty(appraise)) {
			UIUtil.toast(activity, "自我评价不能为空");
			return false;
		}
		return true;
	}

	@Override
	public void onDestroy() {
		if (task != null)
			task.cancel(true);
		if (update != null)
			update.cancel(true);
		super.onDestroy();
	}

	@Override
	public void onClick(View v) {
		if (v == closeButton) {
			activity.hideSelectFragment();
		} else if (v == sumupView) {
			isSumup = true;
			Intent intent = new Intent(activity, ResumeContentActivity.class);
			intent.putExtra("title", "职场签名");
			intent.putExtra("content", updateResume.getSumup());
			startActivityForResult(intent, 0);
		} else if (v == appraiseView) {
			isSumup = false;
			Intent intent = new Intent(activity, ResumeContentActivity.class);
			intent.putExtra("title", "自我评价");
			intent.putExtra("content", updateResume.getAppraise());
			startActivityForResult(intent, 0);
		} else if (v == saveButton) {
			if (getUpdateInfo()) {
				update = new UpdateAppraiseTask();
				update.execute();
			}
		}
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (resultCode) {
		case -1:
			String content = data.getStringExtra("content");
			if (isSumup) {
				sumupView.setText(content);
				updateResume.setSumup(content);
			} else {
				appraiseView.setText(content);
				updateResume.setAppraise(content);
			}
			break;
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	public void resetResumeInfo() {
		sumupView.setText(PersonInfoSave.resumeInfo.getSumup());
		appraiseView.setText(PersonInfoSave.resumeInfo.getAppraise());
	}

	public class GetAppraiseTask extends AsyncTask<Void, Integer, ResumeInfo> {
		protected void onPreExecute() {
			saveButton.setClickable(false);
			activity.showLoadiing();
		};

		@Override
		protected ResumeInfo doInBackground(Void... arg0) {
			try {
				ResumeInfo resumeInfo = ResumeManageService.getAppraise(String.valueOf(PersonInfoSave.resumeInfo.getId()));
				return resumeInfo;
			} catch (MyHttpException e) {
				Log.e("MyHttpException", e.getErrorCode());
				UIUtil.toast(activity, e.getErrorMessage());
			}
			return null;
		}

		@Override
		protected void onPostExecute(ResumeInfo result) {
			activity.hideLoading();
			saveButton.setClickable(true);
			if (result != null) {
				PersonInfoSave.saveResumeAppraise(result);
				updateResume = result;
				updateResume.setId(PersonInfoSave.resumeInfo.getId());
				resetResumeInfo();
				isGetData = true;
			}
		}
	}

	public class UpdateAppraiseTask extends AsyncTask<Void, Integer, Integer> {
		protected void onPreExecute() {
			saveButton.setClickable(false);
			activity.showLoadiing();
		};

		@Override
		protected Integer doInBackground(Void... arg0) {
			try {
				Integer result = ResumeManageService.updateAppraise(updateResume);
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
				updateResume.setId(result);
				PersonInfoSave.saveResumeAppraise(updateResume);
				UIUtil.toast(activity, "修改成功");
				TextView eduView=(TextView)activity.findViewById(R.id.edu_info_show);
				activity.changeFragmentAnOnResume(EduInfoFragment.TAG, null);
				activity.cleanAndSelect(R.id.edu_info, eduView);
			}
		}
	}
}
