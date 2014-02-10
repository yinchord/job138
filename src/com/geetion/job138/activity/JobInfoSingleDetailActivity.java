package com.geetion.job138.activity;

import com.liqi.job.R;
import com.geetion.job138.fragment.PosDetailFragment;
import com.geetion.job138.model.JobInfo;
import com.geetion.job138.service.JobInfoService;
import com.geetion.job138.util.MyHttpException;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.TextView;

public class JobInfoSingleDetailActivity extends BaseActivity implements
		OnClickListener {
	private ImageButton backButton;
	private TextView title;
	private FragmentManager fm;
	private FragmentTransaction tr;
	private AsyncTask myTask;
	private JobInfo jobInfo;
	private PosDetailFragment posDetailFragment;
	private int id;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.activity_jobinfo_single);
		initView();
		initData();
	}

	@Override
	protected void onDestroy() {
		if (myTask != null) {
			myTask.cancel(true);
		}
		super.onDestroy();
	}

	private void initView() {
		backButton = (ImageButton) findViewById(R.id.button_back);
		title = (TextView) findViewById(R.id.title);
		backButton.setOnClickListener(this);

	}

	private void initData() {
		id = getIntent().getIntExtra("id", 0);
		jobInfo = (JobInfo) getIntent().getSerializableExtra("jobInfo");
		myTask = new MyTask().execute();
	}

	class MyTask extends AsyncTask {
		@Override
		protected void onPreExecute() {
			showLoadiing();
			super.onPreExecute();
		}

		@Override
		protected Object doInBackground(Object... params) {
			try {
				if (jobInfo == null)
					jobInfo = JobInfoService.getJobInfo(1, id);
				jobInfo.setLable(JobInfoService.getMapLable(1, jobInfo.getCompanyId()));
			} catch (MyHttpException e) {
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPostExecute(Object result) {
			hideLoading();
			title.setText(jobInfo.getCompanyName());
			fm = getSupportFragmentManager();
			tr = fm.beginTransaction();
			Bundle bundle = new Bundle();
			bundle.putSerializable("jobInfo", jobInfo);
			posDetailFragment = new PosDetailFragment();
			posDetailFragment.setArguments(bundle);
			tr.replace(R.id.content, posDetailFragment);
			tr.commitAllowingStateLoss();
		}
	}

	@Override
	public void onClick(View v) {
		if (v == backButton) {
			finish();
		}
	}
}
