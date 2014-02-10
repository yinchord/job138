package com.geetion.job138.widget.custom;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import com.liqi.job.R;
import com.geetion.job138.adapter.CityListAdapter;
import com.geetion.job138.model.CityInfo;
import com.geetion.job138.model.JobType;
import com.geetion.job138.service.CacheService;
import com.geetion.job138.service.SearchJobService;
import com.geetion.job138.service.StationTypeSave;
import com.geetion.job138.util.AndroidUtil;
import com.geetion.job138.util.FuncUtil;
import com.geetion.job138.util.MyHttpException;
import com.geetion.job138.util.UIUtil;
import com.geetion.job138.widget.custom.ParentLinearLayoutTouch.OnInterceptTouchListener;

import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.TextView;

public class SelectCityPop extends PopupWindow implements OnClickListener {
	private View cityView;
	private ImageButton closeButton;
	private ListView proviceListView, cityListView;
	private TextView cleanButton;
	private CityListAdapter proviceAdapter, cityAdapter;
	private Activity context;
	private ParentLinearLayoutTouch parentLayout;
	private ProgressBar progressBar;
	private GetCitiesTask getCitiesTask;
	private List<CityInfo> childs;
	private Handler handler = new Handler();
	private SelectCity selectInterface;
	private CityInfo parent;
	private boolean isSingle = true;
	private HashMap<Integer, Boolean> clickMap = new HashMap<Integer, Boolean>();
	private HashMap<Integer, Boolean> clickAbleMap = new HashMap<Integer, Boolean>();
	private int Fid = 0;
	private int mPoistion = 0;
	private String chooseIds = null;

	public interface SelectCity {
		public void check(HashMap<Integer, CityInfo> chooseMap, HashMap<Integer, CityInfo> chooseCityMap);

		public void getCity(CityInfo cityInfo);
	}

	public void setSelectCity(SelectCity selectCity) {
		this.selectInterface = selectCity;
	}

	public SelectCityPop(View view, final Activity context, boolean isSingle) {
		super(view, LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT, true);
		this.cityView = view;
		this.context = context;
		this.isSingle = isSingle;
		this.setOutsideTouchable(true);
		this.setTouchable(true);
		this.update();
		cleanButton = (TextView) cityView.findViewById(R.id.clean);
		closeButton = (ImageButton) cityView.findViewById(R.id.button_close);
		closeButton.setImageResource((isSingle ? R.drawable.close_icon : R.drawable.sure_icon));
		proviceListView = (ListView) cityView.findViewById(R.id.list_province);
		cityListView = (ListView) cityView.findViewById(R.id.list_city);
		progressBar = (ProgressBar) cityView.findViewById(R.id.progressBar1);
		closeButton.setOnClickListener(this);
		cleanButton.setOnClickListener(this);
		setAnimationStyle(R.style.PopupAnimation);
		parentLayout = (ParentLinearLayoutTouch) cityView.findViewById(R.id.parent_layout);
		parentLayout.setOnInterceptTouchListener(new OnInterceptTouchListener() {
			float downX = 0;

			@Override
			public boolean OnTouch(MotionEvent event) {
				if (event.getAction() == MotionEvent.ACTION_DOWN) {
					downX = event.getX();
					Log.e("downX", "" + downX);
				} else if (event.getAction() == MotionEvent.ACTION_MOVE) {
					if (event.getX() - downX > AndroidUtil.dpToPx(64, context)) {
						cityView.findViewById(R.id.shadow).setVisibility(View.INVISIBLE);
						cityListView.setVisibility(View.GONE);
						dismiss();
					}
					Log.e("moveX", "" + event.getX());
				}
				return false;
			}
		});
	}

	public void showPop(View region) {
		if (context.getCurrentFocus() != null) {
			((InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(context.getCurrentFocus().getWindowToken(),
					InputMethodManager.HIDE_NOT_ALWAYS);
		}
		getData();
		cityView.findViewById(R.id.shadow).setVisibility(View.INVISIBLE);
		this.showAtLocation(region, Gravity.RIGHT, 0, 0);
		this.showAtLocation(region, Gravity.RIGHT, 0, 0);
		handler.postDelayed(new Runnable() {
			public void run() {
				cityView.findViewById(R.id.shadow).setVisibility(View.VISIBLE);
			}
		}, 250);

	}

	public void getData() {
		initData();
	}

	public void initData() {
		childs = new ArrayList<CityInfo>();
		if (isSingle) {
			proviceAdapter = new CityListAdapter(proviceListView.getContext(), CacheService.provinces, R.layout.item_station, R.id.item_station_text, true,
					isSingle, this);
		} else {
			proviceAdapter = new CityListAdapter(proviceListView.getContext(), CacheService.provinces, R.layout.item_station_multi, R.id.item_station_text,
					true, isSingle, this);
		}
		proviceListView.setAdapter(proviceAdapter);
		cityAdapter = new CityListAdapter(cityListView.getContext(), childs, R.layout.item_station2, R.id.item_station2_text, false, isSingle, this);
		cityListView.setAdapter(cityAdapter);
		if (CacheService.provinces.isEmpty()) {
			getCitiesTask = new GetCitiesTask();
			getCitiesTask.execute();
		} else {
			refresh();
		}
		// proviceListView.setOnItemClickListener(new ProviceItemListener());
		// cityListView.setOnItemClickListener(new CityItemListener());
	}

	@Override
	public void onClick(View v) {
		if (v == closeButton) {
			cityView.findViewById(R.id.shadow).setVisibility(View.INVISIBLE);
			cityListView.setVisibility(View.GONE);
			if (!isSingle) {
				if (selectInterface != null && cityAdapter.getChoseMap() != null) {
					selectInterface.check(proviceAdapter.getChoseMap(), cityAdapter.getCityChoseMap());
				}
			}
			dismiss();
		} else if (v == cleanButton) {
			proviceAdapter.removeChooseMap();
			cityAdapter.removeCityChooseMap();
			cityAdapter.removeChooseMap();
			clickAbleMap.clear();
			clickMap.clear();
			if (isSingle) {
				cityView.findViewById(R.id.shadow).setVisibility(View.INVISIBLE);
				cityListView.setVisibility(View.GONE);
				if (selectInterface != null) {
					selectInterface.getCity(null);
				}
				dismiss();
			}
		}
	}

	@Override
	public void dismiss() {
		// TODO Auto-generated method stub
		if (getCitiesTask != null) {
			getCitiesTask.cancel(true);
		}
		clickAbleMap.clear();
		clickMap.clear();
		mPoistion = 0;
		super.dismiss();
	}

	private class GetCitiesTask extends AsyncTask<Void, Integer, List<CityInfo>> {
		List<CityInfo> list = new ArrayList<CityInfo>();

		@Override
		protected List<CityInfo> doInBackground(Void... params) {
			try {
				list = SearchJobService.getCityInfo();
				return list;
			} catch (MyHttpException e) {
				Log.e("MyHttpException", e.getErrorCode());
				UIUtil.toast(context, e.getErrorMessage());
			}
			return list;
		}

		@Override
		protected void onPostExecute(List<CityInfo> resultList) {
			if (!resultList.isEmpty()) {
				CacheService.saveCity(resultList);
				refresh();
				progressBar.setVisibility(View.GONE);
				proviceAdapter.notifyDataSetChanged();
			}
		}

		@Override
		protected void onPreExecute() {
			progressBar.setVisibility(View.VISIBLE);
		}

	}

	public void onItemClick(int position) {
		mPoistion = position;
		parent = CacheService.provinces.get(position);
		int id = parent.getId();
		Fid = id;
		List<CityInfo> l = CacheService.cities.get(id);
		childs.clear();
		childs.addAll(l);
		cityAdapter.notifyDataSetChanged();
		proviceAdapter.setSelect(position);
		proviceAdapter.notifyDataSetChanged();
		cityListView.setVisibility(View.VISIBLE);
	}

	public void onSubItemClick(int position) {
		if (clickMap.containsKey(Fid)) {
			if (clickMap.get(Fid)) {
				return;
			}
		}
		if (isSingle) {
			CityInfo cityInfo = childs.get(position);
			if (selectInterface != null) {
				selectInterface.getCity(cityInfo);
			}
			cityView.findViewById(R.id.shadow).setVisibility(View.INVISIBLE);
			cityListView.setVisibility(View.GONE);
			dismiss();
		} else {
			int id = childs.get(position).getId();
			CityInfo cityInfo = childs.get(position);
			if (cityAdapter.isSelect(id)) {
				proviceAdapter.setMyCount(cityAdapter.getCitySelectCount());
				clickAbleMap.put(cityInfo.getFid(), true);
				cityAdapter.cleanCitySelect(id);
				cityAdapter.cleanSelect(id);
				for (Entry<Integer, CityInfo> entry : cityAdapter.getChoseMap().entrySet()) {
					if (cityInfo.getFid() == entry.getValue().getFid()) {
						clickAbleMap.put(cityInfo.getFid(), false);
						break;
					}
				}
			} else {
				if (proviceAdapter.getSelectCount() + cityAdapter.getCitySelectCount() >= 5) {
					UIUtil.toast(context, "最多只能选择5个！");
					return;
				}
				proviceAdapter.setMyCount(cityAdapter.getCitySelectCount());
				clickAbleMap.put(cityInfo.getFid(), false);
				cityAdapter.addSelect(id, cityInfo);
				cityAdapter.addCitySelect(id, cityInfo);
			}
			proviceAdapter.notifyDataSetChanged();
			cityAdapter.notifyDataSetChanged();
		}

	}

	public void onCheckClick(int position) {
		CityInfo cityInfo = CacheService.provinces.get(position);
		int id = cityInfo.getId();
		List<CityInfo> l = CacheService.cities.get(id);
		if (proviceAdapter.isSelect(id)) {
			proviceAdapter.cleanSelect(id);
			removeAll(l);
		} else {
			removeChlids(l);
			proviceAdapter.addSelect(id, cityInfo);
			selectAll(l);
		}
		onItemClick(position);
	}

	private void selectAll(List<CityInfo> l) {
		for (int i = 0; i < l.size(); i++) {
			CityInfo child = l.get(i);
			int cId = child.getId();
			cityAdapter.addSelect(cId, child);
		}
	}

	private void removeAll(List<CityInfo> l) {
		for (int i = 0; i < l.size(); i++) {
			CityInfo child = l.get(i);
			int cId = child.getId();
			cityAdapter.cleanSelect(cId);
		}
	}

	private void removeChlids(List<CityInfo> childs) {
		for (int i = 0; i < childs.size(); i++) {
			if (cityAdapter.getChoseMap().containsKey(childs.get(i).getId())) {
				cityAdapter.cleanSelect(childs.get(i).getId());
			}
		}
	}

	public void addClickMap(int id, boolean isCheck) {
		clickMap.put(id, isCheck);
	}

	public boolean clickAble(int id) {
		if (clickAbleMap.containsKey(id)) {
			return clickAbleMap.get(id);
		}
		return true;
	}

	public void setChooseMap(HashMap<Integer, CityInfo> father, HashMap<Integer, CityInfo> child, HashMap<Integer, CityInfo> chooseChild) {
		proviceAdapter.setChooseMap(father);
		cityAdapter.setChooseMap(child);
		cityAdapter.setChooseCityMap(chooseChild);
		proviceAdapter.setSelect(mPoistion);
		proviceAdapter.notifyDataSetChanged();
		cityAdapter.notifyDataSetChanged();
	}

	public void setChooseIds(String chooseIds) {
		this.chooseIds = chooseIds;
	}

	public void refresh() {
		if (chooseIds != null && !TextUtils.isEmpty(chooseIds)) {
			List<List<String>> stringList = FuncUtil.parseStringToList(chooseIds);
			HashMap<Integer, CityInfo> stationChoose = FuncUtil.getCityParentMap(stringList.get(0));
			HashMap<Integer, CityInfo> subChoose = FuncUtil.getCityInfoChildMap(FuncUtil.parseChildSelct(stringList.get(1)));
			if (!stationChoose.isEmpty()) {
				proviceAdapter.setChooseMap(stationChoose);
				for (Entry<Integer, CityInfo> entry : stationChoose.entrySet()) {
					addClickMap(entry.getKey(), true);
					cityAdapter.addChooseMap(CacheService.cities.get(entry.getValue().getId()));
				}
			}
			if (!subChoose.isEmpty()) {
				cityAdapter.setChooseCityMap(subChoose);
				for (Entry<Integer, CityInfo> entry : subChoose.entrySet()) {
					clickAbleMap.put(entry.getValue().getFid(), false);
					cityAdapter.addSelect(entry.getKey(), entry.getValue());
				}
			}
			onItemClick(mPoistion);
			return;
		}
	}
}
