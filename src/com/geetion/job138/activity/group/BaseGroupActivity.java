package com.geetion.job138.activity.group;

import com.liqi.job.R;
import com.geetion.job138.activity.BaseActivity;
import com.geetion.job138.activity.PosListActivity;
import com.geetion.job138.activity.PersonalApplyActivity;
import com.geetion.job138.activity.PersonalFavoriteActivity;
import com.geetion.job138.activity.PersonalInviteActivity;
import com.geetion.job138.activity.PersonalLeaveActivity;
import com.geetion.job138.activity.PersonalPasswordActivity;
import com.geetion.job138.activity.PersonalSeemActivity;
import com.geetion.job138.activity.PersonalStatusActivity;
import com.geetion.job138.activity.PersonalTelActivity;
import com.geetion.job138.fragment.IndexFragment;
import com.geetion.job138.fragment.LoginFragment;
import com.geetion.job138.fragment.PersonalCenterFragment;
import com.geetion.job138.fragment.RandomFragment;
import com.geetion.job138.fragment.SearchFragment;
import com.geetion.job138.fragment.SettingFragment;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;

public class BaseGroupActivity extends BaseActivity {
	private FragmentManager fm;
	private FragmentTransaction tr;
	private Fragment fragment;
	private String TAG;

	public Fragment changeFragmentAnOnResume(String tag, Bundle args) {
		fm = getSupportFragmentManager();
		tr = fm.beginTransaction();
		if (fragment != null)
			tr.remove(fragment);
		Fragment changeFragment = findFragment(tag);
		if (changeFragment.isAdded()) {
			tr.remove(changeFragment);
			changeFragment = findFragment(tag, true);
		}
		if (args != null)
			changeFragment.setArguments(args);
		tr.replace(R.id.content, changeFragment, tag);
		fragment = changeFragment;
		TAG = tag;
		tr.commitAllowingStateLoss();
		return changeFragment;
	}

	public Fragment findFragment(String tag) {
		return findFragment(tag, false);
	}

	public Fragment findFragment(String tag, boolean isAlwaysNew) {
		FragmentManager fm = getSupportFragmentManager();
		Fragment foundFragMent = fm.findFragmentByTag(tag);
		if (isAlwaysNew)
			foundFragMent = null;
		if (foundFragMent == null) {
			if (tag.equals(LoginFragment.TAG)) {
				foundFragMent = new LoginFragment();
			} else if (tag.equals(IndexFragment.TAG)) {
				foundFragMent = new IndexFragment();
			} else if (tag.equals(SearchFragment.TAG)) {
				foundFragMent = new SearchFragment();
			} else if (tag.equals(SettingFragment.TAG)) {
				foundFragMent = new SettingFragment();
			} else if (tag.equals(PersonalCenterFragment.TAG)) {
				foundFragMent = new PersonalCenterFragment();
			} else if (tag.equals(RandomFragment.TAG)) {
				foundFragMent = new RandomFragment();
			}
		}
		return foundFragMent;
	}

	@Override
	public void onClick(View v) {

	}

}
