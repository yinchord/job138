package com.geetion.job138.widget.custom;

import android.view.MotionEvent;
import android.widget.LinearLayout;
import android.content.Context;
import android.util.AttributeSet;

public class ParentLinearLayoutTouch extends LinearLayout {
	private OnInterceptTouchListener onInterceptTouchListener;

	// public ParentLinearLayoutTouch(Context context, AttributeSet attrs, int
	// defStyle) {
	// super(context, attrs, defStyle);
	//
	// }

	public ParentLinearLayoutTouch(Context context, AttributeSet attrs) {
		super(context, attrs);

	}

	public ParentLinearLayoutTouch(Context context) {
		super(context);
	}

	public interface OnInterceptTouchListener {
		public boolean OnTouch(MotionEvent event);
	}

	public void setOnInterceptTouchListener(OnInterceptTouchListener onInterceptTouchListener) {
		this.onInterceptTouchListener = onInterceptTouchListener;
	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		// TODO Auto-generated method stub
		if (onInterceptTouchListener != null) {
			return onInterceptTouchListener.OnTouch(ev);
		} else
			return super.onInterceptTouchEvent(ev);
	}

}
