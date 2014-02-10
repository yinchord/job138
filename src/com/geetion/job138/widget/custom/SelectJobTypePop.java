package com.geetion.job138.widget.custom;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import com.liqi.job.R;
import com.geetion.job138.adapter.JobTypeListAdapter;
import com.geetion.job138.model.JobType;
import com.geetion.job138.service.SearchJobService;
import com.geetion.job138.service.StationTypeSave;
import com.geetion.job138.util.AndroidUtil;
import com.geetion.job138.util.FuncUtil;
import com.geetion.job138.util.MyHttpException;
import com.geetion.job138.util.UIUtil;
import com.geetion.job138.widget.custom.ParentLinearLayoutTouch.OnInterceptTouchListener;

import android.R.bool;
import android.R.integer;
import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class SelectJobTypePop extends PopupWindow implements OnClickListener {
	private View stationView;
	private ImageButton closeButton;
	private TextView cleanButton;
	private ListView stationListView, stationSubListView;
	private JobTypeListAdapter subAdapter, stationAdapter;
	private Activity context;
	private SelectJobType selectInterface;
	private ParentLinearLayoutTouch parentLayout;
	private ProgressBar progressBar;
	private GetJobTypeTask getJobTypeTask;
	private List<JobType> childs;
	private Handler handler = new Handler();
	private boolean isSingle = false;
	private HashMap<Integer, Boolean> clickMap = new HashMap<Integer, Boolean>();
	private HashMap<Integer, Boolean> clickAbleMap = new HashMap<Integer, Boolean>();
	private int Fid = 0;
	private  int mPoistion = 0;
	private String chooseIds = null;

	public interface SelectJobType {
		public void check(HashMap<Integer, JobType> chooseMap, HashMap<Integer, JobType> chooseSubMap);
		public void getJobType(JobType jobType);
	}

	public void setSelectJobTpe(SelectJobType selectJobType) {
		this.selectInterface = selectJobType;
	}

	public SelectJobTypePop(View view, final Activity context, boolean isSingle) {
		super(view, LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT, true);
		this.stationView = view;
		this.context = context;
		this.isSingle = isSingle;
		this.setOutsideTouchable(true);
		this.setTouchable(true);
		this.update();
		cleanButton = (TextView) stationView.findViewById(R.id.clean);
		closeButton = (ImageButton) stationView.findViewById(R.id.button_close);
		closeButton.setImageResource((isSingle ? R.drawable.close_icon : R.drawable.sure_icon));
		stationListView = (ListView) stationView.findViewById(R.id.list_station);
		stationSubListView = (ListView) stationView.findViewById(R.id.list_station_sub);
		progressBar = (ProgressBar) stationView.findViewById(R.id.progressBar1);
		closeButton.setOnClickListener(this);
		cleanButton.setOnClickListener(this);
		setAnimationStyle(R.style.PopupAnimation);
		parentLayout = (ParentLinearLayoutTouch) stationView.findViewById(R.id.parent_layout);
		parentLayout.setOnInterceptTouchListener(new OnInterceptTouchListener() {
			float downX = 0;

			@Override
			public boolean OnTouch(MotionEvent event) {
				if (event.getAction() == MotionEvent.ACTION_DOWN) {
					downX = event.getX();
					Log.e("downX", "" + downX);
				} else if (event.getAction() == MotionEvent.ACTION_MOVE) {
					if (event.getX() - downX > AndroidUtil.dpToPx(64, context)) {
						stationView.findViewById(R.id.shadow).setVisibility(View.INVISIBLE);
						stationSubListView.setVisibility(View.GONE);
						dismiss();
					}
					Log.e("moveX", "" + event.getX());
				}
				return false;
			}
		});
	}

	public void showPop(View station) {
		if (context.getCurrentFocus() != null) {
			((InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(context.getCurrentFocus().getWindowToken(),
					InputMethodManager.HIDE_NOT_ALWAYS);
		}
		getData();
		stationView.findViewById(R.id.shadow).setVisibility(View.INVISIBLE);
		this.showAtLocation(station, Gravity.RIGHT, 0, 0);
		handler.postDelayed(new Runnable() {
			public void run() {
				stationView.findViewById(R.id.shadow).setVisibility(View.VISIBLE);
			}
		}, 250);
	}

	public void getData() {
		initData();
	}

	public void initData() {
		childs = new ArrayList<JobType>();
		if(isSingle) {
			stationAdapter = new JobTypeListAdapter(stationListView.getContext(), StationTypeSave.parentJobTypes, R.layout.item_station, R.id.item_station_text,
					true, isSingle, this);
		} else {
			stationAdapter = new JobTypeListAdapter(stationListView.getContext(), StationTypeSave.parentJobTypes, R.layout.item_station_multi, R.id.item_station_text,
					true, isSingle, this);
		}
		
		stationListView.setAdapter(stationAdapter);
		subAdapter = new JobTypeListAdapter(stationSubListView.getContext(), childs, R.layout.item_station2, R.id.item_station2_text, false, isSingle, this);
		stationSubListView.setAdapter(subAdapter);
		stationSubListView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
		if (!StationTypeSave.isGetType()) {
			getJobTypeTask = new GetJobTypeTask();
			getJobTypeTask.execute();
		} else {
			refresh();
		}
//		stationListView.setOnItemClickListener(new StationItemListener());
//		stationSubListView.setOnItemClickListener(new SubItemListener());

	}

	@Override
	public void onClick(View v) {
		if (v == closeButton) {
			stationView.findViewById(R.id.shadow).setVisibility(View.INVISIBLE);
			stationSubListView.setVisibility(View.GONE);
			if(!isSingle) {
				if (selectInterface != null) {
					selectInterface.check(stationAdapter.getChoseMap(), subAdapter.getSubChoseMap());
				}
			}
			dismiss();
		} else if(v == cleanButton) {
			stationAdapter.removeChooseMap();
			subAdapter.removeSubChooseMap();
			subAdapter.removeChooseMap();
			clickAbleMap.clear();
			clickMap.clear();
			if(isSingle) {
				stationView.findViewById(R.id.shadow).setVisibility(View.INVISIBLE);
				stationSubListView.setVisibility(View.GONE);
				if (selectInterface != null) {
					selectInterface.getJobType(null);
				}
				dismiss();
			}
		}
	}
	
	

	@Override
	public void dismiss() {
		// TODO Auto-generated method stub
		if(getJobTypeTask != null) {
			getJobTypeTask.cancel(true);
		}
		clickAbleMap.clear();
		clickMap.clear();
		mPoistion = 0;
		super.dismiss();
	}



	public class GetJobTypeTask extends AsyncTask<Void, integer, List<JobType>> {
		List<JobType> list = new ArrayList<JobType>();

		@Override
		protected List<JobType> doInBackground(Void... params) {
			try {
				list = SearchJobService.getJobType();
				return list;
			} catch (MyHttpException e) {
				Log.e("MyHttpException", e.getErrorCode());
				UIUtil.toast(context, e.getErrorMessage());
			}
			return list;
		}

		@Override
		protected void onPostExecute(List<JobType> resultList) {
			if (!resultList.isEmpty()) {
				StationTypeSave.saveJobType(resultList);
				progressBar.setVisibility(View.GONE);
				refresh();
				stationAdapter.notifyDataSetChanged();
			}
		}

		@Override
		protected void onPreExecute() {
			progressBar.setVisibility(View.VISIBLE);
		}

	}

		public void onItemClick(int position) {
			mPoistion = position;
			int id = StationTypeSave.parentJobTypes.get(position).getId();
			Fid = id;
			List<JobType> l = StationTypeSave.childJobtypes.get(id);
			childs.clear();
			childs.addAll(l);
			subAdapter.notifyDataSetChanged();
			stationAdapter.setSelect(position);
			stationAdapter.notifyDataSetChanged();
			stationSubListView.setVisibility(View.VISIBLE);
		}


		public void onSubItemClick(int position) {
			if(clickMap.containsKey(Fid)) {
				if (clickMap.get(Fid)) {
					return;
				}
			}
			if (isSingle) {
				JobType jobType = childs.get(position);
				if (selectInterface != null) {
					selectInterface.getJobType(jobType);
				}
				stationView.findViewById(R.id.shadow).setVisibility(View.INVISIBLE);
				stationSubListView.setVisibility(View.GONE);
				dismiss();
			} else {
				int id = childs.get(position).getId();
				JobType jobType = childs.get(position);
				if (subAdapter.isSelect(id)) {
					stationAdapter.setMyCount(subAdapter.getSubSelectCount());
					clickAbleMap.put(jobType.getFid(), true);
					subAdapter.cleanSelect(id);
					subAdapter.cleanSubSelect(id);
					for (Entry<Integer, JobType> entry : subAdapter.getChoseMap().entrySet()) {						
						if (jobType.getFid() == entry.getValue().getFid()) {
							clickAbleMap.put(jobType.getFid(), false);	
							break;
						}				
					}					
				} else {
					if (stationAdapter.getSelectCount() + subAdapter.getSubSelectCount() >= 5) {
						UIUtil.toast(context, "最多只能选择5个！");
						return;
					}
					stationAdapter.setMyCount(subAdapter.getSubSelectCount());
					clickAbleMap.put(jobType.getFid(), false);
					subAdapter.addSelect(id, jobType);
					subAdapter.addSubSelect(id, jobType);
				}
				stationAdapter.notifyDataSetChanged();
				subAdapter.notifyDataSetChanged();
			}
		}
		
		public void onCheckClick(int position) {
			JobType jobType = StationTypeSave.parentJobTypes.get(position);
			int id = jobType.getId();
			List<JobType> l = StationTypeSave.childJobtypes.get(id);
			if(stationAdapter.isSelect(id)) {
				stationAdapter.cleanSelect(id);
				removeAll(l);
			} else {
				removeChlids(l);
				stationAdapter.addSelect(id, jobType);
				selectAll(l);
			}
			onItemClick(position);
		}
		
		private void selectAll(List<JobType> l) {
			for(int i = 0; i < l.size(); i++) {
				JobType child = l.get(i);
				int cId = child.getId();
				subAdapter.addSelect(cId, child);
			}
		}
		
		private void removeAll(List<JobType> l) {			
			for(int i = 0; i < l.size(); i++) {
				JobType child = l.get(i);
				int cId = child.getId();
				subAdapter.cleanSelect(cId);
			}
		}
		
		private void removeChlids(List<JobType> childs) {
			for(int i = 0; i < childs.size(); i++) {
				if(subAdapter.getChoseMap().containsKey(childs.get(i).getId())) {
					subAdapter.cleanSelect(childs.get(i).getId());
//					subCount --;
//					stationAdapter.setMyCount(subCount);
				}
			}
		} 
		
		public void addClickMap(int id, boolean isCheck) {
			clickMap.put(id, isCheck);
		} 
		
		public boolean clickAble(int id) {
			if(clickAbleMap.containsKey(id)) {
				return clickAbleMap.get(id);
			}
			return true;
		} 
		
		public void setChooseMap(HashMap<Integer, JobType> father, HashMap<Integer, JobType> child, HashMap<Integer, JobType> chooseChild) {
			stationAdapter.setChooseMap(father);
			subAdapter.setChooseMap(child);
			subAdapter.setChooseSubMap(chooseChild);
			stationAdapter.setSelect(mPoistion);
			stationAdapter.notifyDataSetChanged();
			subAdapter.notifyDataSetChanged();
		}

		public void setChooseIds(String chooseIds) {
			this.chooseIds = chooseIds;
		}
		
		public void refresh() {
			if(chooseIds != null && !TextUtils.isEmpty(chooseIds)) {
				List<List<String>> stringList = FuncUtil.parseStringToList(chooseIds);
				HashMap<Integer, JobType> stationChoose = FuncUtil.getJobParentMap(stringList.get(0));
				HashMap<Integer, JobType> subChoose = FuncUtil.getJobTypeChildMap(FuncUtil.parseChildSelct(stringList.get(1)));
				if(!stationChoose.isEmpty()) {
					stationAdapter.setChooseMap(stationChoose);
					for (Entry<Integer, JobType> entry : stationChoose.entrySet()) {
						addClickMap(entry.getKey(), true);
						subAdapter.addChooseMap(StationTypeSave.childJobtypes.get(entry.getValue().getId()));
					}
				}
				if (!subChoose.isEmpty()) {
					subAdapter.setChooseSubMap(subChoose);
					for (Entry<Integer, JobType> entry : subChoose.entrySet()) {
						clickAbleMap.put(entry.getValue().getFid(), false);
						subAdapter.addSelect(entry.getKey(), entry.getValue());
					}
				}
				onItemClick(mPoistion);
				return;
			}
		}
}
