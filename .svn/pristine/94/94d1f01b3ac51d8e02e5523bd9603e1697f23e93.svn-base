package com.geetion.job138.service;

import android.content.Context;

import com.baidu.mapapi.BMapManager;

public class LocationService {
	public static BMapManager mBMapMan;

	public static void start(Context context) {
		mBMapMan = new BMapManager(context.getApplicationContext());
		mBMapMan.init("4yUG3Vrmg3BQ68Bv1XGvf01q", null);
		mBMapMan.start();
	}

	public static void stop() {
		if (mBMapMan != null)
			mBMapMan.stop();
	}
}
