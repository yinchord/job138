package com.geetion.job138.widget.custom;

import com.geetion.job138.util.AndroidUtil;
import com.geetion.job138.util.UIUtil;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.HorizontalScrollView;

public class MyViewPaper extends ViewPager {

	public MyViewPaper(Context context) {
		super(context);
	}

	public MyViewPaper(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	private ViewPager viewPaper;
	private HorizontalScrollView view;

	@Override
	protected void onScrollChanged(int l, int t, int oldl, int oldt) {
		// TODO Auto-generated method stub
		super.onScrollChanged(l, t, oldl, oldt);
		
		if (viewPaper != null){
			
			view.scrollTo((int)(200 * (float)l / 960), t);
		}
	}

	@Override
	public boolean onTouchEvent(MotionEvent arg0) {
		// TODO Auto-generated method stub
		if (viewPaper != null)
			viewPaper.onTouchEvent(arg0);
		return super.onTouchEvent(arg0);
	}

	@Override
	protected void onPageScrolled(int arg0, float arg1, int arg2) {
		// TODO Auto-generated method stub
		super.onPageScrolled(arg0, arg1, arg2);

	}

	public void setViewPaper(ViewPager view) {
		this.viewPaper = view;
	}

	public void setViewScorll(HorizontalScrollView view) {
		this.view = view;
	}

}
