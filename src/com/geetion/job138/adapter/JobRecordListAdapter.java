package com.geetion.job138.adapter;

import java.util.List;
import com.liqi.job.R;
import com.geetion.job138.activity.PosDetailActivity;
import com.geetion.job138.model.JobInfo;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class JobRecordListAdapter extends ArrayAdapter<JobInfo> {
	private LayoutInflater mInflater;
	private int mResource = R.layout.item_apply;
	private Context context;

	public JobRecordListAdapter(Context context, List<JobInfo> list) {
		super(context, 0, list);
		mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.context = context;
	}

	@Override
	public View getView(int position, View convertView, final ViewGroup parent) {
		View layout = convertView;
		ViewHolder holder;
		if (layout == null) {
			layout = mInflater.inflate(mResource, parent, false);
			holder = new ViewHolder(layout);
			layout.setTag(holder);
		} else {
			holder = (ViewHolder) layout.getTag();
		}
		final JobInfo jobInfo = getItem(position);
		holder.getCompanyName().setText(jobInfo.getCompany());
		holder.getJobName().setText(jobInfo.getHireName());
		holder.getLoc().setText(jobInfo.getArea());
		holder.getSalary().setText(jobInfo.getPay());
		holder.getDate().setText(jobInfo.getTime());
		layout.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(context, PosDetailActivity.class);
				intent.putExtra("id", jobInfo.getHireId());
				context.startActivity(intent);
			}
		});
		return layout;
	}

	public class ViewHolder {
		private View baseView;
		private TextView jobName;
		private TextView companyName;
		private TextView date;
		private TextView loc;
		private TextView salary;

		public ViewHolder(View baseView) {
			this.baseView = baseView;
		}

		public void setBaseView(View baseView) {
			this.baseView = baseView;
		}

		public TextView getJobName() {
			if (jobName == null)
				jobName = (TextView) baseView.findViewById(R.id.job_name);
			return jobName;
		}

		public TextView getCompanyName() {
			if (companyName == null)
				companyName = (TextView) baseView.findViewById(R.id.company_name);
			return companyName;
		}

		public TextView getDate() {
			if (date == null)
				date = (TextView) baseView.findViewById(R.id.date);
			return date;
		}

		public TextView getLoc() {
			if (loc == null)
				loc = (TextView) baseView.findViewById(R.id.loc);
			return loc;
		}

		public TextView getSalary() {
			if (salary == null)
				salary = (TextView) baseView.findViewById(R.id.salary);
			return salary;
		}

	}
}
