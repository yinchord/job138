package com.geetion.job138.widget.custom;

import java.util.ArrayList;
import java.util.List;

import com.geetion.job138.util.AndroidUtil;
import com.geetion.job138.widget.custom.ParentLinearLayoutTouch.OnInterceptTouchListener;
import com.liqi.job.R;

import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckedTextView;
import android.widget.ImageButton;
import android.widget.PopupWindow;

public class SelectCertificatePop extends PopupWindow implements OnClickListener {
	private int[] Ids = { R.id.select_1, R.id.select_2, R.id.select_3, R.id.select_4, R.id.select_5, R.id.select_6, R.id.select_7, R.id.select_8, R.id.select_9 };
	private View popView;
	private Activity context;
	private ParentLinearLayoutTouch parentLayout;
	private ImageButton closeButton;
	private Handler handler = new Handler();
	private List<String> selectList = new ArrayList<String>();
	private PopSelectInterface popSelectInterface;

	public interface PopSelectInterface {
		public void onSelect(List<String> selectList);
	}

	public void setPopSelectInterface(PopSelectInterface popSelectInterface) {
		this.popSelectInterface = popSelectInterface;
	}

	public SelectCertificatePop(View view, final Activity context) {
		super(view, LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT, true);
		this.setOutsideTouchable(true);
		this.setTouchable(true);
		this.update();
		this.popView = view;
		this.context = context;
		closeButton = (ImageButton) popView.findViewById(R.id.button_close);
		closeButton.setOnClickListener(this);
		setAnimationStyle(R.style.PopupAnimation);
		parentLayout = (ParentLinearLayoutTouch) popView.findViewById(R.id.parent_layout);
		parentLayout.setOnInterceptTouchListener(new OnInterceptTouchListener() {
			float downX = 0;

			@Override
			public boolean OnTouch(MotionEvent event) {
				if (event.getAction() == MotionEvent.ACTION_DOWN) {
					downX = event.getX();
					Log.e("downX", "" + downX);
				} else if (event.getAction() == MotionEvent.ACTION_MOVE) {
					if (event.getX() - downX > AndroidUtil.dpToPx(64, context)) {
						popView.findViewById(R.id.shadow).setVisibility(View.INVISIBLE);
						dismiss();
					}
					Log.e("moveX", "" + event.getX());
				}
				return false;
			}
		});
		init();
	}

	public void showPop(View region) {
		if (context.getCurrentFocus() != null) {
			((InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(context.getCurrentFocus().getWindowToken(),
					InputMethodManager.HIDE_NOT_ALWAYS);
		}
		popView.findViewById(R.id.shadow).setVisibility(View.INVISIBLE);
		this.showAtLocation(region, Gravity.RIGHT, 0, 0);
		handler.postDelayed(new Runnable() {
			public void run() {
				popView.findViewById(R.id.shadow).setVisibility(View.VISIBLE);
			}
		}, 250);

	}

	public void setCheckList(List<String> checkList) {
		selectList.clear();
		for (int i = 0; i < Ids.length; i++) {
			final CheckedTextView checkedText = (CheckedTextView) popView.findViewById(Ids[i]);
			checkedText.setChecked(false);
		}
		for (String select : checkList) {
			CheckedTextView checkedText = (CheckedTextView) popView.findViewWithTag(select);
			if (checkedText != null) {
				checkedText.setChecked(true);
			}
		}
		selectList.addAll(checkList);
	}

	public void init() {
		for (int i = 0; i < Ids.length; i++) {
			final CheckedTextView checkedText = (CheckedTextView) popView.findViewById(Ids[i]);
			checkedText.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					checkedText.toggle();
					boolean isCheck = checkedText.isChecked();
					String name = checkedText.getText().toString();
					if (isCheck) {
						selectList.add(name);
					} else {
						selectList.remove(name);
					}
				}
			});
		}
	}

	@Override
	public void onClick(View v) {
		if (v == closeButton) {
			popView.findViewById(R.id.shadow).setVisibility(View.INVISIBLE);
			dismiss();
			if (popSelectInterface != null) {
				popSelectInterface.onSelect(selectList);
			}
		}
	}

}
