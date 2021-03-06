package com.geetion.job138.activity;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.BMapManager;
import com.baidu.mapapi.map.ItemizedOverlay;
import com.baidu.mapapi.map.LocationData;
import com.baidu.mapapi.map.MapController;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationOverlay;
import com.baidu.mapapi.map.MyLocationOverlay.LocationMode;
import com.baidu.mapapi.map.OverlayItem;
import com.baidu.mapapi.map.RouteOverlay;
import com.baidu.mapapi.map.TransitOverlay;
import com.baidu.mapapi.navi.BaiduMapNavigation;
import com.baidu.mapapi.navi.NaviPara;
import com.baidu.mapapi.search.MKAddrInfo;
import com.baidu.mapapi.search.MKBusLineResult;
import com.baidu.mapapi.search.MKDrivingRouteResult;
import com.baidu.mapapi.search.MKGeocoderAddressComponent;
import com.baidu.mapapi.search.MKPlanNode;
import com.baidu.mapapi.search.MKPoiInfo;
import com.baidu.mapapi.search.MKPoiResult;
import com.baidu.mapapi.search.MKSearch;
import com.baidu.mapapi.search.MKSearchListener;
import com.baidu.mapapi.search.MKShareUrlResult;
import com.baidu.mapapi.search.MKSuggestionResult;
import com.baidu.mapapi.search.MKTransitRouteResult;
import com.baidu.mapapi.search.MKWalkingRouteResult;
import com.baidu.platform.comapi.basestruct.GeoPoint;
import com.liqi.job.R;
import com.geetion.job138.fragment.resume.EduInfoFragment;
import com.geetion.job138.service.LocationService;

import android.R.integer;
import android.app.Activity;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class MapActivity extends BaseActivity implements OnClickListener {
	ImageButton backButton, driveButton, busButton, walkButton;
	MapView mMapView = null;
	MKSearch mkSearch = new MKSearch();
	private myItemOverLay mItemOverLay = null;
	public LocationClient mLocationClient = null;
	public BDLocationListener myListener = new MyLocationListener();
	TextView tvStrat, tvDestination;
	LocationClient mLocClient;
	LocationData locData = null;
	locationOverlay myLocationOverlay = null;
	private MapController mMapController = null;
	boolean isRequest = false;
	boolean isFirstLoc = true;
	Bundle bundle;
	String city;
	float y = 0;// 纬度
	float x = 0;// 经度
	String endAddress, startAddress;
	ProgressBar progressBar;
	Thread thread;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initView();
	}

	private void initView() {
		bundle = getIntent().getExtras();
		if (bundle != null) {// 获取 公司的经纬度,地址
			x = bundle.getFloat("x");
			y = bundle.getFloat("y");
			endAddress = bundle.getString("address");
		}
		setContentView(R.layout.activity_map);
		progressBar = (ProgressBar) findViewById(R.id.loading);
		tvStrat = (TextView) findViewById(R.id.tvStart);
		tvDestination = (TextView) findViewById(R.id.tvDestination);
		backButton = (ImageButton) findViewById(R.id.button_back);
		driveButton = (ImageButton) findViewById(R.id.button_drive);
		busButton = (ImageButton) findViewById(R.id.button_bus);
		walkButton = (ImageButton) findViewById(R.id.button_walk);
		tvDestination.setText(endAddress);
		driveButton.setOnClickListener(this);
		busButton.setOnClickListener(this);
		walkButton.setOnClickListener(this);
		backButton.setOnClickListener(this);

		mMapView = (MapView) findViewById(R.id.bmapsView);
		mMapController = mMapView.getController();
		mMapView.getController().setZoom(14);
		mMapView.getController().enableClick(true);
		mMapView.setDrawingCacheEnabled(true);
		mMapView.setBuiltInZoomControls(true);
		mLocClient = new LocationClient(this);
		locData = new LocationData();
		mLocClient.registerLocationListener(myListener);
		LocationClientOption option = new LocationClientOption();
		option.setOpenGps(true);// ��gps
		option.setCoorType("gcj02"); // �����������
		option.setScanSpan(1000);
		mLocClient.setLocOption(option);
		mLocClient.start();
		myLocationOverlay = new locationOverlay(mMapView);
		myLocationOverlay.setData(locData);
		myLocationOverlay.enableCompass();
		// 添加目的地的气球泡泡
		mItemOverLay = new myItemOverLay(getResources().getDrawable(R.drawable.icon_gcoding), mMapView);
		GeoPoint poi = new GeoPoint((int) (y * 1E6), (int) (x * 1E6));
		OverlayItem overlayItem = new OverlayItem(poi, "", "");
		overlayItem.setMarker(getResources().getDrawable(R.drawable.icon_gcoding));
		mItemOverLay.addItem(overlayItem);
		mMapView.getOverlays().add(mItemOverLay);
		mMapController.animateTo(poi);
		mMapView.refresh();

		mMapView.getOverlays().add(myLocationOverlay);
		mkSearch.init(LocationService.mBMapMan, mkSearchListener);
		mMapView.refresh();
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
			// TODO Auto-generated method stub
			if (result == null) {
				Toast.makeText(MapActivity.this, "抱歉，未找到结果", Toast.LENGTH_LONG).show();
				return;
			}
			TransitOverlay transitOverlay = new TransitOverlay(MapActivity.this, mMapView); // 此处仅展示一个方案作为示例
			transitOverlay.setData(result.getPlan(0));
			mMapView.getOverlays().clear();
			mMapView.getOverlays().add(transitOverlay);
			mMapView.getController().zoomToSpan(transitOverlay.getLatSpanE6(), transitOverlay.getLonSpanE6());

			mMapView.refresh();
			mMapView.getController().animateTo(result.getStart().pt);
		}

		@Override
		public void onGetDrivingRouteResult(MKDrivingRouteResult result, int error) {
			// TODO Auto-generated method stub
			hideLoading();
			if (result == null) {
				Toast.makeText(MapActivity.this, "抱歉，未找到结果", Toast.LENGTH_LONG).show();
				return;
			}
			RouteOverlay routeOverlay = new RouteOverlay(MapActivity.this, mMapView); // 此处仅展示一个方案作为示例
			routeOverlay.setData(result.getPlan(0).getRoute(0));
			mMapView.getOverlays().clear();
			mMapView.getOverlays().add(routeOverlay);
			mMapView.refresh();
			mMapView.getController().zoomToSpan(routeOverlay.getLatSpanE6(), routeOverlay.getLonSpanE6());
			mMapView.getController().animateTo(result.getStart().pt);
		}

		@Override
		public void onGetWalkingRouteResult(MKWalkingRouteResult result, int error) {
			// TODO Auto-generated method stub
			if (result == null) {
				Toast.makeText(MapActivity.this, "抱歉，未找到结果", Toast.LENGTH_LONG).show();
				return;
			}
			RouteOverlay routeOverlay = new RouteOverlay(MapActivity.this, mMapView); // 此处仅展示一个方案作为示例
			routeOverlay.setData(result.getPlan(0).getRoute(0));
			mMapView.getOverlays().clear();
			mMapView.getOverlays().add(routeOverlay);
			mMapView.refresh();
			mMapView.getController().zoomToSpan(routeOverlay.getLatSpanE6(), routeOverlay.getLonSpanE6());
			mMapView.getController().animateTo(result.getStart().pt);
		}

		@Override
		public void onGetAddrResult(MKAddrInfo result, int arg1) {
			// TODO Auto-generated method stub
			MKGeocoderAddressComponent poi = result.addressComponents;
			startAddress = result.strAddr;
			tvStrat.setText(startAddress);
			city = poi.city;
		}

	};

	// 搜索自驾路线
	public void searchDriveRoute() {
		MKPlanNode start = new MKPlanNode();
		start.pt = new GeoPoint((int) (locData.latitude * 1E6), (int) (locData.longitude * 1E6));
		MKPlanNode end = new MKPlanNode();
		end.pt = new GeoPoint((int) (y * 1E6), (int) (x * 1E6));
		mkSearch.setDrivingPolicy(MKSearch.ECAR_TIME_FIRST);
		mkSearch.drivingSearch(null, start, null, end);
	}

	// 搜索公交路线
	public void searchBusLine() {

		MKPlanNode start = new MKPlanNode();
		start.pt = new GeoPoint((int) (locData.latitude * 1E6), (int) (locData.longitude * 1E6));
		MKPlanNode end = new MKPlanNode();
		end.pt = new GeoPoint((int) (y * 1E6), (int) (x * 1E6));
		mkSearch.setDrivingPolicy(MKSearch.ECAR_TIME_FIRST);
		mkSearch.transitSearch(city, start, end);
	}

	public void searchWalkLine() {
		MKPlanNode start = new MKPlanNode();
		MKPlanNode end = new MKPlanNode();
		start.pt = new GeoPoint((int) (locData.latitude * 1E6), (int) (locData.longitude * 1E6));
		end.pt = new GeoPoint((int) (y * 1E6), (int) (x * 1E6));
		mkSearch.walkingSearch(null, start, null, end);
	}

	public class MyLocationListener implements BDLocationListener {
		@Override
		public void onReceiveLocation(BDLocation location) {
			if (location == null)
				return;
			locData.latitude = location.getLatitude();
			locData.longitude = location.getLongitude();
			locData.accuracy = location.getRadius();
			locData.direction = location.getDerect();
			myLocationOverlay.setData(locData);
			mkSearch.reverseGeocode(new GeoPoint((int) (locData.latitude * 1E6), (int) (locData.longitude * 1E6)));
			mMapView.refresh();
			if (isRequest || isFirstLoc) {
				Log.d("LocationOverlay", "receive location, animate to it");
				// mMapController.animateTo(new GeoPoint((int) (locData.latitude
				// * 1e6), (int) (locData.longitude * 1e6)));
				Log.d("location", location.getLatitude() + " " + location.getLongitude());
				isRequest = false;
				// myLocationOverlay.setLocationMode(LocationMode.FOLLOWING);
			}
			isFirstLoc = false;
		}

		public void onReceivePoi(BDLocation poiLocation) {
			if (poiLocation == null) {
				return;
			}
		}
	}

	public class myItemOverLay extends ItemizedOverlay<OverlayItem> {

		public myItemOverLay(Drawable drawableMark, MapView mapView) {
			super(drawableMark, mapView);
		}
	}

	public class locationOverlay extends MyLocationOverlay {

		public locationOverlay(MapView mapView) {
			super(mapView);
			// TODO Auto-generated constructor stub
		}

		@Override
		protected boolean dispatchTap() {
			return true;
		}

	}

	@Override
	protected void onPause() {
		mMapView.onPause();
		super.onPause();
	}

	@Override
	protected void onResume() {
		mMapView.onResume();
		super.onResume();
	}

	@Override
	protected void onDestroy() {
		// �˳�ʱ��ٶ�λ
		if (mLocClient != null)
			mLocClient.stop();
		mMapView.destroy();
		super.onDestroy();
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		mMapView.onSaveInstanceState(outState);

	}

	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		super.onRestoreInstanceState(savedInstanceState);
		mMapView.onRestoreInstanceState(savedInstanceState);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	public void AsySerch(final View v) {
		new AsyncTask<Void, Void, Void>() {
			@Override
			protected void onPreExecute() {
				super.onPreExecute();
				showLoadiing();

			}

			@Override
			protected Void doInBackground(Void... params) {
				if (v == driveButton) {
					searchDriveRoute();
				} else if (v == busButton) {
					searchBusLine();
				} else if (v == walkButton) {
					searchWalkLine();
				}
				return null;
			}

			protected void onProgressUpdate(Void[] values) {
				showLoadiing();
			};

			@Override
			protected void onPostExecute(Void result) {
				super.onPostExecute(result);
				hideLoading();
			}
		}.execute();
	}

	@Override
	public void onClick(View v) {
		if (backButton == v) {
			finish();
		} else if (v == driveButton) {
			AsySerch(driveButton);
		} else if (v == busButton) {
			AsySerch(busButton);
		} else if (v == walkButton) {
			AsySerch(walkButton);
		}
	}
}
