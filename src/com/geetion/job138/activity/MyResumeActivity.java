package com.geetion.job138.activity;

import com.liqi.job.R;
import com.geetion.job138.fragment.IndexFragment;
import com.geetion.job138.fragment.LoginFragment;
import com.geetion.job138.fragment.PersonalCenterFragment;
import com.geetion.job138.fragment.RandomFragment;
import com.geetion.job138.fragment.SearchFragment;
import com.geetion.job138.fragment.SettingFragment;
import com.geetion.job138.fragment.resume.AbilityInfoFragment;
import com.geetion.job138.fragment.resume.BaseInfoFragment;
import com.geetion.job138.fragment.resume.ContactInfoFragment;
import com.geetion.job138.fragment.resume.EduInfoChildFragment;
import com.geetion.job138.fragment.resume.EduInfoFragment;
import com.geetion.job138.fragment.resume.JobInfoChildFragment;
import com.geetion.job138.fragment.resume.JobInfoFragment;
import com.geetion.job138.fragment.resume.JobIntentFragment;
import com.geetion.job138.fragment.resume.LanInfoChildFragment;
import com.geetion.job138.fragment.resume.LanInfoFragment;
import com.geetion.job138.fragment.resume.MyContentInfoFragment;
import com.geetion.job138.fragment.resume.TrainInfoChildragment;
import com.geetion.job138.fragment.resume.TrainInfoFragment;
import com.geetion.job138.model.ResumeBaseInfo;
import com.geetion.job138.model.ResumeInfo;
import com.geetion.job138.service.PersonInfoSave;
import com.geetion.job138.service.ResumeInfoService;
import com.geetion.job138.util.MyHttpException;
import com.geetion.job138.util.UIUtil;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

public class MyResumeActivity extends BaseActivity {
	private FragmentManager fm;
	private FragmentTransaction tr;
	private Fragment fragment;
	private String TAG;
	private int[] viewIDs = { R.id.base_info, R.id.contact_info, R.id.ability_info, R.id.job_info, R.id.job_intent, R.id.edu_info, R.id.train_info,
			R.id.my_content_info, R.id.language_info };
	private TextView baseInfoView, contactInfoView, abilityInfoView, jobInfoView, jobIntentView, eduInfoView, trainInfoView, myContentInfoView, langInfoView,
			percentView;
	private Handler handler = new Handler();
	private ImageButton returnButton;
	private GetResumeBaseInfoTask task;

	@Override
	protected void onCreate(Bundle arg0) {
		setContentView(R.layout.activity_myresume);
		super.onCreate(arg0);
		init();
	}

	public void init() {
		setTitleOnClick();
		baseInfoView = (TextView) findViewById(R.id.base_info_show);
		baseInfoView.setOnClickListener(this);
		contactInfoView = (TextView) findViewById(R.id.contact_info_show);
		contactInfoView.setOnClickListener(this);
		abilityInfoView = (TextView) findViewById(R.id.ability_info_show);
		abilityInfoView.setOnClickListener(this);
		jobInfoView = (TextView) findViewById(R.id.job_info_show);
		jobInfoView.setOnClickListener(this);
		jobIntentView = (TextView) findViewById(R.id.job_intent_show);
		jobIntentView.setOnClickListener(this);
		eduInfoView = (TextView) findViewById(R.id.edu_info_show);
		eduInfoView.setOnClickListener(this);
		trainInfoView = (TextView) findViewById(R.id.train_info_show);
		trainInfoView.setOnClickListener(this);
		myContentInfoView = (TextView) findViewById(R.id.my_contant_show);
		myContentInfoView.setOnClickListener(this);
		langInfoView = (TextView) findViewById(R.id.language_info_show);
		langInfoView.setOnClickListener(this);
		returnButton = (ImageButton) findViewById(R.id.button_left);
		returnButton.setOnClickListener(this);
		percentView = (TextView) findViewById(R.id.percent);
		if (PersonInfoSave.resumeInfo.getId() == 0) {
			changeFragmentAnOnResume(BaseInfoFragment.TAG, null);
			cleanAndSelect(R.id.base_info, baseInfoView);
		} else {
			percentView.setText("(" + PersonInfoSave.resumeInfo.getPercent() + "%)");
			task = new GetResumeBaseInfoTask();
			task.execute();
		}
	}

	@Override
	public void onClick(View v) {
		int id = v.getId();
		if (id == R.id.base_info || id == R.id.base_info_show) {
			changeFragmentAnOnResume(BaseInfoFragment.TAG, null);
			cleanAndSelect(R.id.base_info, baseInfoView);
		} else if (id == R.id.contact_info || id == R.id.contact_info_show) {
			changeFragmentAnOnResume(ContactInfoFragment.TAG, null);
			cleanAndSelect(R.id.contact_info, contactInfoView);
		} else if (id == R.id.ability_info || id == R.id.ability_info_show) {
			changeFragmentAnOnResume(AbilityInfoFragment.TAG, null);
			cleanAndSelect(R.id.ability_info, abilityInfoView);
		} else if (id == R.id.job_intent || id == R.id.job_intent_show) {
			changeFragmentAnOnResume(JobIntentFragment.TAG, null);
			cleanAndSelect(R.id.job_intent, jobIntentView);
		} else if (id == R.id.job_info || id == R.id.job_info_show) {
			changeFragmentAnOnResume(JobInfoFragment.TAG, null);
			cleanAndSelect(R.id.job_info, jobInfoView);
		} else if (id == R.id.my_content_info || id == R.id.my_contant_show) {
			changeFragmentAnOnResume(MyContentInfoFragment.TAG, null);
			cleanAndSelect(R.id.my_content_info, myContentInfoView);
		} else if (id == R.id.train_info || id == R.id.train_info_show) {
			changeFragmentAnOnResume(TrainInfoFragment.TAG, null);
			cleanAndSelect(R.id.train_info, trainInfoView);
		} else if (id == R.id.edu_info || id == R.id.edu_info_show) {
			changeFragmentAnOnResume(EduInfoFragment.TAG, null);
			cleanAndSelect(R.id.edu_info, eduInfoView);
		} else if (id == R.id.language_info || id == R.id.language_info_show) {
			changeFragmentAnOnResume(LanInfoFragment.TAG, null);
			cleanAndSelect(R.id.language_info, langInfoView);
		} else if (v == returnButton) {
			finish();
		}
	}

	public void setTitleOnClick() {
		for (int i = 0; i < viewIDs.length; i++) {
			TextView textView = (TextView) findViewById(viewIDs[i]);
			textView.setOnClickListener(this);
		}
	}

	public void cleanAndSelect(int selectID, TextView selectView) {
		cleanAll();
		TextView textView = (TextView) findViewById(selectID);
		textView.setBackgroundResource(R.color.resume_bg);
		selectView.setBackgroundResource(R.color.resume_bg);
	}

	public void cleanAll() {
		for (int i = 0; i < viewIDs.length; i++) {
			TextView textView = (TextView) findViewById(viewIDs[i]);
			textView.setBackgroundResource(R.color.bg);
		}
		baseInfoView.setBackgroundResource(R.color.bg);
		contactInfoView.setBackgroundResource(R.color.bg);
		abilityInfoView.setBackgroundResource(R.color.bg);
		jobInfoView.setBackgroundResource(R.color.bg);
		jobIntentView.setBackgroundResource(R.color.bg);
		eduInfoView.setBackgroundResource(R.color.bg);
		trainInfoView.setBackgroundResource(R.color.bg);
		myContentInfoView.setBackgroundResource(R.color.bg);
		langInfoView.setBackgroundResource(R.color.bg);
	}

	public class GetResumeBaseInfoTask extends AsyncTask<Void, Integer, ResumeBaseInfo> {
		protected void onPreExecute() {
			showLoadiing();
		};

		@Override
		protected ResumeBaseInfo doInBackground(Void... arg0) {
			try {
				Log.e("memberId", PersonInfoSave.memberInfo.getMemberId() + "");
				ResumeBaseInfo resumeInfo = ResumeInfoService.getResumeBaseInfo(PersonInfoSave.resumeInfo.getId());
				return resumeInfo;
			} catch (MyHttpException e) {
				Log.e("MyHttpException", e.getErrorCode());
				UIUtil.toast(context, e.getErrorMessage());
			}
			return null;
		}

		@Override
		protected void onPostExecute(ResumeBaseInfo resumeInfo) {
			hideLoading();
			if (resumeInfo != null) {
				baseInfoView.setText(resumeInfo.getBaseInfo());
				contactInfoView.setText(resumeInfo.getContact());
				jobIntentView.setText(resumeInfo.getQiZhi());
				myContentInfoView.setText(Html.fromHtml(resumeInfo.getAppraise() == 0 ? "<font color='red'>未填写</font>" : "已填写"));
				eduInfoView.setText(Html.fromHtml(resumeInfo.getEducation() == 0 ? "<font color='red'>未填写</font>" : "已填写"));
				trainInfoView.setText(Html.fromHtml(resumeInfo.getTraining() == 0 ? "<font color='red'>未填写</font>" : "已填写"));
				jobInfoView.setText(Html.fromHtml(resumeInfo.getWork() == 0 ? "<font color='red'>未填写</font>" : "已填写"));
				abilityInfoView.setText(Html.fromHtml(resumeInfo.getJiNeng() == 0 ? "<font color='red'>未填写</font>" : "已填写"));
				langInfoView.setText(Html.fromHtml(resumeInfo.getLang() == 0 ? "<font color='red'>未填写</font>" : "已填写"));
			}
		}
	}

	@Override
	protected void onDestroy() {
		if (task != null)
			task.cancel(true);
		super.onDestroy();
	}

	public Fragment changeFragmentAnOnResume(String tag, Bundle args) {
		if (TAG != null && TAG.equals(tag))
			return null;
		fm = getSupportFragmentManager();
		tr = fm.beginTransaction();
		if (fragment != null)
			tr.remove(fragment);
		Fragment changeFragment = findFragment(tag);
		if (changeFragment.isAdded()) {
			tr.remove(changeFragment);
			changeFragment = findFragment(tag, true);
		}
		if (args != null)
			changeFragment.setArguments(args);
		tr.setCustomAnimations(R.anim.left_slide_enter, R.anim.right_slide_exit);
		tr.replace(R.id.content, changeFragment, tag);
		fragment = changeFragment;
		TAG = tag;
		tr.commitAllowingStateLoss();
		return changeFragment;
	}

	public void hideSelectFragment() {
		fm = getSupportFragmentManager();
		tr = fm.beginTransaction();
		tr.setCustomAnimations(R.anim.left_slide_enter, R.anim.right_slide_exit);
		tr.remove(fragment);
		tr.commit();
		TAG = null;
		handler.postDelayed(new Runnable() {
			public void run() {
				cleanAll();
			}
		}, 210);
	}

	public Fragment findFragment(String tag) {
		return findFragment(tag, false);
	}

	public Fragment findFragment(String tag, boolean isAlwaysNew) {
		FragmentManager fm = getSupportFragmentManager();
		Fragment foundFragMent = fm.findFragmentByTag(tag);
		if (isAlwaysNew)
			foundFragMent = null;
		if (foundFragMent == null) {
			if (tag.equals(BaseInfoFragment.TAG)) {
				foundFragMent = new BaseInfoFragment();
			} else if (tag.equals(ContactInfoFragment.TAG)) {
				foundFragMent = new ContactInfoFragment();
			} else if (tag.equals(AbilityInfoFragment.TAG)) {
				foundFragMent = new AbilityInfoFragment();
			} else if (tag.equals(JobIntentFragment.TAG)) {
				foundFragMent = new JobIntentFragment();
			} else if (tag.equals(JobInfoFragment.TAG)) {
				foundFragMent = new JobInfoFragment();
			} else if (tag.equals(MyContentInfoFragment.TAG)) {
				foundFragMent = new MyContentInfoFragment();
			} else if (tag.equals(EduInfoFragment.TAG)) {
				foundFragMent = new EduInfoFragment();
			} else if (tag.equals(TrainInfoFragment.TAG)) {
				foundFragMent = new TrainInfoFragment();
			} else if (tag.equals(LanInfoFragment.TAG)) {
				foundFragMent = new LanInfoFragment();
			} else if (tag.equals(LanInfoChildFragment.TAG)) {
				foundFragMent = new LanInfoChildFragment();
			} else if (tag.equals(JobInfoChildFragment.TAG)) {
				foundFragMent = new JobInfoChildFragment();
			} else if (tag.equals(TrainInfoChildragment.TAG)) {
				foundFragMent = new TrainInfoChildragment();
			} else if (tag.equals(EduInfoChildFragment.TAG)) {
				foundFragMent = new EduInfoChildFragment();
			}
		}
		return foundFragMent;
	}
}