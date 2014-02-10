package com.geetion.job138.fragment;

import java.lang.reflect.InvocationTargetException;

import com.liqi.job.R;
import com.geetion.job138.activity.BaseActivity;
import com.geetion.job138.activity.PersonalStatusActivity;
import com.geetion.job138.activity.group.BaseGroupActivity;
import com.geetion.job138.util.AndroidUtil;
import com.geetion.job138.widget.custom.ParentLinearLayoutTouch;
import com.geetion.job138.widget.custom.ParentLinearLayoutTouch.OnInterceptTouchListener;

import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;

public class SelectStatusFragment extends BaseFragment {
	private BaseActivity activity;
	private ImageButton closeButton;
	private int[] radioIDs = { R.id.status_1, R.id.status_2, R.id.status_3, R.id.status_4, R.id.status_5 };
	private int selectNum = 0;
	private ParentLinearLayoutTouch parentLayout;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_select_status, container, false);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		activity = (BaseActivity) getActivity();
		getData();
		init();
		super.onActivityCreated(savedInstanceState);
	}

	public void getData() {
		selectNum = getArguments().getInt("selectStatus", 0);
	}

	public void init() {
		closeButton = (ImageButton) getView().findViewById(R.id.button_close);
		closeButton.setOnClickListener(this);
		selectStatus(selectNum, false);
		for (int i = 0; i < radioIDs.length; i++) {
			RadioButton radioButton = (RadioButton) getView().findViewById(radioIDs[i]);
			radioButton.setOnClickListener(this);
			switch (i) {
			case 1:
				radioButton.setText(Html.fromHtml("我目前在职, 正考虑换个新环境<br/><small><font color='#777777'>(如有合适的工作机会, 到岗时间一个月左右)</font></small>"));
				break;
			case 2:
				radioButton.setText(Html.fromHtml("我对现有工作算满意, 如有更好的工作机会, 我也可以考虑<br/><small><font color='#777777'>(到岗时间另议)</font></small>"));
				break;
			}
		}
		parentLayout = (ParentLinearLayoutTouch) getView().findViewById(R.id.parent_layout);
		parentLayout.setOnInterceptTouchListener(new OnInterceptTouchListener() {
			float downX = 0;

			@Override
			public boolean OnTouch(MotionEvent event) {
				if (event.getAction() == MotionEvent.ACTION_DOWN) {
					downX = event.getX();
					Log.e("downX", "" + downX);
				} else if (event.getAction() == MotionEvent.ACTION_MOVE) {
					if (event.getX() - downX > AndroidUtil.dpToPx(64, activity)) {
						Class personClass = activity.getClass();
						try {
							personClass.getMethod("hideSelectFragment", new Class[]{}).invoke(activity, null);
						} catch (IllegalArgumentException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (IllegalAccessException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (InvocationTargetException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (NoSuchMethodException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					Log.e("moveX", "" + event.getX());
				}
				return false;
			}
		});
	}

	@Override
	public void onClick(View v) {
		if (v == closeButton) {
			Class personClass = activity.getClass();
			try {
				personClass.getMethod("hideSelectFragment", new Class[]{}).invoke(activity, null);
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else if (v.getId() == radioIDs[0]) {
			selectStatus(0, true);
		} else if (v.getId() == radioIDs[1]) {
			selectStatus(1, true);
		} else if (v.getId() == radioIDs[2]) {
			selectStatus(2, true);
		} else if (v.getId() == radioIDs[3]) {
			selectStatus(3, true);
		} else if (v.getId() == radioIDs[4]) {
			selectStatus(4, true);
		}
	}

	public void selectStatus(int num, boolean isClose) {
		for (int i = 0; i < radioIDs.length; i++) {
			RadioButton radioButton = (RadioButton) getView().findViewById(radioIDs[i]);
			radioButton.setChecked(false);
		}
		RadioButton radioButton = (RadioButton) getView().findViewById(radioIDs[num]);
		radioButton.setChecked(true);
		try {
			Class personClass = activity.getClass();
			personClass.getMethod("setStatus", new Class[] { int.class }).invoke(activity, num);
			if (isClose) {
				personClass.getMethod("hideSelectFragment", new Class[] {}).invoke(activity, null);
			}
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
