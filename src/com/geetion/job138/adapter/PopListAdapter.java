package com.geetion.job138.adapter;

import java.util.List;

import com.geetion.job138.model.SelectInfo;
import com.geetion.job138.widget.custom.SelectCheckView;
import com.geetion.job138.widget.custom.SelectCheckView.SelectCheckListener;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

public class PopListAdapter extends ArrayAdapter<SelectInfo> {
	private Context context;
	private List<SelectInfo> list;
	private int type = 0;
	private int selectPostion = -1;
	private PopListAdapter otherAdapter;
	
	public PopListAdapter(Context context, List<SelectInfo> list, int type, boolean isSingle, PopListAdapter otherAdapter) {
		super(context, 0, list);
		this.context = context;
		this.list.addAll(list);
		this.type = type;
		this.otherAdapter = otherAdapter;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		SelectCheckView item = new SelectCheckView(context, list.get(position));
		item.setType(type);
		item.setSelectCheckListener(new SelectCheckListener() {
			
			@Override
			public void onSelected(boolean isSelecct) {
				list.get(position).setCheck(isSelecct);
				notifyDataSetChanged();
			}
			
			@Override
			public void onClick() {
				notifyDataSetChanged();
			}
		});
		if(type == 0) {
			item.setSelected(selectPostion == position);
		}
		return item;
	}
	
	public void setSelect(int postion) {
		selectPostion = postion;
	}
}
