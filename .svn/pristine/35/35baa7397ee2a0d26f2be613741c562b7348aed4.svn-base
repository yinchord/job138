package com.geetion.job138.activity;

import com.liqi.job.R;
import com.geetion.job138.fragment.SelectStatusFragment;
import com.geetion.job138.service.PersonInfoSave;
import com.geetion.job138.service.SettingService;
import com.geetion.job138.util.MyHttpException;
import com.geetion.job138.util.UIUtil;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

public class PersonalStatusActivity extends BaseActivity {
	private ImageButton backButton, yesButton;
	private Fragment selectFragment;
	private FragmentManager fm;
	private FragmentTransaction tr;
	private View shawdow;
	private String[] statusSting = { "我目前正在求职, 可立即上岗", "我目前在职, 正考虑换个新环境", "我对现有工作算满意, 如有更好的工作机会, 我也可以考虑", "我是毕业生,还没有太多工作经验, 但我对新工作充满期待与热情, 请给我机会",
			"我暂无求职打算" };
	private EditText statusText;
	private int selectStatus = 0;
	private ChangeStatusTask task;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.activity_personal_status);
		init();
		initStatus();
	}

	public void initStatus() {
		String initStatus = PersonInfoSave.memberInfo.getStatus();
		for (int i = 0; i < statusSting.length; i++) {
			String nowStatus = statusSting[i];
			if (nowStatus.equals(initStatus)) {
				statusText.setText(initStatus);
				selectStatus = i;
				break;
			}
		}
	}

	public void init() {
		backButton = (ImageButton) findViewById(R.id.button_left);
		backButton.setOnClickListener(this);
		yesButton = (ImageButton) findViewById(R.id.button_right);
		yesButton.setOnClickListener(this);
		shawdow = findViewById(R.id.shadow);
		shawdow.setOnClickListener(this);
		statusText = (EditText) findViewById(R.id.status);
		statusText.setOnClickListener(this);
	}

	public void showSelectFragment() {
		selectFragment = new SelectStatusFragment();
		Bundle bundle = new Bundle();
		bundle.putInt("selectStatus", selectStatus);
		selectFragment.setArguments(bundle);
		shawdow.setVisibility(View.VISIBLE);
		fm = getSupportFragmentManager();
		tr = fm.beginTransaction();
		tr.setCustomAnimations(R.anim.left_slide_enter, R.anim.right_slide_exit);
		tr.add(R.id.select, selectFragment);
		tr.commit();
	}

	public void hideSelectFragment() {
		fm = getSupportFragmentManager();
		tr = fm.beginTransaction();
		tr.setCustomAnimations(R.anim.left_slide_enter, R.anim.right_slide_exit);
		tr.remove(selectFragment);
		tr.commit();
		shawdow.setVisibility(View.GONE);
	}

	public void setStatus(int num) {
		statusText.setText(statusSting[num]);
		selectStatus = num;
	}

	@Override
	public void onClick(View v) {
		if (v == backButton) {
			finish();
		} else if (v == yesButton) {
			task = new ChangeStatusTask();
			task.execute();
		} else if (v == statusText) {
			showSelectFragment();
		}
	}

	@Override
	protected void onDestroy() {
		if (task != null)
			task.cancel(true);
		super.onDestroy();
	}

	public class ChangeStatusTask extends AsyncTask<Void, Integer, Integer> {
		protected void onPreExecute() {
			showHoldLoading();
		};

		@Override
		protected Integer doInBackground(Void... arg0) {
			try {
				int resumeId = SettingService.updateStatus(PersonInfoSave.memberInfo.getMemberId(), statusSting[selectStatus]);
				return resumeId;
			} catch (MyHttpException e) {
				Log.e("MyHttpException", e.getErrorCode());
				UIUtil.toast(context, e.getErrorMessage());
			}
			return null;
		}

		@Override
		protected void onPostExecute(Integer result) {
			hideHoldLoading();
			if (result != null) {
				PersonInfoSave.memberInfo.setStatus(statusSting[selectStatus]);
				UIUtil.toast(context, "修改求职状态成功");
				finish();
			}
		}
	}
}
