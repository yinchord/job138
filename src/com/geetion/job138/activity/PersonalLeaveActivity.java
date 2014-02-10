package com.geetion.job138.activity;

import java.util.ArrayList;
import java.util.List;
import com.liqi.job.R;
import com.geetion.job138.adapter.LeaveListAdapter;
import com.geetion.job138.model.Comment;
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
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

public class PersonalLeaveActivity extends BaseActivity {
	private ImageButton backButton;
	private PullToRefreshListView listView;
	private TextView emptyText;
	private LeaveListAdapter adapter;
	private List<Comment> list = new ArrayList<Comment>();
	private boolean isPullUp = false;
	private PageUtil pageUtil = new PageUtil();
	private GetLeaveTask task;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.activity_personal_leave);
		init();
	}

	public void init() {
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
				task = new GetLeaveTask();
				task.execute();
			}

			@Override
			public void onPullUpToRefresh(PullToRefreshBase refreshView) {
				pageUtil.setPageNo(pageUtil.getPageNo() + 1);
				Log.e("pageNo", pageUtil.getPageNo() + "");
				isPullUp = false;
				task = new GetLeaveTask();
				task.execute();
			}
		});
		adapter = new LeaveListAdapter(this, list);
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
	 * 获取应聘职位
	 */
	public class GetLeaveTask extends AsyncTask<String, Integer, List<Comment>> {
		protected void onPreExecute() {
		};

		@Override
		protected List<Comment> doInBackground(String... arg0) {
			try {
				List<Comment> list = JobManageService.getCommentsList(pageUtil, PersonInfoSave.memberInfo.getMemberId());
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
		protected void onPostExecute(List<Comment> result) {
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
