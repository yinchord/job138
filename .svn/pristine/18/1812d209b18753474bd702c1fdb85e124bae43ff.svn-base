package com.geetion.job138.adapter;

import java.util.List;

import java.util.zip.Inflater;
import com.liqi.job.R;
import com.geetion.job138.activity.MyResumeActivity;
import com.geetion.job138.adapter.TrainInfoAdpter.DelTrainInfoTask;
import com.geetion.job138.fragment.resume.JobInfoChildFragment;
import com.geetion.job138.fragment.resume.TrainInfoChildragment;
import com.geetion.job138.model.JobInfo;
import com.geetion.job138.model.Lang;
import com.geetion.job138.model.TrainExperience;
import com.geetion.job138.model.WorkExperience;
import com.geetion.job138.service.PersonInfoSave;
import com.geetion.job138.service.ResumeManageService;
import com.geetion.job138.util.MyHttpException;
import com.geetion.job138.util.UIUtil;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

public class JobInfoAdpter extends ArrayAdapter<WorkExperience> {
	private MyResumeActivity activity;
	private boolean isDel;
	private LayoutInflater inflater;
	private DelWorkExperienceInfoTask delTask;

	public JobInfoAdpter(MyResumeActivity activity, List<WorkExperience> list) {
		super(activity, 0, list);
		this.activity = activity;
		inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View layout = convertView;
		if (layout == null)
			layout = inflater.inflate(R.layout.item_resume, null);
		final WorkExperience workExperience = getItem(position);
		TextView titleView = (TextView) layout.findViewById(R.id.title);
		titleView.setText(workExperience.getPosition() + " - " + workExperience.getComName());
		TextView dateView = (TextView) layout.findViewById(R.id.date);
		String dateTime = workExperience.getStartYear() + "-" + workExperience.getStartMonth() + " ~ " + workExperience.getEndYear() + " - "
				+ workExperience.getEndMonth();
		dateView.setText(dateTime);
		ImageButton btDelete = (ImageButton) layout.findViewById(R.id.button_delete);
		ImageButton btModify = (ImageButton) layout.findViewById(R.id.button_modify);
		btDelete.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (isDel) {
					UIUtil.toast(activity, "删除中请稍后...");
					return;
				}
				UIUtil.confirm((Activity)getContext(), "提示", "是否要删除?", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface arg0, int arg1) {
						delTask = new DelWorkExperienceInfoTask();
						delTask.execute(workExperience);
					}
				});
			}
		});
		btModify.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Bundle bundle = new Bundle();
				bundle.putSerializable("workExperience", workExperience);
				activity.changeFragmentAnOnResume(JobInfoChildFragment.TAG, bundle);
			}
		});
		return layout;
	}

	public class DelWorkExperienceInfoTask extends AsyncTask<WorkExperience, Integer, WorkExperience> {
		protected void onPreExecute() {
			activity.showLoadiing();
			isDel = true;
		};

		@Override
		protected WorkExperience doInBackground(WorkExperience... arg0) {
			try {
				ResumeManageService.delWorkExorience(PersonInfoSave.resumeInfo.getId(), arg0[0].getWorkId());
				return arg0[0];
			} catch (MyHttpException e) {
				Log.e("MyHttpException", e.getErrorCode());
				UIUtil.toast(activity, e.getErrorMessage());
			}
			return null;
		}

		@Override
		protected void onPostExecute(WorkExperience result) {
			activity.hideLoading();
			isDel = false;
			if (result != null) {
				remove(result);
				PersonInfoSave.resumeInfo.getEduList().remove(result);
				notifyDataSetChanged();
				UIUtil.toast(activity, "删除成功");
			}
		}
	}
}
