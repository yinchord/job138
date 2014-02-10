package com.geetion.job138.activity;

import com.liqi.job.R;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

public class ResumeContentActivity extends BaseActivity {
	private String title, request;
	private TextView titleView;
	private ImageButton returnButton, okButton;
	private EditText infoView;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.activity_resume_big_content);
		getData();
		init();
	}

	public void getData() {
		title = getIntent().getStringExtra("title");
		request = getIntent().getStringExtra("content");
	}

	public void init() {
		titleView = (TextView) findViewById(R.id.title);
		titleView.setText(title);
		returnButton = (ImageButton) findViewById(R.id.button_back);
		returnButton.setOnClickListener(this);
		okButton = (ImageButton) findViewById(R.id.button_send);
		okButton.setOnClickListener(this);
		infoView = (EditText) findViewById(R.id.info);
		infoView.setText(request);
	}

	@Override
	public void onClick(View v) {
		if (v == returnButton) {
			finish();
		} else if (v == okButton) {
			Intent intent = new Intent();
			intent.putExtra("content", infoView.getText().toString());
			setResult(RESULT_OK, intent);
			finish();
		}
	}
}
