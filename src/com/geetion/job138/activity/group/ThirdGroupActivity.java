package com.geetion.job138.activity.group;

import com.liqi.job.R;
import com.geetion.job138.fragment.LoginFragment;
import com.geetion.job138.fragment.PersonalCenterFragment;
import com.geetion.job138.service.CacheService;
import com.geetion.job138.service.PersonInfoSave;
import com.geetion.job138.service.SettingService;

import android.os.Bundle;

public class ThirdGroupActivity extends BaseGroupActivity {
	// private String TAG = LoginFragment.TAG;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.activity_group);
		if (CacheService.isLogined) {
			changeFragmentAnOnResume(PersonalCenterFragment.TAG, null);
		} else {
			changeFragmentAnOnResume(LoginFragment.TAG, null);
		}
	}
}
