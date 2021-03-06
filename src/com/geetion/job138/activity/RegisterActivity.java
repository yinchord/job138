package com.geetion.job138.activity;

import com.liqi.job.R;
import com.geetion.job138.model.LoginRegisterMessage;
import com.geetion.job138.model.RegisterMessage;
import com.geetion.job138.service.CacheService;
import com.geetion.job138.service.LoginRegisterService;
import com.geetion.job138.service.PersonInfoSave;
import com.geetion.job138.service.ResumeInfoService;
import com.geetion.job138.service.SettingService;
import com.geetion.job138.util.MyHttpException;
import com.geetion.job138.util.UIUtil;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

public class RegisterActivity extends BaseActivity {
	private ImageButton returnButton, submitButton;
	private EditText emailView, userNameView, passwordView, telView;
	private CheckBox isReadCheckBox;
	private String userName, password, tel;
	private RegisterTask task;
	private Activity activity;
	private TextView lawView;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		this.activity = this;
		setContentView(R.layout.activity_register);
		init();
	}

	public void init() {
		returnButton = (ImageButton) findViewById(R.id.button_left);
		submitButton = (ImageButton) findViewById(R.id.submit);
		returnButton.setOnClickListener(this);
		submitButton.setOnClickListener(this);
		emailView = (EditText) findViewById(R.id.email);
		userNameView = (EditText) findViewById(R.id.username);
		passwordView = (EditText) findViewById(R.id.password);
		isReadCheckBox = (CheckBox) findViewById(R.id.is_read);
		lawView = (TextView) findViewById(R.id.law);
		lawView.setOnClickListener(new OnClickListener() {
			public void onClick(View arg0) {
				Intent intent = new Intent(activity, RegisterLawActivity.class);
				startActivity(intent);
			}
		});
		// confirmPwdView = (EditText) findViewById(R.id.confirm_password);
		telView = (EditText) findViewById(R.id.tel);
	}

	@Override
	public void onClick(View v) {
		if (v == returnButton) {
			finish();
		} else if (v == submitButton) {
			// email = emailView.getText().toString();
			userName = userNameView.getText().toString();
			password = passwordView.getText().toString();
			tel = telView.getText().toString();
			if (TextUtils.isEmpty(userName)) {
				UIUtil.toast(context, "用户名不能为空");
				return;
			}
			if (TextUtils.isEmpty(password)) {
				UIUtil.toast(context, "密码不能为空");
				return;
			}
			// if (TextUtils.isEmpty(confirm)) {
			// UIUtil.toast(context, "确认密码不能为空");
			// return;
			// }
			if (TextUtils.isEmpty(tel)) {
				UIUtil.toast(context, "手机号码不能为空");
				return;
			}
			// if (!password.equals(confirm)) {
			// UIUtil.toast(context, "两次输入的密码不一致");
			// return;
			// }
			if (!isReadCheckBox.isChecked()) {
				UIUtil.toast(context, "请阅读会员注册协议");
				return;
			}

			RegisterMessage message = new RegisterMessage();
			message.setUserName(userName);
			message.setUserPwd(password);
			// message.setEmail(email);
			message.setMobile(tel);
			task = new RegisterTask();
			task.execute(message);
		}
	}

	@Override
	protected void onDestroy() {
		if (task != null)
			task.cancel(true);
		super.onDestroy();
	}

	public class RegisterTask extends AsyncTask<RegisterMessage, Integer, LoginRegisterMessage> {
		protected void onPreExecute() {
			submitButton.setClickable(false);
			showHoldLoading();
		};

		@Override
		protected LoginRegisterMessage doInBackground(RegisterMessage... arg0) {
			try {
				LoginRegisterMessage message = LoginRegisterService.register(arg0[0]);
				return message;
			} catch (MyHttpException e) {
				Log.e("MyHttpException", e.getErrorCode());
				UIUtil.toast(context, e.getErrorMessage());
			}
			return null;
		}

		@Override
		protected void onPostExecute(LoginRegisterMessage message) {
			hideHoldLoading();
			submitButton.setClickable(true);
			if (message == null)
				return;
			else {
				SettingService.saveloginMessage(context, message);
				PersonInfoSave.resumeInfo.setId(message.getResumeId());
				PersonInfoSave.resumeInfo.setUserName(message.getUserName());
				PersonInfoSave.memberInfo.setMemberId(message.getMemberId());
				PersonInfoSave.memberInfo.setUserName(SettingService.loginMessage.getUserName());
				UIUtil.toast(context, "注册成功");
				CacheService.isLogined = true;
				Intent intent = new Intent(activity, CreateResumeActivity.class);
				intent.putExtra("MemberId", message.getMemberId());
				startActivity(intent);
				finish();
			}

		}
	}
}
