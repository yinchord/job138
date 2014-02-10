package com.geetion.job138.adapter;

import java.util.List;
import com.liqi.job.R;
import com.geetion.job138.activity.PosDetailActivity;
import com.geetion.job138.activity.TabWidgetActivity;
import com.geetion.job138.model.JobInfo;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class RandomListAdapter extends ArrayAdapter<JobInfo> {
	private LayoutInflater mInflater;
	private int mResource = R.layout.item_random;
	private Context context;

	public RandomListAdapter(Context context, List<JobInfo> list) {
		super(context, 0, list);
		mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.context = context;
	}

	@Override
	public View getView(int position, View convertView, final ViewGroup parent) {
		View layout = convertView;
		if (layout == null) {
			layout = mInflater.inflate(mResource, parent, false);
		}
		final JobInfo jobInfo = getItem(position);
		TextView signView = (TextView) layout.findViewById(R.id.sign);
		TextView jobNameView = (TextView) layout.findViewById(R.id.job_name);
		if (jobInfo.getType() == 2) {
			signView.setText("中上签 - ");
		} else {
			signView.setText("上上签 - ");
		}
		jobNameView.setText(jobInfo.getHireName());
		return layout;
	}
}
