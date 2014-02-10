package com.geetion.job138.fragment.resume;

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
import com.geetion.job138.adapter.EduInfoAdapter;
import com.geetion.job138.fragment.BaseFragment;
import com.geetion.job138.model.EduExperience;
import com.geetion.job138.model.ResumeInfo;
import com.geetion.job138.service.PersonInfoSave;
import com.geetion.job138.service.ResumeManageService;
import com.geetion.job138.util.MyHttpException;
import com.geetion.job138.util.UIUtil;

public class EduInfoFragment extends BaseFragment {
	public static String TAG = EduInfoFragment.class.getName();
	private MyResumeActivity activity;
	private ImageButton closeButton;
	private Button btAdd;
	private ListView listView;
	private EduInfoAdapter eduInfoAdapter;
	private GetEduInfoTask task;

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
		eduInfoAdapter = new EduInfoAdapter(activity, PersonInfoSave.resumeInfo.getEduList());
		listView.setAdapter(eduInfoAdapter);
		if (PersonInfoSave.resumeInfo.getId() != 0) {
			task = new GetEduInfoTask();
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
	public void onResume() {
		eduInfoAdapter.notifyDataSetChanged();
		super.onResume();
	}

	@Override
	public void onClick(View v) {
		if (v == closeButton) {
			activity.hideSelectFragment();
		} else if (v == btAdd) {
			activity.changeFragmentAnOnResume(EduInfoChildFragment.TAG, null);
		}
	}

	public class GetEduInfoTask extends AsyncTask<Void, Integer, List<EduExperience>> {
		protected void onPreExecute() {
			activity.showLoadiing();
		};

		@Override
		protected List<EduExperience> doInBackground(Void... arg0) {
			try {
				List<EduExperience> list = ResumeManageService.getEduExperienceList(PersonInfoSave.resumeInfo.getId());
				return list;
			} catch (MyHttpException e) {
				Log.e("MyHttpException", e.getErrorCode());
				UIUtil.toast(activity, e.getErrorMessage());
			}
			return null;
		}

		@Override
		protected void onPostExecute(List<EduExperience> result) {
			activity.hideLoading();
			if (result != null) {
				PersonInfoSave.saveEduList(result);
				eduInfoAdapter.notifyDataSetChanged();
			}
		}
	}
}
