package com.geetion.job138.activity.group;

import com.liqi.job.R;
import com.geetion.job138.fragment.SettingFragment;

import android.os.Bundle;

public class FiveGroupActivity extends BaseGroupActivity {
	private String TAG = SettingFragment.TAG;

	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		setContentView(R.layout.activity_group);
		changeFragmentAnOnResume(TAG, null);
	}

}
