package com.geetion.job138.application;

import com.geetion.job138.service.CacheService;
import com.geetion.job138.service.PersonInfoSave;
import com.geetion.job138.service.PushService;
import com.geetion.job138.service.SettingService;
import android.app.Application;

public class BaseApplication extends Application {
	public static boolean IS_DEBUG = true;

	@Override
	public void onCreate() {
		super.onCreate();
		CacheService.initMap();
		PushService.init(this);
	}

}
