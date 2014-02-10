package com.geetion.job138.activity;

import java.util.HashMap;

import com.geetion.job138.model.CityInfo;
import com.geetion.job138.model.JobType;
import com.geetion.job138.model.LoginRegisterMessage;
import com.geetion.job138.model.RegisterMessage;
import com.geetion.job138.service.CacheService;
import com.geetion.job138.service.LoginRegisterService;
import com.geetion.job138.service.PersonInfoSave;
import com.geetion.job138.service.SettingService;
import com.geetion.job138.util.MyHttpException;
import com.geetion.job138.util.UIUtil;
import com.geetion.job138.util.UIUtil.OnSelectAlter;
import com.geetion.job138.widget.custom.SelectCityPop;
import com.geetion.job138.widget.custom.SelectJobTypePop;
import com.geetion.job138.widget.custom.SelectCityPop.SelectCity;
import com.geetion.job138.widget.custom.SelectJobTypePop.SelectJobType;
import com.liqi.job.R;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

public class ApplyActivity extends BaseActivity {
	private LayoutInflater inflater;
	private EditText userpwd, pname, sex, birth, position, workadd, pay, email, mobile;
	private ImageButton applyButton, returnButton, loginButton;
	private AsyncTask applyTask;
	private RegisterMessage registerMessage;
	private int hireId, sexId = 1, positionId = 0, areaId = 0, memberId = 0;;
	private SelectCityPop cityPop;
	private SelectJobTypePop jobTypePop;
	private Activity activity;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.activity_apply_resume);
		initView();
		initData();
	}

	@Override
	protected void onDestroy() {
		if (applyTask != null) {
			applyTask.cancel(true);
		}
		super.onDestroy();
	}

	void initView() {
		activity = (Activity) context;
		userpwd = (EditText) findViewById(R.id.UserPwd);
		pname = (EditText) findViewById(R.id.PName);
		sex = (EditText) findViewById(R.id.Sex);
		birth = (EditText) findViewById(R.id.Birth);
		position = (EditText) findViewById(R.id.Position);
		workadd = (EditText) findViewById(R.id.WorkAdd);
		pay = (EditText) findViewById(R.id.Pay);
		sex.setFocusable(false);
		birth.setFocusable(false);
		position.setFocusable(false);
		workadd.setFocusable(false);
		pay.setFocusable(false);
		email = (EditText) findViewById(R.id.Email);
		mobile = (EditText) findViewById(R.id.Mobile);
		applyButton = (ImageButton) findViewById(R.id.Apply_Button);
		returnButton = (ImageButton) findViewById(R.id.button_back);
		loginButton = (ImageButton) findViewById(R.id.button_pass);

		applyButton.setOnClickListener(this);
		returnButton.setOnClickListener(this);
		loginButton.setOnClickListener(this);
		sex.setOnClickListener(this);
		birth.setOnClickListener(this);
		position.setOnClickListener(this);
		workadd.setOnClickListener(this);
		pay.setOnClickListener(this);
		inflater = activity.getLayoutInflater();
		View cityView = inflater.inflate(R.layout.window_city, null);
		View stationView = inflater.inflate(R.layout.window_station, null);
		cityPop = new SelectCityPop(cityView, activity, true);
		cityPop.setSelectCity(new SelectCity() {
			@Override
			public void getCity(CityInfo cityInfo) {
				workadd.setText(CacheService.provincesMap.get(cityInfo.getFid()).getName() + " " + cityInfo.getName());
				areaId = cityInfo.getId();
			}

			@Override
			public void check(HashMap<Integer, CityInfo> chooseMap, HashMap<Integer, CityInfo> chooseCityMap) {
				// TODO Auto-generated method stub

			}
		});

		jobTypePop = new SelectJobTypePop(stationView, activity, true);
		jobTypePop.setSelectJobTpe(new SelectJobType() {
			@Override
			public void getJobType(JobType jobType) {
				position.setText(jobType.getName());
				positionId = jobType.getId();
			}

			@Override
			public void check(HashMap<Integer, JobType> chooseMap, HashMap<Integer, JobType> chooseSubMap) {
				// TODO Auto-generated method stub

			}
		});
	}

	void initData() {
		hireId = getIntent().getIntExtra("id", -1);
		Log.d("hireId", hireId + "");
	}

	@Override
	public void onClick(View v) {
		if (returnButton == v) {
			finish();
		} else if (loginButton == v) {
			Intent intent = new Intent(context, LoginActivity.class);
			startActivity(intent);
		} else if (sex == v) {
			UIUtil.showSelectAlert(activity, CacheService.sexKeyMap.values().toArray(new String[0]), new OnSelectAlter() {
				public void onSelect(String select) {
					sex.setText(select);
				}
			});
		} else if (birth == v) {
			UIUtil.datePicker(activity, birth);
		} else if (position == v) {
			jobTypePop.showPop(position);
		} else if (workadd == v) {
			cityPop.showPop(workadd);
		} else if (pay == v) {
			UIUtil.showSelectAlert(activity, CacheService.payKeyMap.values().toArray(new String[0]), new OnSelectAlter() {
				public void onSelect(String select) {
					pay.setText(select);
				}
			});
		} else if (applyButton == v) {
			String userpwd_ = userpwd.getEditableText().toString();
			String pname_ = pname.getEditableText().toString();
			String sex_ = sex.getEditableText().toString();
			String birth_ = birth.getEditableText().toString();
			String position_ = position.getEditableText().toString();
			String workadd_ = workadd.getEditableText().toString();
			String pay_ = pay.getEditableText().toString();
			String email_ = email.getEditableText().toString();
			String mobile_ = mobile.getEditableText().toString();
			if (userpwd_.equals("") || pname_.equals("") || sex_.equals("") || birth_.equals("") || position_.equals("") || workadd_.equals("")
					|| pay_.equals("") || email_.equals("") || mobile_.equals("")) {
				UIUtil.toast(context, "请将信息填写完整");
				return;
			}
			registerMessage = new RegisterMessage();
			registerMessage.setBirth(birth_);
			registerMessage.setEmail(email_);
			registerMessage.setHireId(hireId + "");
			registerMessage.setMobile(mobile_);
			registerMessage.setPay(pay_);
			registerMessage.setPname(pname_);
			registerMessage.setPosition(positionId + "");
			registerMessage.setSex(CacheService.sexNameMap.get(sex_) + "");
			registerMessage.setUserName(mobile_);
			registerMessage.setUserPwd(userpwd_);
			registerMessage.setWorkAdd(areaId + "");
			applyTask = new ApplyTask().execute();
		}
	}

	class ApplyTask extends AsyncTask<Void, Void, LoginRegisterMessage> {
		@Override
		protected void onPreExecute() {
			showHoldLoading();
			super.onPreExecute();
		}

		@Override
		protected LoginRegisterMessage doInBackground(Void... params) {
			try {
				return LoginRegisterService.microRegister(registerMessage);
			} catch (MyHttpException e) {
				UIUtil.toast(activity, e.getErrorMessage());
			}
			return null;
		}

		@Override
		protected void onPostExecute(LoginRegisterMessage message) {
			hideHoldLoading();
			if (message != null) {
				SettingService.saveloginMessage(context, message);
				PersonInfoSave.resumeInfo.setId(message.getResumeId());
				PersonInfoSave.resumeInfo.setUserName(message.getUserName());
				PersonInfoSave.memberInfo.setMemberId(message.getMemberId());
				PersonInfoSave.memberInfo.setUserName(SettingService.loginMessage.getUserName());
				CacheService.isLogined = true;
				UIUtil.toast(activity, "招聘职位应聘成功");
				finish();
			}
		}
	}

}
