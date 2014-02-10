package com.geetion.job138.activity;

import java.util.HashMap;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.sina.weibo.SinaWeibo;
import cn.sharesdk.tencent.qzone.QZone;
import cn.sharesdk.tencent.weibo.TencentWeibo;

import com.liqi.job.R;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageButton;

public class BindActivity extends BaseActivity {
	private ImageButton returnButton;
	private CheckBox sina, tencent, qq;
	private Platform sinaWeibo, tencentWeibo, qZone;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.activity_bind);
		initView();
		initData();
	}

	void initView() {
		sina = (CheckBox) findViewById(R.id.sina);
		tencent = (CheckBox) findViewById(R.id.tencent);
		qq = (CheckBox) findViewById(R.id.qq);
		returnButton = (ImageButton) findViewById(R.id.button_back);
		returnButton.setOnClickListener(this);
		sina.setOnClickListener(this);
		tencent.setOnClickListener(this);
		qq.setOnClickListener(this);
	}

	void initData() {
		sinaWeibo = ShareSDK.getPlatform(context, SinaWeibo.NAME);
		tencentWeibo = ShareSDK.getPlatform(context, TencentWeibo.NAME);
		qZone = ShareSDK.getPlatform(context, QZone.NAME);
		sinaWeibo.setPlatformActionListener(new MyActionListener());
		tencentWeibo.setPlatformActionListener(new MyActionListener());
		qZone.setPlatformActionListener(new MyActionListener());
		sina.setChecked(sinaWeibo.isValid());
		tencent.setChecked(tencentWeibo.isValid());
		qq.setChecked(qZone.isValid());
		sinaWeibo.SSOSetting(true);
		qZone.SSOSetting(true);
	}

	class MyActionListener implements PlatformActionListener {

		@Override
		public void onCancel(Platform arg0, int arg1) {
			changeBox(arg0.getName(), false);
		}

		@Override
		public void onComplete(Platform arg0, int arg1,
				HashMap<String, Object> arg2) {
			changeBox(arg0.getName(), true);
		}

		@Override
		public void onError(Platform arg0, int arg1, Throwable arg2) {
			changeBox(arg0.getName(), false);
		}

	}
	
	void changeBox(String name, boolean result){
		if (SinaWeibo.NAME.equals(name)) {
			sina.setChecked(result);
		} else if (TencentWeibo.NAME.equals(name)) {
			tencent.setChecked(result);
		} else if (QZone.NAME.equals(name)) {
			qq.setChecked(result);
		}
	}

	@Override
	public void onClick(View v) {
		if (v == returnButton) {
			finish();
		} else if (v == sina) {
			if (sinaWeibo.isValid()) {
				sinaWeibo.removeAccount();
			} else {
				sinaWeibo.authorize();
			}
		} else if (v == tencent) {
			if (tencentWeibo.isValid()) {
				tencentWeibo.removeAccount();
			} else {
				tencentWeibo.authorize();
			}
		} else if (v == qq) {
			if (qZone.isValid()) {
				qZone.removeAccount();
			} else {
				qZone.authorize();
			}
		}
	}
}
