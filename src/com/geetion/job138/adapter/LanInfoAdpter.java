package com.geetion.job138.adapter;

import java.util.List;

import com.liqi.job.R;
import com.geetion.job138.activity.MyResumeActivity;
import com.geetion.job138.adapter.EduInfoAdapter.DelEduInfoTask;
import com.geetion.job138.adapter.TrainInfoAdpter.DelTrainInfoTask;
import com.geetion.job138.fragment.resume.LanInfoChildFragment;
import com.geetion.job138.fragment.resume.LanInfoFragment;
import com.geetion.job138.fragment.resume.TrainInfoChildragment;
import com.geetion.job138.model.JobInfo;
import com.geetion.job138.model.Lang;
import com.geetion.job138.model.TrainExperience;
import com.geetion.job138.service.CacheService;
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

public class LanInfoAdpter extends ArrayAdapter<Lang> {
	private LayoutInflater inflater;
	private MyResumeActivity activity;
	private boolean isDel;
	private DelLangInfoTask delTask;

	public LanInfoAdpter(MyResumeActivity activity, List<Lang> list) {
		super(activity, 0, list);
		this.activity = activity;
		inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View layout = convertView;
		if (layout == null)
			layout = inflater.inflate(R.layout.item_resume, null);
		final Lang lang = getItem(position);
		TextView titleView = (TextView) layout.findViewById(R.id.title);
		titleView.setText(lang.getName());
		TextView dateView = (TextView) layout.findViewById(R.id.date);
		dateView.setText(CacheService.langMasterKeyMap.get(Integer.parseInt(lang.getMaster())));
		ImageButton btDelete = (ImageButton) layout.findViewById(R.id.button_delete);
		ImageButton btModify = (ImageButton) layout.findViewById(R.id.button_modify);
		btDelete.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (isDel) {
					UIUtil.toast(activity, "删除中请稍后...");
					return;
				}
				UIUtil.confirm((Activity) getContext(), "提示", "是否要删除?", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface arg0, int arg1) {
						delTask = new DelLangInfoTask();
						delTask.execute(lang);
					}
				});

			}
		});
		btModify.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Bundle bundle = new Bundle();
				bundle.putSerializable("lang", lang);
				activity.changeFragmentAnOnResume(LanInfoChildFragment.TAG, bundle);
			}
		});
		return layout;
	}

	public class DelLangInfoTask extends AsyncTask<Lang, Integer, Lang> {
		protected void onPreExecute() {
			activity.showLoadiing();
			isDel = true;
		};

		@Override
		protected Lang doInBackground(Lang... arg0) {
			try {
				ResumeManageService.delLang(PersonInfoSave.resumeInfo.getId(), arg0[0].getLangId());
				return arg0[0];
			} catch (MyHttpException e) {
				Log.e("MyHttpException", e.getErrorCode());
				UIUtil.toast(activity, e.getErrorMessage());
			}
			return null;
		}

		@Override
		protected void onPostExecute(Lang result) {
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
