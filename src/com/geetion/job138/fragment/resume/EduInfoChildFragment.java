package com.geetion.job138.fragment.resume;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

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
import com.geetion.job138.model.EduExperience;
import com.geetion.job138.model.ResumeInfo;
import com.geetion.job138.model.WorkExperience;
import com.geetion.job138.service.CacheService;
import com.geetion.job138.service.PersonInfoSave;
import com.geetion.job138.service.ResumeManageService;
import com.geetion.job138.util.MyHttpException;
import com.geetion.job138.util.UIUtil;
import com.geetion.job138.util.UIUtil.OnSelectAlter;

public class EduInfoChildFragment extends BaseFragment {
	public static String TAG = EduInfoChildFragment.class.getName();
	private MyResumeActivity activity;
	private ImageButton btSave, btClose;
	private EduExperience eduExperience = new EduExperience();
	private GetEduInfoTask task;
	private TextView startDateView, endDateView, eduView, majorView;
	private EditText schoolView, profesView;
	private UpdateEduInfoTask updateTask;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_resume_eduinfo, container, false);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		activity = (MyResumeActivity) getActivity();
		init();
		getDate();
		super.onActivityCreated(savedInstanceState);
	}

	public void getDate() {
		Bundle bundle = getArguments();
		if (bundle != null) {
			eduExperience = (EduExperience) bundle.getSerializable("eduExperience");
			task = new GetEduInfoTask();
			task.execute();
		}
	}

	public void init() {
		majorView = (TextView) getView().findViewById(R.id.major);
		majorView.setOnClickListener(this);
		btClose = (ImageButton) getView().findViewById(R.id.close_button);
		btSave = (ImageButton) getView().findViewById(R.id.save_button);
		btSave.setOnClickListener(this);
		btClose.setOnClickListener(this);
		startDateView = (TextView) getView().findViewById(R.id.start_date);
		startDateView.setOnClickListener(this);
		endDateView = (TextView) getView().findViewById(R.id.end_date);
		endDateView.setOnClickListener(this);
		eduView = (TextView) getView().findViewById(R.id.education);
		eduView.setOnClickListener(this);
		schoolView = (EditText) getView().findViewById(R.id.school);
		profesView = (EditText) getView().findViewById(R.id.profession);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if (v == btClose) {
			activity.changeFragmentAnOnResume(EduInfoFragment.TAG, null);
		} else if (v == btSave) {
			if (getInfo()) {
				updateTask = new UpdateEduInfoTask();
				updateTask.execute();
			}
		} else if (v == eduView) {
			UIUtil.showSelectAlert(activity, CacheService.educKeyMap.values().toArray(new String[0]), new OnSelectAlter() {
				public void onSelect(String select) {
					eduView.setText(select);
					eduExperience.setEducation(String.valueOf(CacheService.educNameMap.get(select)));
				}
			});
		} else if (v == startDateView) {
			UIUtil.datePicker(activity, startDateView);
		} else if (v == endDateView) {
			UIUtil.datePicker(activity, endDateView);
		} else if (v == majorView) {
			Intent intent = new Intent(activity, ResumeContentActivity.class);
			intent.putExtra("title", "专业描述");
			intent.putExtra("content", eduExperience.getDetail());
			startActivityForResult(intent, 0);
		}
	}

	public boolean getInfo() {
		String startDate = startDateView.getText().toString();
		if (TextUtils.isEmpty(startDate)) {
			UIUtil.toast(activity, "开始日期不能为空");
			return false;
		}
		String school = schoolView.getText().toString();
		if (TextUtils.isEmpty(school)) {
			UIUtil.toast(activity, "学校名不能为空");
			return false;
		}
		String edu = eduView.getText().toString();
		if (TextUtils.isEmpty(edu)) {
			UIUtil.toast(activity, "学历必须选择");
			return false;
		}
		String endDate = endDateView.getText().toString();
		if (!TextUtils.isEmpty(endDate)) {
			String end[] = endDate.split("-");
			String endYear = end[0];
			String endMonth = end[1];
			eduExperience.setEndYear(endYear);
			eduExperience.setEndMonth(endMonth);
		} else {
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(new Date());
			eduExperience.setEndYear(String.valueOf(calendar.get(Calendar.YEAR)));
			eduExperience.setEndMonth(String.valueOf(calendar.get(Calendar.MONTH) + 1));
		}
		String date[] = startDate.split("-");
		String startYear = date[0];
		String startMonth = date[1];
		String profession = profesView.getText().toString();
		String detail = majorView.getText().toString();
		eduExperience.setStartYear(startYear);
		eduExperience.setStartMonth(startMonth);
		eduExperience.setProfession(profession);
		eduExperience.setDetail(detail);
		eduExperience.setSchool(school);
		eduExperience.setUserName(PersonInfoSave.resumeInfo.getUserName());
		eduExperience.setId(PersonInfoSave.resumeInfo.getId());
		return true;
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (resultCode) {
		case -1:
			String content = data.getStringExtra("content");
			majorView.setText(content);
			eduExperience.setDetail(content);
			break;

		default:
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

	public class GetEduInfoTask extends AsyncTask<Void, Integer, EduExperience> {
		protected void onPreExecute() {
			activity.showLoadiing();
			btSave.setClickable(false);
		};

		@Override
		protected EduExperience doInBackground(Void... arg0) {
			try {
				EduExperience edu = ResumeManageService.getEduExperienceDetail(eduExperience.getId(), eduExperience.getEduId());
				return edu;
			} catch (MyHttpException e) {
				Log.e("MyHttpException", e.getErrorCode());
				UIUtil.toast(activity, e.getErrorMessage());
			}
			return null;
		}

		@Override
		protected void onPostExecute(EduExperience result) {
			activity.hideLoading();
			btSave.setClickable(true);
			if (result != null) {
				startDateView.setText(result.getStartYear() + "-" + result.getStartMonth() + "-01");
				endDateView.setText(result.getEndYear() + "-" + result.getEndMonth() + "-01");
				schoolView.setText(result.getSchool());
				eduView.setText(CacheService.educKeyMap.get(Integer.parseInt(result.getEducation())));
				majorView.setText(result.getDetail());
				profesView.setText(result.getProfession());
				eduExperience = result;
			}
		}
	}

	public class UpdateEduInfoTask extends AsyncTask<Void, Integer, Integer> {
		protected void onPreExecute() {
			activity.showLoadiing();
			btSave.setClickable(false);
		};

		@Override
		protected Integer doInBackground(Void... arg0) {
			try {
				Integer result = ResumeManageService.newOrUpdateEduExperience(eduExperience);
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
				UIUtil.toast(activity, "添加修改教育经历成功");
				PersonInfoSave.updateSaveID(activity, result);
				activity.changeFragmentAnOnResume(EduInfoFragment.TAG, null);
			}
		}
	}
}
