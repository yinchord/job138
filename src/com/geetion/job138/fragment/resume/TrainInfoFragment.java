package com.geetion.job138.fragment.resume;

import java.util.ArrayList;
import java.util.List;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;

import com.liqi.job.R;
import com.geetion.job138.activity.MyResumeActivity;
import com.geetion.job138.adapter.JobInfoAdpter;
import com.geetion.job138.adapter.TrainInfoAdpter;
import com.geetion.job138.fragment.BaseFragment;
import com.geetion.job138.fragment.resume.EduInfoFragment.GetEduInfoTask;
import com.geetion.job138.model.EduExperience;
import com.geetion.job138.model.JobInfo;
import com.geetion.job138.model.Lang;
import com.geetion.job138.model.TrainExperience;
import com.geetion.job138.service.PersonInfoSave;
import com.geetion.job138.service.ResumeManageService;
import com.geetion.job138.util.MyHttpException;
import com.geetion.job138.util.UIUtil;

public class TrainInfoFragment extends BaseFragment {

	public static String TAG = TrainInfoFragment.class.getName();
	private MyResumeActivity activity;
	private ImageButton closeButton;
	private ListView listView;
	private Button btAdd;
	private TrainInfoAdpter trainInfoAdpter;
	private GetTrainInfoTask task;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_resume_global_list, container, false);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		activity = (MyResumeActivity) getActivity();
		init();
		super.onActivityCreated(savedInstanceState);
	}

	public void init() {
		View footerView = getActivity().getLayoutInflater().inflate(R.layout.button_add, null);
		listView = (ListView) getView().findViewById(R.id.listView_jobInfo);
		listView.addFooterView(footerView);
		closeButton = (ImageButton) getView().findViewById(R.id.close_button);
		btAdd = (Button) getView().findViewById(R.id.btAdd);
		btAdd.setOnClickListener(this);
		closeButton.setOnClickListener(this);
		trainInfoAdpter = new TrainInfoAdpter(activity, PersonInfoSave.resumeInfo.getTrainList());
		listView.setAdapter(trainInfoAdpter);
		if (PersonInfoSave.resumeInfo.getId() != 0) {
			task = new GetTrainInfoTask();
			task.execute();
		}
	}

	@Override
	public void onDestroy() {
		if (task != null)
			task.cancel(true);
		super.onDestroy();
	}

	@Override
	public void onClick(View v) {
		if (v == closeButton) {
			activity.hideSelectFragment();
		} else if (v == btAdd) {
			activity.changeFragmentAnOnResume(TrainInfoChildragment.TAG, null);
		}
	}

	public class GetTrainInfoTask extends AsyncTask<Void, Integer, List<TrainExperience>> {
		protected void onPreExecute() {
			activity.showLoadiing();
		};

		@Override
		protected List<TrainExperience> doInBackground(Void... arg0) {
			try {
				List<TrainExperience> list = ResumeManageService.getTraiinExperienceList(PersonInfoSave.resumeInfo.getId());
				return list;
			} catch (MyHttpException e) {
				Log.e("MyHttpException", e.getErrorCode());
				UIUtil.toast(activity, e.getErrorMessage());
			}
			return null;
		}

		@Override
		protected void onPostExecute(List<TrainExperience> result) {
			activity.hideLoading();
			if (result != null) {
				PersonInfoSave.saveTrainList(result);
				trainInfoAdpter.notifyDataSetChanged();
			}
		}
	}
}
