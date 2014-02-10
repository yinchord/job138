package com.geetion.job138.adapter;

import java.util.List;

import com.liqi.job.R;
import com.geetion.job138.activity.JobInfoSingleDetailActivity;
import com.geetion.job138.model.JobInfo;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class PosDetailListAdapter extends ArrayAdapter<JobInfo> {
	private LayoutInflater mInflater;
	private int mResource = R.layout.item_detail_pos;

	public PosDetailListAdapter(Context context, List<JobInfo> list) {
		super(context, 0, list);
		mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public View getView(int position, View convertView, final ViewGroup parent) {
		View layout = convertView;
		if (layout == null) {
			layout = mInflater.inflate(mResource, parent, false);
		}
		final JobInfo jobInfo = getItem(position);
		TextView name = (TextView) layout.findViewById(R.id.hirename);
		name.setText(jobInfo.getHireName());
		TextView info = (TextView) layout.findViewById(R.id.info);
		info.setText("(" + jobInfo.getArea() + ") (" + jobInfo.getNumber() + "人)");
		layout.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(getContext(), JobInfoSingleDetailActivity.class);
				intent.putExtra("id", jobInfo.getHireId());
				getContext().startActivity(intent);
			}
		});
		return layout;
	}
}
