package com.geetion.job138.activity.group;

import com.liqi.job.R;
import com.geetion.job138.fragment.RandomFragment;

import android.os.Bundle;

public class FourGroupActivity extends BaseGroupActivity {
	private String TAG = RandomFragment.TAG;

	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		setContentView(R.layout.activity_group);
		changeFragmentAnOnResume(TAG, null);
	}

}
