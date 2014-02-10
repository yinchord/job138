package com.geetion.job138.fragment.resume;

import java.util.Calendar;
import java.util.Date;

import android.content.Intent;
import android.os.AsyncTask;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.liqi.job.R;
import com.geetion.job138.activity.MyResumeActivity;
import com.geetion.job138.activity.ResumeContentActivity;
import com.geetion.job138.fragment.BaseFragment;
import com.geetion.job138.fragment.resume.EduInfoChildFragment.GetEduInfoTask;
import com.geetion.job138.model.EduExperience;
import com.geetion.job138.model.WorkExperience;
import com.geetion.job138.service.CacheService;
import com.geetion.job138.service.PersonInfoSave;
import com.geetion.job138.service.ResumeManageService;
import com.geetion.job138.util.MyHttpException;
import com.geetion.job138.util.UIUtil;
import com.geetion.job138.util.UIUtil.OnSelectAlter;

public class JobInfoChildFragment extends BaseFragment {
	public static String TAG = JobInfoChildFragment.class.getName();
	private MyResumeActivity activity;
	private ImageButton btSave, btClose;
	private WorkExperience workExperience = new WorkExperience();
	private GetWorkInfoTask task;
	private UpdateWorkInfoTask updateTask;
	private TextView startDateView, endDateView, workersView, ecoclassView, introduceView;
	private EditText comView, positionView;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_resume_workexperience, container, false);
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
		btSave.setOnClickListener(this);
		btClose.setOnClickListener(this);
		startDateView = (TextView) getView().findViewById(R.id.start_date);
		startDateView.setOnClickListener(this);
		endDateView = (TextView) getView().findViewById(R.id.end_date);
		endDateView.setOnClickListener(this);
		workersView = (TextView) getView().findViewById(R.id.workers);
		workersView.setOnClickListener(this);
		ecoclassView = (TextView) getView().findViewById(R.id.ecoclass);
		ecoclassView.setOnClickListener(this);
		comView = (EditText) getView().findViewById(R.id.com_name);
		positionView = (EditText) getView().findViewById(R.id.position);
		introduceView = (TextView) getView().findViewById(R.id.introduce);
		introduceView.setOnClickListener(this);
	}

	public void getDate() {
		Bundle bundle = getArguments();
		if (bundle != null) {
			workExperience = (WorkExperience) bundle.getSerializable("workExperience");
			task = new GetWorkInfoTask();
			task.execute();
		}
	}

	public boolean getInfo() {
		String startDate = startDateView.getText().toString();
		if (TextUtils.isEmpty(startDate)) {
			UIUtil.toast(activity, "开始日期不能为空");
			return false;
		}
		String endDate = endDateView.getText().toString();
		if (TextUtils.isEmpty(endDate)) {
			UIUtil.toast(activity, "结束日期不能为空");
			return false;
		}
		String comName = comView.getText().toString();
		if (TextUtils.isEmpty(comName)) {
			UIUtil.toast(activity, "公司名称不能为空");
			return false;
		}
		String position = positionView.getText().toString();
		if (TextUtils.isEmpty(position)) {
			UIUtil.toast(activity, "担任职务不能为空");
			return false;
		}
		String introduce = introduceView.getText().toString();
		String ecoclass = ecoclassView.getText().toString();
		String workers = workersView.getText().toString();
		String[] date = startDate.split("-");
		String startYear = date[0];
		String startMonth = date[1];
		date = endDate.split("-");
		String endYear = date[0];
		String endMonth = date[1];
		workExperience.setComName(comName);
		workExperience.setPosition(position);
		workExperience.setIntroduce(introduce);
		workExperience.setEcoclass(ecoclass);
		workExperience.setWorkers(workers);
		workExperience.setStartMonth(startMonth);
		workExperience.setStartYear(startYear);
		workExperience.setEndMonth(endMonth);
		workExperience.setEndYear(endYear);
		workExperience.setUserName(PersonInfoSave.resumeInfo.getUserName());
		workExperience.setId(PersonInfoSave.resumeInfo.getId());
		return true;
	}

	@Override
	public void onClick(View v) {
		if (v == btClose) {
			activity.changeFragmentAnOnResume(JobInfoFragment.TAG, null);
		} else if (v == btSave) {
			if (getInfo()) {
				updateTask = new UpdateWorkInfoTask();
				updateTask.execute();
			}
		} else if (v == startDateView) {
			UIUtil.datePicker(activity, startDateView);
		} else if (v == endDateView) {
			UIUtil.datePicker(activity, endDateView);
		} else if (v == workersView) {
			UIUtil.showSelectAlert(activity, CacheService.workersKeyMap.values().toArray(new String[0]), new OnSelectAlter() {
				public void onSelect(String select) {
					workersView.setText(select);
					workExperience.setWorkers(select);
				}
			});
		} else if (v == ecoclassView) {
			UIUtil.showSelectAlert(activity, CacheService.ecoclassKeyMap.values().toArray(new String[0]), new OnSelectAlter() {
				public void onSelect(String select) {
					ecoclassView.setText(select);
					workExperience.setEcoclass(select);
				}
			});
		} else if (v == introduceView) {
			Intent intent = new Intent(activity, ResumeContentActivity.class);
			intent.putExtra("title", "工作描述");
			intent.putExtra("content", workExperience.getIntroduce());
			startActivityForResult(intent, 0);
		}
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (resultCode) {
		case -1:
			String content = data.getStringExtra("content");
			introduceView.setText(content);
			workExperience.setIntroduce(content);
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

	public class GetWorkInfoTask extends AsyncTask<Void, Integer, WorkExperience> {
		protected void onPreExecute() {
			activity.showLoadiing();
			btSave.setClickable(false);
		};

		@Override
		protected WorkExperience doInBackground(Void... arg0) {
			try {
				WorkExperience work = ResumeManageService.getWorkExorienceDetail(workExperience.getId(), workExperience.getWorkId());
				return work;
			} catch (MyHttpException e) {
				Log.e("MyHttpException", e.getErrorCode());
				UIUtil.toast(activity, e.getErrorMessage());
			}
			return null;
		}

		@Override
		protected void onPostExecute(WorkExperience result) {
			activity.hideLoading();
			btSave.setClickable(true);
			if (result != null) {
				startDateView.setText(result.getStartYear() + "-" + result.getStartMonth() + "-01");
				endDateView.setText(result.getEndYear() + "-" + result.getEndMonth() + "-01");
				comView.setText(result.getComName());
				workersView.setText(result.getWorkers());
				ecoclassView.setText(result.getEcoclass());
				positionView.setText(result.getPosition());
				introduceView.setText(result.getIntroduce());
				workExperience = result;
			}
		}
	}

	public class UpdateWorkInfoTask extends AsyncTask<Void, Integer, Integer> {
		protected void onPreExecute() {
			activity.showLoadiing();
			btSave.setClickable(false);
		};

		@Override
		protected Integer doInBackground(Void... arg0) {
			try {
				Integer result = ResumeManageService.newOrUpdateWorkExperience(workExperience);
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
				UIUtil.toast(activity, "添加修改工作经历成功");
				PersonInfoSave.updateSaveID(activity, result);
				activity.changeFragmentAnOnResume(JobInfoFragment.TAG, null);
			}
		}
	}
}
