package com.geetion.job138.fragment;

import java.util.List;

import com.google.gson.internal.bind.ReflectiveTypeAdapterFactory.Adapter;
import com.liqi.job.R;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public abstract class BaseFragment extends Fragment implements OnClickListener {

	public void closeInput() {
		if (getActivity().getCurrentFocus() != null) {
			((InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(getActivity().getCurrentFocus()
					.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
		}
	}

	@Override
	public void onDestroy() {
		closeInput();
		View view = getActivity().findViewById(R.id.loading);
		if (view != null)
			view.setVisibility(View.GONE);
		super.onDestroy();
	}
}
