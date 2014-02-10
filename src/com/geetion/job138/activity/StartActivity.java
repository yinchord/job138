package com.geetion.job138.activity;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.BMapManager;
import com.baidu.mapapi.search.MKAddrInfo;
import com.baidu.mapapi.search.MKBusLineResult;
import com.baidu.mapapi.search.MKDrivingRouteResult;
import com.baidu.mapapi.search.MKGeocoderAddressComponent;
import com.baidu.mapapi.search.MKPoiResult;
import com.baidu.mapapi.search.MKSearch;
import com.baidu.mapapi.search.MKSearchListener;
import com.baidu.mapapi.search.MKShareUrlResult;
import com.baidu.mapapi.search.MKSuggestionResult;
import com.baidu.mapapi.search.MKTransitRouteResult;
import com.baidu.mapapi.search.MKWalkingRouteResult;
import com.baidu.platform.comapi.basestruct.GeoPoint;
import com.geetion.job138.service.CacheService;
import com.geetion.job138.service.LocationService;
import com.geetion.job138.util.AndroidUtil;
import com.geetion.job138.util.FuncUtil;
import com.geetion.job138.util.HttpUtil;
import com.liqi.job.R;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;

public class StartActivity extends BaseActivity {
	LocationClient mLocClient;
	BDLocationListener myListener = new MyLocationListener();
	MKSearch mkSearch = new MKSearch();
	private boolean isOK, isGo = false;
	private Handler handler = new Handler();

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.activity_start);
		init();
	}

	@Override
	protected void onDestroy() {
		if (mLocClient != null)
			mLocClient.stop();
		super.onDestroy();
	}

	void init() {
		if (HttpUtil.haveConnection(context)) {
			LocationService.start(context);
			mLocClient = new LocationClient(this);
			mLocClient.registerLocationListener(myListener);
			LocationClientOption option = new LocationClientOption();
			option.setOpenGps(true);
			option.setCoorType("gcj02");
			option.setTimeOut(5000);
			mLocClient.setLocOption(option);
			mLocClient.start();
			mkSearch.init(LocationService.mBMapMan, mkSearchListener);
		}
		handler.postDelayed(new Runnable() {
			public void run() {
				if (!isGo) {
					isGo = true;
					Intent intent = new Intent(context, WelcomeActivity.class);
					startActivity(intent);
					finish();
				}
			}
		}, 5500);
	}

	public class MyLocationListener implements BDLocationListener {
		@Override
		public void onReceiveLocation(BDLocation location) {
			if (location == null)
				return;
			if (!isOK) {
				mkSearch.reverseGeocode(new GeoPoint((int) (location.getLatitude() * 1E6), (int) (location.getLongitude() * 1E6)));
			}
		}

		public void onReceivePoi(BDLocation poiLocation) {
			if (poiLocation == null) {
				return;
			}
		}
	}

	MKSearchListener mkSearchListener = new MKSearchListener() {
		@Override
		public void onGetSuggestionResult(MKSuggestionResult arg0, int arg1) {
		}

		@Override
		public void onGetShareUrlResult(MKShareUrlResult arg0, int arg1, int arg2) {
		}

		@Override
		public void onGetPoiResult(MKPoiResult arg0, int arg1, int arg2) {
		}

		@Override
		public void onGetPoiDetailSearchResult(int arg0, int arg1) {
		}

		@Override
		public void onGetBusDetailResult(MKBusLineResult result, int arg1) {
		}

		@Override
		public void onGetTransitRouteResult(MKTransitRouteResult result, int error) {
		}

		@Override
		public void onGetWalkingRouteResult(MKWalkingRouteResult result, int error) {
		}

		@Override
		public void onGetAddrResult(MKAddrInfo result, int arg1) {
			MKGeocoderAddressComponent poi = result.addressComponents;
			CacheService.city = poi.city.replace("市", "");
			Log.e("city", CacheService.city);
			if (!isGo) {
				Intent intent = new Intent(context, WelcomeActivity.class);
				startActivity(intent);
				finish();
				isGo = true;
			}
			isOK = true;
		}

		@Override
		public void onGetDrivingRouteResult(MKDrivingRouteResult arg0, int arg1) {

		}

	};

	@Override
	public void onClick(View arg0) {

	}
}
