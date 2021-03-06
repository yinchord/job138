package com.geetion.job138.activity;

import com.liqi.job.R;
import com.geetion.job138.activity.group.BaseGroupActivity;
import com.geetion.job138.fragment.BaseFragment;
import com.geetion.job138.fragment.PersonalCenterFragment;
import com.geetion.job138.model.MemberMessage;
import com.geetion.job138.service.PersonInfoSave;
import com.geetion.job138.service.ResumeInfoService;
import com.geetion.job138.service.SettingService;
import com.geetion.job138.util.AndroidUtil;
import com.geetion.job138.util.MyHttpException;
import com.geetion.job138.util.UIUtil;

import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;

public class PersonalTelActivity extends BaseActivity {
	private ImageButton backButton, yesButton;
	private EditText telView;
	private String tel;
	private UpdateTelTask task;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.activity_personal_tel);
		init();
	}

	public void init() {
		backButton = (ImageButton) findViewById(R.id.button_left);
		backButton.setOnClickListener(this);
		yesButton = (ImageButton) findViewById(R.id.button_right);
		yesButton.setOnClickListener(this);
		telView = (EditText) findViewById(R.id.tel);
		telView.setText(PersonInfoSave.memberInfo.getMobile());
	}

	@Override
	public void onClick(View v) {
		if (v == backButton) {
			finish();
		} else if (v == yesButton) {
			tel = telView.getText().toString();
			if (TextUtils.isEmpty(tel)) {
				UIUtil.toast(context, "手机号码不能为空");
				return;
			}
			task = new UpdateTelTask();
			task.execute();
		}
	}

	public class UpdateTelTask extends AsyncTask<Void, Integer, Integer> {
		protected void onPreExecute() {
			showHoldLoading();
			yesButton.setClickable(false);
		};

		@Override
		protected Integer doInBackground(Void... arg0) {
			try {
				Log.e("memberId", PersonInfoSave.memberInfo.getMemberId() + "");
				int resumeId = SettingService.updateMobile(PersonInfoSave.resumeInfo.getUserName(), String.valueOf(PersonInfoSave.resumeInfo.getId()), tel);
				return resumeId;
			} catch (MyHttpException e) {
				Log.e("MyHttpException", e.getErrorCode());
				UIUtil.toast(context, e.getErrorMessage());
			}
			return 0;
		}

		@Override
		protected void onPostExecute(Integer result) {
			hideHoldLoading();
			if (result != 0) {
				UIUtil.toast(context, "修改成功");
				PersonInfoSave.memberInfo.setMobile(tel);
				finish();
			}
			yesButton.setClickable(true);
		}
	}
}
