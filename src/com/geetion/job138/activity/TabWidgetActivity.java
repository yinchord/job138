package com.geetion.job138.activity;

import java.util.Calendar;
import java.util.TimeZone;
import cn.sharesdk.framework.ShareSDK;

import com.baidu.mobstat.SendStrategyEnum;
import com.baidu.mobstat.StatService;
import com.liqi.job.R;
import com.geetion.job138.activity.group.FirstGroupActivity;
import com.geetion.job138.activity.group.FiveGroupActivity;
import com.geetion.job138.activity.group.FourGroupActivity;
import com.geetion.job138.activity.group.SecondGroupActivity;
import com.geetion.job138.activity.group.ThirdGroupActivity;
import com.geetion.job138.asynctask.PushTask;
import com.geetion.job138.service.LocationService;
import com.geetion.job138.service.SettingService;
import com.geetion.job138.widget.pulldoor.PullDoorView;
import com.geetion.job138.widget.pulldoor.PullDoorView.FinishListener;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.TabActivity;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;

public class TabWidgetActivity extends TabActivity {
	private Context context;
	private TabHost tabHost;
	private ImageButton btn1, btn2, btn3, btn4, btn5;
	private ImageView wordView;
	private AsyncTask pushTask;
	PullDoorView pullView;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		context = this;
		setContentView(R.layout.tab);
		init();
		initTab();
		ShareSDK.initSDK(context);
		// 百度统计
		StatService.setAppChannel(this, "RepleceWithYourChannel", true);
		StatService.setOn(this, StatService.EXCEPTION_LOG);
		StatService.setLogSenderDelayed(10);
		StatService.setSendLogStrategy(this, SendStrategyEnum.APP_START, 1, false);
		StatService.setSessionTimeOut(30);
	}

	@Override
	protected void onResume() {
		StatService.onResume(context);
		super.onResume();
	}

	@Override
	protected void onPause() {
		StatService.onPause(context);
		super.onPause();
	}

	@Override
	protected void onDestroy() {
		ShareSDK.stopSDK(context);
		LocationService.stop();
		if (pushTask != null) {
			pushTask.cancel(true);
		}
		SettingService.clearCache();
		if (!SettingService.isAutoLogin(context)) {
			SettingService.cleanLoginMessage(context);
		}
		super.onDestroy();
	}

	public void init() {
		wordView = (ImageView) findViewById(R.id.word);
		pullView = (PullDoorView) findViewById(R.id.myImage);
		final Calendar c = Calendar.getInstance();
		c.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
		int mWay = c.get(Calendar.DAY_OF_WEEK);
		switch (mWay) {
		case 1:
			wordView.setImageResource(R.drawable.text_sun);
			break;
		case 2:
			wordView.setImageResource(R.drawable.text_mon);
			break;
		case 3:
			wordView.setImageResource(R.drawable.text_tue);
			break;
		case 4:
			wordView.setImageResource(R.drawable.text_wed);
			break;
		case 5:
			wordView.setImageResource(R.drawable.text_thu);
			break;
		case 6:
			wordView.setImageResource(R.drawable.text_fri);
			break;
		case 7:
			wordView.setImageResource(R.drawable.text_sat);
			break;
		}
		btn1 = (ImageButton) findViewById(R.id.btn1);
		btn2 = (ImageButton) findViewById(R.id.btn2);
		btn3 = (ImageButton) findViewById(R.id.btn3);
		btn4 = (ImageButton) findViewById(R.id.btn4);
		btn5 = (ImageButton) findViewById(R.id.btn5);
		btn1.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				tabHost.setCurrentTabByTag("tab1");
				btn1.setImageResource(R.drawable.home_hover);
				btn2.setImageResource(R.drawable.findjob);
				btn3.setImageResource(R.drawable.my);
				btn4.setImageResource(R.drawable.tips);
				btn5.setImageResource(R.drawable.more);

			}
		});
		btn2.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				tabHost.setCurrentTabByTag("tab2");
				btn1.setImageResource(R.drawable.home);
				btn2.setImageResource(R.drawable.findjob_hover);
				btn3.setImageResource(R.drawable.my);
				btn4.setImageResource(R.drawable.tips);
				btn5.setImageResource(R.drawable.more);

			}
		});
		btn3.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				tabHost.setCurrentTabByTag("tab3");
				btn1.setImageResource(R.drawable.home);
				btn2.setImageResource(R.drawable.findjob);
				btn3.setImageResource(R.drawable.my_hover);
				btn4.setImageResource(R.drawable.tips);
				btn5.setImageResource(R.drawable.more);
			}
		});

		btn4.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				tabHost.setCurrentTabByTag("tab4");
				btn1.setImageResource(R.drawable.home);
				btn2.setImageResource(R.drawable.findjob);
				btn3.setImageResource(R.drawable.my);
				btn4.setImageResource(R.drawable.tips_hover);
				btn5.setImageResource(R.drawable.more);
			}
		});
		btn5.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				tabHost.setCurrentTabByTag("tab5");
				btn1.setImageResource(R.drawable.home);
				btn2.setImageResource(R.drawable.findjob);
				btn3.setImageResource(R.drawable.my);
				btn4.setImageResource(R.drawable.tips);
				btn5.setImageResource(R.drawable.more_hover);
			}
		});
		pullView.setFinishListener(new FinishListener() {
			public void onFinish() {
				pushTask = new PushTask(context).execute();
			}
		});

	}

	public void initTab() {
		tabHost = getTabHost();
		TabSpec spec;
		Intent intent;
		// 第一个TAB
		intent = new Intent(this, FirstGroupActivity.class);
		spec = tabHost.newTabSpec("tab1").setIndicator("").setContent(intent);
		tabHost.addTab(spec);

		// 第二个TAB
		intent = new Intent(this, SecondGroupActivity.class);
		spec = tabHost.newTabSpec("tab2").setIndicator("").setContent(intent);
		tabHost.addTab(spec);

		// 第三个TAB
		intent = new Intent(this, ThirdGroupActivity.class);
		spec = tabHost.newTabSpec("tab3").setIndicator("").setContent(intent);
		tabHost.addTab(spec);

		// 第四个TAB
		intent = new Intent(this, FourGroupActivity.class);
		spec = tabHost.newTabSpec("tab4").setIndicator("").setContent(intent);
		tabHost.addTab(spec);

		// 第五个TAB
		intent = new Intent(this, FiveGroupActivity.class);
		spec = tabHost.newTabSpec("tab5").setIndicator("").setContent(intent);
		tabHost.addTab(spec);
		tabHost.setCurrentTab(0);
		btn1.setImageResource(R.drawable.home_hover);
	}
}
