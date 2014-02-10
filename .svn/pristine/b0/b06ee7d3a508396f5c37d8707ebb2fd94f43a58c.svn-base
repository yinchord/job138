package com.geetion.job138.adapter;

import java.util.HashMap;
import java.util.List;

import com.liqi.job.R;
import com.geetion.job138.model.CityInfo;
import com.geetion.job138.model.JobType;
import com.geetion.job138.util.UIUtil;
import com.geetion.job138.widget.custom.SelectCityPop;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

public class CityListAdapter extends ArrayAdapter<CityInfo>{
	private LayoutInflater mInflater;
	private int mResource, mItemResource;
	private Context context;
	private List<CityInfo> list;
	private boolean isFather = false, isSingle = true;
	private int selectPostion = -1;
	private HashMap<Integer, CityInfo> chooseMap = new HashMap<Integer, CityInfo>();
	private HashMap<Integer, CityInfo> chooseCityMap = new HashMap<Integer, CityInfo>();
	private SelectCityPop Pop;
	private int otherCount = 0;
	
	public CityListAdapter(Context context, List<CityInfo> list, int mResource, int mItemResource, boolean isFather, boolean isSingle, SelectCityPop Pop) {
		super(context, 0, list);
		mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.context = context;
		this.list = list;
		this.mResource = mResource;
		this.mItemResource = mItemResource;
		this.isFather = isFather;
		this.isSingle = isSingle;
		this.Pop = Pop;
	}
	
	@Override
	public View getView(final int position, View convertView, final ViewGroup parent) {
		View layout = convertView;
		if (layout == null) {
			layout = mInflater.inflate(mResource, parent, false);
		}

		final CityInfo cityInfo = list.get(position);
		TextView itemText = (TextView) layout.findViewById(mItemResource);
		itemText.setText(cityInfo.getName().replaceAll(" ", ""));
		if (isSingle) {
			if (isFather) {
				if (selectPostion != position) {
					itemText.setTextColor(context.getResources().getColor(R.color.white));
					itemText.setBackgroundResource(R.color.select_bg);
					itemText.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
				} else {
					itemText.setTextColor(context.getResources().getColor(R.color.select_font_blue));
					itemText.setBackgroundResource(R.color.select_bg_selected);
					itemText.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.arrow_right, 0);
				}
			} else {
				if (chooseMap.containsKey(cityInfo.getId())) {
					itemText.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
				} else {
					itemText.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
				}
			}
		} else {
			final CheckBox itemCheck = (CheckBox) layout.findViewById(R.id.item_station_check);
			if (isFather) {
				if (chooseMap.containsKey(cityInfo.getId())) {
					itemCheck.setChecked(true);				
					if (selectPostion != position) {
						layout.setBackgroundColor(context.getResources().getColor(R.color.select_bg));
						itemText.setTextColor(context.getResources().getColor(R.color.white));
						itemText.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
					} else {
						layout.setBackgroundColor(context.getResources().getColor(R.color.select_bg_selected));
						itemText.setTextColor(context.getResources().getColor(R.color.select_font_blue));
						itemText.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.arrow_right, 0);
					}
				} else {
					if(!Pop.clickAble(cityInfo.getId())) {
						itemCheck.setEnabled(false);
					} else {
						itemCheck.setEnabled(true);
					}
					itemCheck.setChecked(false);
					if (selectPostion != position) {
						layout.setBackgroundColor(context.getResources().getColor(R.color.select_bg));
						itemText.setTextColor(context.getResources().getColor(R.color.white));
						itemText.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
					} else {
						layout.setBackgroundColor(context.getResources().getColor(R.color.select_bg_selected));
						itemText.setTextColor(context.getResources().getColor(R.color.select_font_blue));
						itemText.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.arrow_right, 0);
					}
				}

			} else {
				if (chooseMap.containsKey(cityInfo.getId())) {
					itemText.setCompoundDrawablesWithIntrinsicBounds(R.drawable.sub_select_on, 0, 0, 0);
				} else {
					itemText.setCompoundDrawablesWithIntrinsicBounds(R.drawable.sub_select_off, 0, 0, 0);
				}
			}
		}
		layout.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (isFather) {
					Pop.onItemClick(position);
				} else {
					Pop.onSubItemClick(position);
				}

			}
		});
		if (!isSingle && isFather) {
			final CheckBox itemCheck = (CheckBox) layout.findViewById(R.id.item_station_check);
			itemCheck.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					if (chooseMap.size() + otherCount >= 5 && !chooseMap.containsKey(cityInfo.getId())) {
						String string = String.valueOf(itemCheck.isChecked());
						Log.e("check", string);
						UIUtil.toast(context, "最多只能选择5个！");
						notifyDataSetChanged();
						return;
					}
					if(!Pop.clickAble(list.get(position).getId())) {
						notifyDataSetChanged();
						return;
					}
					itemCheck.setButtonDrawable(R.drawable.station_check_selector);
					Pop.onCheckClick(position);
					if (chooseMap.containsKey(cityInfo.getId())) {
						Pop.addClickMap(cityInfo.getId(), true);
					} else {
						Pop.addClickMap(cityInfo.getId(), false);
					}
				}
			});
		}
		return layout;
	}
	
	public void setSelect(int postion) {
		selectPostion = postion;
	}
	
	public void addSelect(int id, CityInfo cityInfo) {
		chooseMap.put(id, cityInfo);
	}

	public void cleanSelect(int id) {
		chooseMap.remove(id);
	}
	
	public void addCitySelect(int id, CityInfo cityInfo) {
		chooseCityMap.put(id, cityInfo);
	}

	public void cleanCitySelect(int id) {
		chooseCityMap.remove(id);
	}

	public int getSelectCount() {
		return chooseMap.size();
	}
	
	public boolean isSelect(int id) {
		return chooseMap.containsKey(id);
	}
	
	public HashMap<Integer, CityInfo> getChoseMap() {
		return chooseMap;
	}
	
	public HashMap<Integer, CityInfo> getCityChoseMap() {
		return chooseCityMap;
	}
	
	public void removeChooseMap() {
		chooseMap.clear();
		notifyDataSetChanged();
	}
	
	public void removeCityChooseMap() {
		chooseCityMap.clear();
	}
	
	public HashMap<Integer, CityInfo> getChooseMap() {
		return chooseMap;
	}

	public void setChooseMap(HashMap<Integer, CityInfo> chooseMap) {
		this.chooseMap = chooseMap;
	}
	
	public void setChooseCityMap(HashMap<Integer, CityInfo> chooseMap) {
		this.chooseCityMap = chooseMap;
	}
	
	public void setMyCount(int otherCount) {
		this.otherCount = otherCount;
	}
	
	public int getCitySelectCount() {
		return chooseCityMap.size();
	}

	public void addChooseMap(List<CityInfo> list) {
		for (int i = 0; i < list.size(); i++) {
			chooseMap.put(list.get(i).getId(), list.get(i));
		}
	}
}
