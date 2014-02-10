package com.geetion.job138.activity;

import com.liqi.job.R;

import com.geetion.job138.activity.RegisterActivity;
import com.geetion.job138.activity.group.BaseGroupActivity;
import com.geetion.job138.model.LoginRegisterMessage;
import com.geetion.job138.model.ResumeInfo;
import com.geetion.job138.service.CacheService;
import com.geetion.job138.service.LoginRegisterService;
import com.geetion.job138.service.PersonInfoSave;
import com.geetion.job138.service.SettingService;
import com.geetion.job138.util.AndroidUtil;
import com.geetion.job138.util.MyHttpException;
import com.geetion.job138.util.UIUtil;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;

public class LoginActivity extends BaseActivity {
	private ImageButton registerButton, loginButton;
	private EditText passwordView, usernameView;
	private CheckBox autoLogin;
	private String userName, password;
	private LoginTask task;

	@Override
	protected void onCreate(Bundle arg0) {
		setContentView(R.layout.fragment_login);
		init();
		super.onCreate(arg0);
	}

	public void init() {
		loginButton = (ImageButton) findViewById(R.id.button_login);
		registerButton = (ImageButton) findViewById(R.id.button_right);
		registerButton.setOnClickListener(this);
		loginButton = (ImageButton) findViewById(R.id.button_login);
		loginButton.setOnClickListener(this);
		passwordView = (EditText) findViewById(R.id.password);
		usernameView = (EditText) findViewById(R.id.username);
		autoLogin = (CheckBox) findViewById(R.id.confirm);
	}

	@Override
	public void onClick(View v) {
		if (v == registerButton) {
			Intent intent = new Intent(this, RegisterActivity.class);
			startActivity(intent);
		} else if (v == loginButton) {
			userName = usernameView.getText().toString();
			password = passwordView.getText().toString();
			if (TextUtils.isEmpty(userName)) {
				UIUtil.toast(this, "用户名不能为空");
				return;
			}
			if (TextUtils.isEmpty(password)) {
				UIUtil.toast(this, "密码不能为空");
				return;
			}
			task = new LoginTask();
			task.execute(userName, password);
		}
	}

	@Override
	public void onDestroy() {
		if (task != null)
			task.cancel(true);
		super.onDestroy();
	}

	public class LoginTask extends AsyncTask<String, Integer, LoginRegisterMessage> {
		protected void onPreExecute() {
			loginButton.setClickable(false);
			showHoldLoading();
		};

		@Override
		protected LoginRegisterMessage doInBackground(String... arg0) {
			try {
				LoginRegisterMessage message = LoginRegisterService.login(arg0[0], arg0[1]);
				return message;
			} catch (MyHttpException e) {
				Log.e("MyHttpException", e.getErrorCode());
				UIUtil.toast(context, e.getErrorMessage());
				loginButton.setClickable(true);
			}
			return null;
		}

		@Override
		protected void onPostExecute(LoginRegisterMessage message) {
			hideHoldLoading();
			if (message == null)
				return;
			else {
				SettingService.saveloginMessage(context, message);
				PersonInfoSave.resumeInfo.setId(message.getResumeId());
				PersonInfoSave.resumeInfo.setUserName(message.getUserName());
				PersonInfoSave.memberInfo.setMemberId(message.getMemberId());
				PersonInfoSave.memberInfo.setUserName(SettingService.loginMessage.getUserName());
				if (autoLogin.isChecked()) {
					SettingService.setAutoLogin(context);
				}
			}
			CacheService.isLogined = true;
			finish();
		}
	}
}
