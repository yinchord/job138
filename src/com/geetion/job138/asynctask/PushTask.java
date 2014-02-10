package com.geetion.job138.asynctask;

import java.util.List;

import com.geetion.job138.activity.JobInfoSingleDetailActivity;
import com.geetion.job138.activity.PersonalInviteActivity;
import com.geetion.job138.model.JobInfo;
import com.geetion.job138.model.MemberMessage;
import com.geetion.job138.service.PersonInfoSave;
import com.geetion.job138.service.PushService;
import com.geetion.job138.util.MyHttpException;
import com.geetion.job138.util.UIUtil;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;

public class PushTask extends AsyncTask {
	private MemberMessage memberInfo = PersonInfoSave.memberInfo;
	private List<JobInfo> interViews, orderHires;
	private Context context;

	public PushTask(Context context) {
		this.context = context;
	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
	}

	@Override
	protected Object doInBackground(Object... arg0) {
		if (PushService.isInterview) {
			try {
				interViews = PushService.getInterviewPush(memberInfo
						.getMemberId());
			} catch (MyHttpException e) {
				e.printStackTrace();
			}
		}
		if (PushService.isOrderHir) {
			try {
				orderHires = PushService.getOrderHire(memberInfo.getMemberId());
			} catch (MyHttpException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	@Override
	protected void onPostExecute(Object result) {
		if (interViews != null) {
			if (interViews.size() > 0) {
				String message = memberInfo.getUserName() + ":恭喜您收到"
						+ interViews.get(0).getCompany()
						+ interViews.get(0).getHireName() + "的面试通知！点击查看。";
				UIUtil.showAlert(context, "面试通知", message,
						new android.content.DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								Intent intent = new Intent(context,
										PersonalInviteActivity.class);
								intent.putExtra("type", "interViews");
								context.startActivity(intent);
							}
						});
			}
		}
		if (orderHires != null) {
			if (orderHires.size() > 0) {
				String message = memberInfo.getUserName() + ":根据您的求职意向，138为您推荐"
						+ orderHires.get(0).getCompanyName()
						+ orderHires.get(0).getHireName() + "的工作，点击查看。";
				UIUtil.showAlert(context, "简历订阅", message,
						new android.content.DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								Intent intent = new Intent(context,
										JobInfoSingleDetailActivity.class);
								intent.putExtra("jobInfo", orderHires.get(0));
								context.startActivity(intent);
							}
						});
			}
		}
	}
}