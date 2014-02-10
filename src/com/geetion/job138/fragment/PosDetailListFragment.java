package com.geetion.job138.fragment;

import java.util.ArrayList;
import java.util.List;

import com.liqi.job.R;
import com.geetion.job138.activity.BaseActivity;
import com.geetion.job138.activity.JobInfoSingleDetailActivity;
import com.geetion.job138.adapter.PosDetailListAdapter;
import com.geetion.job138.model.Company;
import com.geetion.job138.model.JobInfo;
import com.geetion.job138.model.PageUtil;
import com.geetion.job138.service.JobInfoService;
import com.geetion.job138.util.MyHttpException;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class PosDetailListFragment extends BaseFragment {
	public static String TAG = PosDetailListFragment.class.getName();
	private PullToRefreshListView listView;
	private PosDetailListAdapter adapter;
	private List<JobInfo> list = new ArrayList<JobInfo>();
	private AsyncTask listTask;
	private Company company;
	private PageUtil pageUtil = new PageUtil();
	private boolean isRefresh;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_pos_detail_list, container, false);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		initView();
		initData();
		super.onActivityCreated(savedInstanceState);
	}

	@Override
	public void onDestroy() {
		if (listTask != null) {
			listTask.cancel(true);
		}
		super.onDestroy();
	}

	private void initView() {
		listView = (PullToRefreshListView) getActivity().findViewById(R.id.pull_refresh_list);
	}

	private void initData() {
		Bundle bundle = getArguments();
		if (bundle != null)
			company = (Company) bundle.get("company");
		adapter = new PosDetailListAdapter(getActivity(), list);
		listView.getLoadingLayoutProxy(true, false).setPullLabel("下拉刷新...");
		listView.getLoadingLayoutProxy(false, true).setPullLabel("上拉加载更多...");
		listView.getLoadingLayoutProxy(true, true).setRefreshingLabel("数据加载中...");
		listView.setAdapter(adapter);
		listView.setOnRefreshListener(new OnRefreshListener2<ListView>() {
			@Override
			public void onPullDownToRefresh(PullToRefreshBase refreshView) {
				String label = DateUtils.formatDateTime(getActivity(), System.currentTimeMillis(), DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE
						| DateUtils.FORMAT_ABBREV_ALL);
				listView.getLoadingLayoutProxy(true, false).setLastUpdatedLabel("刷新时间:" + label);
				pageUtil.setPageNo(1);
				isRefresh = true;
				listTask = new ListTask().execute();
			}

			@Override
			public void onPullUpToRefresh(PullToRefreshBase refreshView) {
				pageUtil.setPageNo(pageUtil.getNextPage());
				listTask = new ListTask().execute();
			}
		});
		listView.setRefreshing();
	}

	class ListTask extends AsyncTask {
		List<JobInfo> addList;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
		}

		@Override
		protected Object doInBackground(Object... params) {
			try {
				if (company != null)
					addList = JobInfoService.getOtherJobInfo(1, pageUtil, company.getId());
			} catch (MyHttpException e) {
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPostExecute(Object result) {
			listView.onRefreshComplete();
			if (addList != null) {
				if (isRefresh) {
					isRefresh = false;
					listView.setMode(Mode.BOTH);
					list.clear();
				} else {
					listView.setMode(Mode.PULL_FROM_START);
				}
				list.addAll(addList);
				adapter.notifyDataSetChanged();
			}
		}
	}

	@Override
	public void onClick(View v) {
	}
}
