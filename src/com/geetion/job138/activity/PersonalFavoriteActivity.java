package com.geetion.job138.activity;

import java.util.ArrayList;
import java.util.List;
import com.liqi.job.R;
import com.geetion.job138.activity.PersonalApplyActivity.GetApplyTask;
import com.geetion.job138.activity.group.BaseGroupActivity;
import com.geetion.job138.adapter.JobRecordListAdapter;
import com.geetion.job138.fragment.BaseFragment;
import com.geetion.job138.fragment.PersonalCenterFragment;
import com.geetion.job138.model.JobInfo;
import com.geetion.job138.model.PageUtil;
import com.geetion.job138.service.JobManageService;
import com.geetion.job138.service.PersonInfoSave;
import com.geetion.job138.util.MyHttpException;
import com.geetion.job138.util.UIUtil;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;

import android.os.AsyncTask;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

public class PersonalFavoriteActivity extends BaseActivity {
	private ImageButton backButton;
	private PullToRefreshListView listView;
	private JobRecordListAdapter adapter;
	private TextView emptyText;
	private List<JobInfo> list = new ArrayList<JobInfo>();
	private boolean isPullUp = false;
	private PageUtil pageUtil = new PageUtil();
	private GetFavTask task;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.activity_personal_favorite);
		init();
	}

	public void init() {
		pageUtil.setPageSize(15);
		backButton = (ImageButton) findViewById(R.id.button_left);
		backButton.setOnClickListener(this);
		listView = (PullToRefreshListView) findViewById(R.id.pull_refresh_list);
		emptyText = (TextView) findViewById(R.id.empty_text);
		listView.setOnRefreshListener(new OnRefreshListener2<ListView>() {
			@Override
			public void onPullDownToRefresh(PullToRefreshBase refreshView) {
				String label = DateUtils.formatDateTime(getApplicationContext(), System.currentTimeMillis(), DateUtils.FORMAT_SHOW_TIME
						| DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);
				listView.getLoadingLayoutProxy(true, false).setLastUpdatedLabel("刷新时间:" + label);
				pageUtil.setPageNo(1);
				isPullUp = true;
				task = new GetFavTask();
				task.execute();
			}

			@Override
			public void onPullUpToRefresh(PullToRefreshBase refreshView) {
				pageUtil.setPageNo(pageUtil.getPageNo() + 1);
				Log.e("pageNo", pageUtil.getPageNo() + "");
				isPullUp = false;
				task = new GetFavTask();
				task.execute();
			}
		});
		adapter = new JobRecordListAdapter(context, list);
		listView.setAdapter(adapter);
		listView.setRefreshing();
	}

	@Override
	public void onClick(View v) {
		if (v == backButton) {
			finish();
		}
	}

	@Override
	protected void onDestroy() {
		if (task != null)
			task.cancel(true);
		super.onDestroy();
	}

	/**
	 * 获取收藏职位
	 */
	public class GetFavTask extends AsyncTask<String, Integer, List<JobInfo>> {
		protected void onPreExecute() {
		};

		@Override
		protected List<JobInfo> doInBackground(String... arg0) {
			try {
				List<JobInfo> list = JobManageService.getWorkFavorite(pageUtil, PersonInfoSave.memberInfo.getMemberId());
				return list;
			} catch (MyHttpException e) {
				Log.e("MyHttpException", e.getErrorCode());
				UIUtil.toast(context, e.getErrorMessage());
				runOnUiThread(new Runnable() {
					public void run() {
						listView.onRefreshComplete();
					}
				});
			}
			return null;
		}

		@Override
		protected void onPostExecute(List<JobInfo> result) {
			if (result != null && !result.isEmpty()) {
				if (isPullUp) {
					listView.setMode(Mode.BOTH);
					list.clear();
				} else {
					if (pageUtil.getPageNo() == pageUtil.getPageCount()) {
						listView.setMode(Mode.PULL_FROM_START);
					}
				}
				list.addAll(result);
				adapter.notifyDataSetChanged();
			}
			if (list.isEmpty()) {
				emptyText.setVisibility(View.VISIBLE);
			}
			listView.onRefreshComplete();
		}
	}
}
