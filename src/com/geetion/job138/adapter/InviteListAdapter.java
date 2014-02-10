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

public class InviteListAdapter extends ArrayAdapter<JobInfo> {
	private LayoutInflater mInflater;
	private int mResource = R.layout.item_invite;
	private Context context;

	public InviteListAdapter(Context context, List<JobInfo> list) {
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
		ImageView imageView = (ImageView) layout.findViewById(R.id.new_icon);
		TextView jobName = (TextView) layout.findViewById(R.id.job_name);
		TextView companyName = (TextView) layout.findViewById(R.id.company_name);
		if (jobInfo.getIsRead() == 1) {
			imageView.setVisibility(View.GONE);
		} else {
			imageView.setVisibility(View.VISIBLE);
		}
		jobName.setText(jobInfo.getHireName());
		companyName.setText(jobInfo.getCompany());
		layout.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(context, PosDetailActivity.class);
				intent.putExtra("id", jobInfo.getHireId());
				intent.putExtra("interviewId", jobInfo.getId());
				jobInfo.setIsRead(1);
				notifyDataSetChanged();
				context.startActivity(intent);
			}
		});
		return layout;
	}
}
