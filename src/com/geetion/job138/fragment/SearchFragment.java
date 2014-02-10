package com.geetion.job138.fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import com.liqi.job.R;
import com.geetion.job138.activity.PosListActivity;
import com.geetion.job138.activity.group.BaseGroupActivity;
import com.geetion.job138.adapter.SearchListAdapter;
import com.geetion.job138.model.CityInfo;
import com.geetion.job138.model.JobType;
import com.geetion.job138.model.Search;
import com.geetion.job138.model.Station;
import com.geetion.job138.service.CacheService;
import com.geetion.job138.service.SearchJobService;
import com.geetion.job138.service.SettingService;
import com.geetion.job138.util.MyHttpException;
import com.geetion.job138.util.UIUtil;
import com.geetion.job138.widget.custom.SelectCityPop;
import com.geetion.job138.widget.custom.SelectCityPop.SelectCity;
import com.geetion.job138.widget.custom.SelectJobTypePop;
import com.geetion.job138.widget.custom.SelectJobTypePop.SelectJobType;
import android.app.ActionBar.LayoutParams;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.TextView;

public class SearchFragment extends BaseFragment implements OnClickListener {
	public static String TAG = SearchFragment.class.getName();
	private BaseGroupActivity activity;
	private ImageButton searchButton;
	private SearchListAdapter adapter;
	private ListView listView;
	private View headerView;
	private List<Search> list;
	private TextView searchType, com_selector, pos_selector;
	private EditText keyword, station, region;
	private LayoutInflater inflater;
	private PopupWindow selectType;
	private SelectJobTypePop selectStation;
	private SelectCityPop selectCity;
	private TextView quicText1, quicText2, quicText3, quicText4;
	private ProgressBar quickProgressBar;
	private LinearLayout quickSearchBox;
	private JobType searchJobType = new JobType();
	private CityInfo searchCityInfo = new CityInfo();
	private int keyWordType = 1;
	private String keyWord;
	private String searchTitle;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_search, container, false);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		activity = (BaseGroupActivity) getActivity();
		initView();
		initData();
		super.onActivityCreated(savedInstanceState);
	}

	private void initView() {
		inflater = getActivity().getLayoutInflater();
		headerView = inflater.inflate(R.layout.header_search, null);
		listView = (ListView) getView().findViewById(R.id.listView1);
		listView.addHeaderView(headerView);
		View stationView = inflater.inflate(R.layout.window_station, null);
		View cityView = inflater.inflate(R.layout.window_city, null);
		View typeView = inflater.inflate(R.layout.window_category, null);
		com_selector = (TextView) typeView.findViewById(R.id.com_selector);
		pos_selector = (TextView) typeView.findViewById(R.id.pos_selector);
//		searchType = (TextView) typeView.findViewById(R.id.search_type);

		quickSearchBox = (LinearLayout) headerView.findViewById(R.id.quick_search_box);
		quickProgressBar = (ProgressBar) headerView.findViewById(R.id.quick_progressBar);
		quicText1 = (TextView) headerView.findViewById(R.id.quick_text1);
		quicText2 = (TextView) headerView.findViewById(R.id.quick_text2);
		quicText3 = (TextView) headerView.findViewById(R.id.quick_text3);
		quicText4 = (TextView) headerView.findViewById(R.id.quick_text4);
		selectType = new PopupWindow(typeView, LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		selectStation = new SelectJobTypePop(stationView, activity, true);
		selectCity = new SelectCityPop(cityView, activity, true);
		selectStation.setSelectJobTpe(new SelectJobType() {
			public void getJobType(JobType jobType) {
				// TODO Auto-generated method stub
				searchJobType = jobType;
				if(searchJobType != null) {
					station.setText(jobType.getName());
				} else {
					station.setText("");
				}
			}
			
			public void check(HashMap<Integer, JobType> chooseMap, HashMap<Integer, JobType> chooseSubMap) {
			}
		});

		selectCity.setSelectCity(new SelectCity() {
			public void check(HashMap<Integer, CityInfo> chooseMap, HashMap<Integer, CityInfo> chooseCityMap) {
			}

			@Override
			public void getCity(CityInfo cityInfo) {
				// TODO Auto-generated method stub
				searchCityInfo = cityInfo;
				if (searchCityInfo != null) {
					region.setText(CacheService.provincesMap.get(cityInfo.getFid()).getName() + " " + cityInfo.getName());
				} else {
					region.setText("");
				}
				
			}
		});

		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				Intent intent = new Intent(getActivity(), PosListActivity.class);
				startActivity(intent);
			}
		});
		searchType = (TextView) headerView.findViewById(R.id.type);
		keyword = (EditText) headerView.findViewById(R.id.keyword);
		station = (EditText) headerView.findViewById(R.id.station);
		region = (EditText) headerView.findViewById(R.id.regin);
		station.setFocusable(false);
		region.setFocusable(false);
		searchButton = (ImageButton) headerView.findViewById(R.id.button_search);
		searchButton.setOnClickListener(this);
		searchType.setOnClickListener(this);
		com_selector.setOnClickListener(this);
		pos_selector.setOnClickListener(this);
		station.setOnClickListener(this);
		region.setOnClickListener(this);
		quicText1.setOnClickListener(this);
		quicText2.setOnClickListener(this);
		quicText3.setOnClickListener(this);
		quicText4.setOnClickListener(this);
	}

	@Override
	public void onResume() {
		list.clear();
		list.addAll(SettingService.loadSearchHistory(activity));
		adapter.notifyDataSetChanged();
		super.onResume();
	}

	private void initData() {
		list = SettingService.loadSearchHistory(activity);
		adapter = new SearchListAdapter(getActivity(), list);
		listView.setAdapter(adapter);
//		searchCityInfo.setId(0);
//		searchCityInfo.setName("未选");
//		searchJobType.setId(0);
//		searchJobType.setName("未选");
		if (CacheService.quickSearchKeyWords.isEmpty()) {
			GetQuickSearchTask getQuickSearchTask = new GetQuickSearchTask();
			getQuickSearchTask.execute();
		} else {
			quickProgressBar.setVisibility(View.GONE);
			quickSearchBox.setVisibility(View.VISIBLE);
			quicText1.setText(CacheService.quickSearchKeyWords.get(0).getName());
			quicText2.setText(CacheService.quickSearchKeyWords.get(1).getName());
			quicText3.setText(CacheService.quickSearchKeyWords.get(2).getName());
			quicText4.setText(CacheService.quickSearchKeyWords.get(3).getName());
		}

	}

	@Override
	public void onClick(View v) {
		if (v == searchButton) {
//			if(TextUtils.isEmpty(keyword.getText().toString())) {
//				UIUtil.toast(activity, "请输入关键字");
//				return;
//			}
			goSearch(keyword);
		} else if (v == searchType) {
			if (!selectType.isShowing()) {
				selectType.showAsDropDown(searchType);
			} else {
				selectType.dismiss();
			}
		} else if (v == com_selector) {
			keyWordType = 2;
			searchType.setText(com_selector.getText());
			selectType.dismiss();
		} else if (v == pos_selector) {
			keyWordType = 1;
			searchType.setText(pos_selector.getText());
			selectType.dismiss();
		} else if (v == station) {
			selectStation.showPop(station);
		} else if (v == region) {
			selectCity.showPop(region);
		} else if (v == quicText1) {
			goQuickSearch(quicText1);
		} else if (v == quicText2) {
			goQuickSearch(quicText2);
		} else if (v == quicText3) {
			goQuickSearch(quicText3);
		} else if (v == quicText4) {
			goQuickSearch(quicText4);
		}
	}

	public void goSearch(TextView textView) {
		keyWord = textView.getText().toString();
		String cityName = "";
		String jobTypeName = "";
		if (TextUtils.isEmpty(keyWord)) {
			keyWord = "全部";
		}
		if (searchCityInfo != null && searchCityInfo.getName() != null) {
			cityName = "+" + searchCityInfo.getName();
		}
		if (searchJobType != null && searchJobType.getName() != null) {
			jobTypeName = "+" + searchJobType.getName();
		}
		searchTitle = keyWord + cityName + jobTypeName;
		Search search = new Search(keyWord, keyWordType, searchJobType, searchCityInfo);
		Intent intent = new Intent(getActivity(), PosListActivity.class);
		intent.putExtra("search", search);
		intent.putExtra("title", searchTitle);
		SettingService.saveSearchHistory(activity, search);
		startActivity(intent);
	}
	
	public void goQuickSearch(TextView textView) {
		keyWord = textView.getText().toString();
		searchTitle = keyWord;
		Search search = new Search(keyWord, keyWordType, null, null);
		Intent intent = new Intent(getActivity(), PosListActivity.class);
		intent.putExtra("search", search);
		intent.putExtra("title", searchTitle);
		SettingService.saveSearchHistory(activity, search);
		startActivity(intent);
	}

	private class GetQuickSearchTask extends AsyncTask<Void, Integer, List<Station>> {

		@Override
		protected List<Station> doInBackground(Void... params) {
			List<Station> stations = new ArrayList<Station>();
			try {
				stations = SearchJobService.quickSearch();
				return stations;
			} catch (MyHttpException e) {
				Log.e("MyHttpException", e.getErrorCode());
				UIUtil.toast(activity, e.getErrorMessage());
			}
			return stations;
		}

		@Override
		protected void onPostExecute(List<Station> result) {
			if (!result.isEmpty()) {
				CacheService.saveQuickSearchKeyWords(result);
				quickProgressBar.setVisibility(View.GONE);
				quickSearchBox.setVisibility(View.VISIBLE);
				quicText1.setText(result.get(0).getName());
				quicText2.setText(result.get(1).getName());
				quicText3.setText(result.get(2).getName());
				quicText4.setText(result.get(3).getName());
			}
		}
	}
}
