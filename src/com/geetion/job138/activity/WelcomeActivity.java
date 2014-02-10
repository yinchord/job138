package com.geetion.job138.activity;

import java.util.ArrayList;

import java.util.List;
import com.liqi.job.R;
import com.geetion.job138.service.CacheService;
import com.geetion.job138.service.PersonInfoSave;
import com.geetion.job138.service.SettingService;
import com.geetion.job138.util.AndroidUtil;
import com.geetion.job138.util.UIUtil;
import com.geetion.job138.widget.custom.MyViewPaper;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class WelcomeActivity extends BaseActivity {
	private MyViewPaper viewPager;
	private ViewPager backPager;
	private List<ImageView> pageViews;
	private List<View> myViews;
	// 用来表示每个小圆点的imageView
	private ImageView[] imageViews;
	private HorizontalScrollView scorllView;

	// 包裹圆点的LinearLayout
	private LinearLayout group;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		SettingService.getLoginMessage(this);
		if (SettingService.loginMessage.getMemberId() != 0 || SettingService.loginMessage.getResumeId() != 0) {
			PersonInfoSave.resumeInfo.setId(SettingService.loginMessage.getResumeId());
			PersonInfoSave.resumeInfo.setUserName(SettingService.loginMessage.getUserName());
			PersonInfoSave.memberInfo.setMemberId(SettingService.loginMessage.getMemberId());
			PersonInfoSave.memberInfo.setUserName(SettingService.loginMessage.getUserName());
			CacheService.isLogined = true;
		}
		if (SettingService.isShowedHelp(context)) {
			SettingService.saveHelpShowed(context);
			Intent intent = new Intent(context, TabWidgetActivity.class);
			startActivity(intent);
			finish();
			return;
		}
		setContentView(R.layout.activity_welcome);
		initView();
	}

	private void initView() {
		pageViews = new ArrayList<ImageView>();
		myViews = new ArrayList<View>();
		for (int i = 1; i <= 3; i++) {
			ImageView pic = new ImageView(this);
			int resid = getResources().getIdentifier("bg" + i, "drawable", this.getPackageName());
			pic.setBackgroundResource(resid);
			pageViews.add(pic);
		}
		imageViews = new ImageView[pageViews.size()];
		viewPager = (MyViewPaper) findViewById(R.id.awesomepager);
		backPager = (ViewPager) findViewById(R.id.back);
		group = (LinearLayout) findViewById(R.id.viewGroup);
		scorllView = (HorizontalScrollView) findViewById(R.id.horizontalScrollView1);
		for (int i = 0; i < pageViews.size(); i++) {
			imageViews[i] = new ImageView(this);
			imageViews[i].setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
			imageViews[i].setPadding(AndroidUtil.dpToPx(5, this), 0, AndroidUtil.dpToPx(5, this), 0);
			if (i == 0) {
				imageViews[i].setImageResource(R.drawable.point1);
			} else {
				imageViews[i].setImageResource(R.drawable.point0);
			}
			group.addView(imageViews[i]);
		}
		viewPager.setViewPaper(backPager);
		viewPager.setViewScorll(scorllView);
		LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View layout = mInflater.inflate(R.layout.item_welcome_last, null);
		ImageButton imageButton = (ImageButton) layout.findViewById(R.id.ok);
		imageButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				SettingService.saveHelpShowed(context);
				Intent intent = new Intent(context, TabWidgetActivity.class);
				startActivity(intent);
				finish();
			}
		});
		View one = mInflater.inflate(R.layout.item_welcome_one, null);
		View two = mInflater.inflate(R.layout.item_welcome_two, null);
		myViews.add(one);
		myViews.add(two);
		myViews.add(layout);

		viewPager.setAdapter(new PagerAdapter() {
			@Override
			public boolean isViewFromObject(View arg0, Object arg1) {
				return arg0 == arg1;
			}

			@Override
			public int getCount() {
				return myViews.size();
			}

			@Override
			public int getItemPosition(Object object) {
				return super.getItemPosition(object);
			}

			@Override
			public void destroyItem(View arg0, int arg1, Object arg2) {
				((ViewPager) arg0).removeView(myViews.get(arg1));
			}

			@Override
			public Object instantiateItem(View arg0, int arg1) {
				View layout = myViews.get(arg1);
				((ViewPager) arg0).addView(layout);
				return layout;
			}
		});
		viewPager.setOnPageChangeListener(new OnPageChangeListener() {
			@Override
			public void onPageSelected(int arg0) {
				for (int i = 0; i < imageViews.length; i++) {
					imageViews[arg0].setImageResource(R.drawable.point1);

					if (arg0 != i) {
						imageViews[i].setImageResource(R.drawable.point0);
					}
				}
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
			}

			@Override
			public void onPageScrollStateChanged(int arg0) {
			}
		});
		backPager.setAdapter(new PagerAdapter() {
			@Override
			public boolean isViewFromObject(View arg0, Object arg1) {
				return arg0 == arg1;
			}

			@Override
			public int getCount() {
				return pageViews.size();
			}

			@Override
			public int getItemPosition(Object object) {
				return super.getItemPosition(object);
			}

			@Override
			public void destroyItem(View arg0, int arg1, Object arg2) {
				((ViewPager) arg0).removeView(pageViews.get(arg1));
			}

			@Override
			public Object instantiateItem(View arg0, int arg1) {
				ImageView pic = pageViews.get(arg1);
				((ViewPager) arg0).addView(pic);
				return pageViews.get(arg1);
			}
		});
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub

	}
}
