package com.geetion.job138.activity;

import com.liqi.job.R;
import com.geetion.job138.model.FeedBack;
import com.geetion.job138.service.FeedBackService;
import com.geetion.job138.service.PersonInfoSave;
import com.geetion.job138.util.MyHttpException;
import com.geetion.job138.util.UIUtil;

import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

public class FeedBackActivity extends BaseActivity {
	private EditText info;
	private ImageButton buttonBack, buttonSend;
	private AsyncTask sendTask;
	private FeedBack feedBack;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.activity_edittext);
		initView();
		initData();
	}
	
	@Override
	protected void onDestroy() {
		if(sendTask != null){
			sendTask.cancel(true);
		}
		super.onDestroy();
	}

	void initView() {
		info = (EditText) findViewById(R.id.info);
		buttonSend = (ImageButton) findViewById(R.id.button_send);
		buttonBack = (ImageButton) findViewById(R.id.button_back);
		buttonSend.setOnClickListener(this);
		buttonBack.setOnClickListener(this);
	}

	void initData() {
	}

	@Override
	public void onClick(View v) {
		if (v == buttonSend) {
			if(info.getEditableText().toString().equals("")){
				UIUtil.toast(context, "请填写反馈内容");
				return;
			}
			sendTask = new SendTask().execute();
		} else if (v == buttonBack) {
			finish();
		}
	}

	class SendTask extends AsyncTask<Object, Object, Integer> {
		@Override
		protected void onPreExecute() {
			showLoadiing();
			buttonSend.setEnabled(false);
			feedBack = new FeedBack();
			feedBack.setInfo(info.getEditableText().toString());
			feedBack.setMemberLogin(PersonInfoSave.resumeInfo.getUserName());
			super.onPreExecute();
		}

		@Override
		protected Integer doInBackground(Object... params) {
			try {
				return FeedBackService.addFeedBack(feedBack);
			} catch (MyHttpException e) {
				e.printStackTrace();
			}
			return 0;
		}

		@Override
		protected void onPostExecute(Integer result) {
			hideLoading();
			buttonSend.setEnabled(true);
			if (result == 1) {
				UIUtil.toast(context, "反馈成功");
			}
			finish();
		}
	}

}
