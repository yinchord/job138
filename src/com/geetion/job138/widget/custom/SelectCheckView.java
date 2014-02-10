package com.geetion.job138.widget.custom;
import com.geetion.job138.model.SelectInfo;
import com.liqi.job.R;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;

public class SelectCheckView extends RelativeLayout implements OnClickListener, OnCheckedChangeListener{
	
	private CheckBox mCheckBox;
	private TextView mTextView;
	private boolean isSelect = false;
	private SelectCheckListener mSelectCheckListener;
	private SelectInfo mSelectInfo;
	private int mType = 0;
	public static final int TYPE_PARENT = 0;
	public static final int TYPE_CHILD = 1;
	
	public SelectCheckView(Context context, AttributeSet attrs, int defStyle, SelectInfo selectInfo) {
		super(context, attrs, defStyle);
		setSelectInfo(selectInfo);
		init();
	}
	
	public SelectCheckView(Context context, AttributeSet attrs, SelectInfo selectInfo) {
		super(context, attrs);
		setSelectInfo(selectInfo);
		init();
	}
	
	public SelectCheckView(Context context, SelectInfo selectInfo) {
		super(context);
		setSelectInfo(selectInfo);
		init();
	}

	private void init() {
		inflate(getContext(), R.layout.item_station_multi, this);
		mCheckBox = (CheckBox) findViewById(R.id.item_station_check);
		mTextView = (TextView) findViewById(R.id.item_station_text);
		mCheckBox.setOnCheckedChangeListener(this);
		mTextView.setOnClickListener(this);
		parse(mSelectInfo);
	}
	
	public void setSelectCheckListener(SelectCheckListener listener) {
		mSelectCheckListener = listener;
	} 
	
	public static interface SelectCheckListener  {
		public void onClick();
		public void onSelected(boolean isSelecct);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.item_station_check:
			if(mSelectCheckListener != null) {
				mSelectCheckListener.onClick();
			}
			break;
		}
	}
	
	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		if(mSelectCheckListener != null) {
			mSelectCheckListener.onSelected(isChecked);
		}
	}

	public SelectInfo getSelectInfo() {
		return mSelectInfo;
	}

	public void setSelectInfo(SelectInfo mSelectInfo) {
		this.mSelectInfo = mSelectInfo; 
	}
	
	private void parse(SelectInfo selectInfo) {
		mTextView.setText(selectInfo.getName());
	}
	
	public void setType(int type) {
		switch (type) {
		case TYPE_PARENT:
			if(mType != TYPE_PARENT) {
				mCheckBox.setButtonDrawable(R.drawable.station_check_selector);
			}
			break;
			
		case TYPE_CHILD:
			if(mType != TYPE_CHILD) {
				mCheckBox.setButtonDrawable(R.drawable.sub_check_selector);
				mTextView.setSingleLine(true);
				mTextView.setGravity(Gravity.CENTER);
				mTextView.setPadding(5, 0, 0, 0);
				mTextView.setTextSize(14);
			}
			break;
		default:
			break;
		}
		mType = type;
	}
	
	public void setSelectView(boolean isSelect) {
		if(isSelect) {
			setBackgroundColor(getContext().getResources().getColor(R.color.select_bg_selected));
			mTextView.setTextColor(getContext().getResources().getColor(R.color.select_font_blue));
			mTextView.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.arrow_right, 0);
		} else {
			setBackgroundColor(getContext().getResources().getColor(R.color.select_bg));
			mTextView.setTextColor(getContext().getResources().getColor(R.color.white));
			mTextView.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
		}
	}

}
