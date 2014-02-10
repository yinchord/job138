package com.geetion.job138.fragment;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.liqi.job.R;
import com.geetion.job138.activity.BaseActivity;
import com.geetion.job138.activity.MyResumeActivity;
import com.geetion.job138.activity.PersonalApplyActivity;
import com.geetion.job138.activity.PersonalFavoriteActivity;
import com.geetion.job138.activity.PersonalInviteActivity;
import com.geetion.job138.activity.PersonalLeaveActivity;
import com.geetion.job138.activity.PersonalPasswordActivity;
import com.geetion.job138.activity.PersonalSeemActivity;
import com.geetion.job138.activity.PersonalStatusActivity;
import com.geetion.job138.activity.PersonalTelActivity;
import com.geetion.job138.activity.ViewImageActivity;
import com.geetion.job138.activity.group.BaseGroupActivity;
import com.geetion.job138.imagecache.ImageLoader;
import com.geetion.job138.model.MemberMessage;
import com.geetion.job138.model.ResumeInfo;
import com.geetion.job138.service.CacheService;
import com.geetion.job138.service.IndexPageService;
import com.geetion.job138.service.PersonInfoSave;
import com.geetion.job138.service.ResumeInfoService;
import com.geetion.job138.service.ResumeManageService;
import com.geetion.job138.service.SettingService;
import com.geetion.job138.service.UploadPhotoService;
import com.geetion.job138.util.AndroidUtil;
import com.geetion.job138.util.FileUtil;
import com.geetion.job138.util.MyHttpException;
import com.geetion.job138.util.PhotoUtil;
import com.geetion.job138.util.UIUtil;

import android.R.integer;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.LinearLayout.LayoutParams;

public class PersonalCenterFragment extends BaseFragment {
	public static String TAG = PersonalCenterFragment.class.getName();
	private BaseGroupActivity activity;
	private ImageButton registerButton, noChooseButton, refreshButton, updateButton, applyButton, secrecyButton, photoButton;
	private int[] buttonIDs = { R.id.resume, R.id.work, R.id.setting };
	private int[] arrowIDs = { R.id.arrow_resume, R.id.arrow_work, R.id.arrow_setting };
	private int[] layoutIDs = { R.id.resume_layout, R.id.work_layout, R.id.setting_layout };
	private RelativeLayout noPicLayout, picLayout, myResumeLayout, headImgLayout;
	private LinearLayout photoShowLayout;
	private TextView inviteView, applyView, favoriteView, seemView, leaveView, passwordView, telView, statusView, titleView, emailView, topStatusView,
			topTelView, resumeTimeView, myResumeViewsd, myResumeView, photoView, dialogTitleView;
	private GetMemberMessageTask task;
	private ImageView avaterView;
	private GetMemberPhotoTask photoTask;
	private ImageLoader imageLoader;
	private GetResumeMessageTask messageTask;
	private GetIndexPeronTask indexPersonTask;
	private GoRefreshTask goRefreshTask;
	private GoSecrecyTask goSecrecyTask;
	private GoAdvanceTask goAdvanceTask;
	private Dialog refreshDialog;
	private Dialog applyDialog, secercyDialog;
	private SimpleDateFormat f = new SimpleDateFormat("yyyy.MM.dd");
	private TextView refreshTimeView;
	private String percent;
	private Uri photo;
	private File renameFile;
	private boolean photoFlag;// 判断头像照还是个人风采照,true代表头像
	private AsyncTask uploadImg, uploadAvatar;
	private int resultIsOpen;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_personal_center, container, false);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		activity = (BaseGroupActivity) getActivity();
		init();
		getData();
		super.onActivityCreated(savedInstanceState);
	}

	public void getData() {
		task = new GetMemberMessageTask();
		task.execute();
		photoTask = new GetMemberPhotoTask();
		photoTask.execute();
		messageTask = new GetResumeMessageTask();
		messageTask.execute();
		indexPersonTask = new GetIndexPeronTask();
		indexPersonTask.execute();
	}

	public void init() {
		imageLoader = new ImageLoader(activity);
		for (int i = 0; i < 3; i++) {
			Button button = (Button) getView().findViewById(buttonIDs[i]);
			button.setOnClickListener(this);
		}
		photoButton = (ImageButton) getView().findViewById(R.id.choose_photo);
		photoButton.setOnClickListener(this);
		photoView = (TextView) getView().findViewById(R.id.photo_title);
		photoView.setOnClickListener(this);
		avaterView = (ImageView) getView().findViewById(R.id.headImg);
		headImgLayout = (RelativeLayout) getView().findViewById(R.id.headImgLayout);
		headImgLayout.setOnClickListener(this);
		noPicLayout = (RelativeLayout) getView().findViewById(R.id.no_pic_layout);
		noPicLayout.setOnClickListener(this);
		noChooseButton = (ImageButton) getView().findViewById(R.id.choose_photo);
		noChooseButton.setOnClickListener(this);
		picLayout = (RelativeLayout) getView().findViewById(R.id.pic_layout);
		inviteView = (TextView) getView().findViewById(R.id.invite);
		((RelativeLayout)inviteView.getParent()).setOnClickListener(this);
		applyView = (TextView) getView().findViewById(R.id.apply_record);
		((RelativeLayout)applyView.getParent()).setOnClickListener(this);
		favoriteView = (TextView) getView().findViewById(R.id.favorite);
		((RelativeLayout)favoriteView.getParent()).setOnClickListener(this);
		seemView = (TextView) getView().findViewById(R.id.seem);
		((RelativeLayout)seemView.getParent()).setOnClickListener(this);
		leaveView = (TextView) getView().findViewById(R.id.leave);
		((RelativeLayout)leaveView.getParent()).setOnClickListener(this);
		passwordView = (TextView) getView().findViewById(R.id.password);
		passwordView.setOnClickListener(this);
		telView = (TextView) getView().findViewById(R.id.tel_status);
		telView.setOnClickListener(this);
		statusView = (TextView) getView().findViewById(R.id.apply_status);
		statusView.setOnClickListener(this);
		titleView = (TextView) getView().findViewById(R.id.title);
		titleView.setText(SettingService.loginMessage.getUserName());
		emailView = (TextView) getView().findViewById(R.id.email);
		topStatusView = (TextView) getView().findViewById(R.id.status);
		topTelView = (TextView) getView().findViewById(R.id.tel);
		topTelView.setOnClickListener(this);
		photoShowLayout = (LinearLayout) getView().findViewById(R.id.photo_show);
		resumeTimeView = (TextView) getView().findViewById(R.id.resume_time);
		myResumeLayout = (RelativeLayout) getView().findViewById(R.id.my_resume_layout);
		activity.setTotalLoading(4);
		refreshButton = (ImageButton) getView().findViewById(R.id.refresh);
		refreshButton.setOnClickListener(this);
		updateButton = (ImageButton) getView().findViewById(R.id.update);
		updateButton.setOnClickListener(this);
		secrecyButton = (ImageButton) getView().findViewById(R.id.secrecy);
		secrecyButton.setOnClickListener(this);
		applyButton = (ImageButton) getView().findViewById(R.id.apply);
		applyButton.setOnClickListener(this);
		myResumeView = (TextView) getView().findViewById(R.id.my_resume);
	}

	@Override
	public void onClick(View v) {
		if (v == registerButton) {
			// activity.changeFragmentAnOnResume(RegisterFragment.TAG, null);
		} else if (v.getId() == buttonIDs[0]) {
			changeLayout(0);
		} else if (v.getId() == buttonIDs[1]) {
			changeLayout(1);
		} else if (v.getId() == buttonIDs[2]) {
			changeLayout(2);
		} else if (v == noChooseButton) {
			photoFlag = false;
			addPicture();
		} else if (v == applyView.getParent()) {
			Intent intent = new Intent(activity, PersonalApplyActivity.class);
			startActivity(intent);
		} else if (v == favoriteView.getParent()) {
			Intent intent = new Intent(activity, PersonalFavoriteActivity.class);
			startActivity(intent);
		} else if (v == inviteView.getParent()) {
			Intent intent = new Intent(activity, PersonalInviteActivity.class);
			startActivity(intent);
		} else if (v == seemView.getParent()) {
			Intent intent = new Intent(activity, PersonalSeemActivity.class);
			startActivity(intent);
		} else if (v == leaveView.getParent()) {
			Intent intent = new Intent(activity, PersonalLeaveActivity.class);
			startActivity(intent);
		} else if (v == passwordView) {
			Intent intent = new Intent(activity, PersonalPasswordActivity.class);
			startActivity(intent);
		} else if (v == telView || v == topTelView) {
			Intent intent = new Intent(activity, PersonalTelActivity.class);
			startActivity(intent);
		} else if (v == statusView) {
			Intent intent = new Intent(activity, PersonalStatusActivity.class);
			startActivity(intent);
		} else if (v == myResumeLayout || v == updateButton) {
			Intent intent = new Intent(activity, MyResumeActivity.class);
			startActivity(intent);
		} else if (v == refreshButton) {
			goRefreshTask = new GoRefreshTask();
			goRefreshTask.execute();
		} else if (v == secrecyButton) {
			if (secercyDialog == null)
				initSecrecyDialog();
			if (resultIsOpen == 1) {
				dialogTitleView.setText("将简历设置为保密状态?");
			} else {
				dialogTitleView.setText("将简历设置为公开状态?");
			}
			secercyDialog.show();
		} else if (v == applyButton) {
			if (applyDialog == null)
				initApplyDialog();
			applyDialog.show();
		} else if (v == headImgLayout) {
			photoFlag = true;
			addPicture();
		} else if (v == photoView) {
			photoFlag = false;
			addPicture();
		} else if (v == photoButton) {
			photoFlag = false;
			addPicture();
		}
	}

	private void addPicture() {
		{
			String title[] = new String[] { "照相", "相册", "取消" };
			AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
			builder.setItems(title, new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					switch (which) {
					case 0:
						PhotoUtil.takePhotoByCamera(PersonalCenterFragment.this);
						break;
					case 1:
						PhotoUtil.choesePhoto(PersonalCenterFragment.this);
						break;
					case 2:
						dialog.dismiss();
					}
				}
			});
			builder.show();
		}
	}

	public void changeLayout(int num) {
		for (int i = 0; i < 3; i++) {
			Button button = (Button) getView().findViewById(buttonIDs[i]);
			button.setBackgroundResource(R.drawable.bg_button_main_0);
			View arrow = getView().findViewById(arrowIDs[i]);
			arrow.setVisibility(View.GONE);
			View layout = getView().findViewById(layoutIDs[i]);
			layout.setVisibility(View.GONE);
		}

		Button button = (Button) getView().findViewById(buttonIDs[num]);
		button.setBackgroundResource(R.drawable.bg_button_main_1);
		View arrow = getView().findViewById(arrowIDs[num]); 
		arrow.setVisibility(View.VISIBLE);
		View layout = getView().findViewById(layoutIDs[num]);
		layout.setVisibility(View.VISIBLE);
	}

	@Override
	public void onDestroy() {
		if (task != null)
			task.cancel(false);
		if (photoTask != null)
			photoTask.cancel(false);
		if (indexPersonTask != null)
			indexPersonTask.cancel(false);
		if (messageTask != null)
			messageTask.cancel(false);
		if (goRefreshTask != null)
			goRefreshTask.cancel(true);
		if (goAdvanceTask != null)
			goAdvanceTask.cancel(true);
		if (goSecrecyTask != null) {
			goSecrecyTask.cancel(true);
		}
		if (uploadImg != null) {
			uploadImg.cancel(true);
		}
		if (uploadAvatar != null) {
			uploadAvatar.cancel(true);
		}
		super.onDestroy();
	}

	/**
	 * 获取个人风采
	 */
	public class GetMemberPhotoTask extends AsyncTask<Void, Integer, List<Map<String, String>>> {
		protected void onPreExecute() {
			activity.showLoadiing();
		};

		@Override
		protected List<Map<String, String>> doInBackground(Void... arg0) {
			try {
				List<Map<String, String>> photoList = ResumeInfoService.getPersonImage(PersonInfoSave.memberInfo.getMemberId());
				return photoList;
			} catch (MyHttpException e) {
				Log.e("MyHttpException", e.getErrorCode());
				UIUtil.toast(activity, e.getErrorMessage());
			}
			return null;
		}

		@Override
		protected void onPostExecute(List<Map<String, String>> photoList) {
			activity.hideLoading();
			if (photoList == null || photoList.isEmpty()) {
				noPicLayout.setVisibility(View.VISIBLE);
				picLayout.setVisibility(View.GONE);
			} else {
				noPicLayout.setVisibility(View.GONE);
				picLayout.setVisibility(View.VISIBLE);
				addPhoto(photoList);
			}
		}
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode != BaseActivity.RESULT_OK) {
			return;
		}
		switch (requestCode) {
		case PhotoUtil.PHOTO_PICKED_WITH_DATA:
			photo = data.getData();
			renameFile = FileUtil.saveFile(photo, PhotoUtil.getSavePath("/JOB138/image/"), "temp", getActivity());
			if (photoFlag) {
				cropPhoto();
			} else {
				compressBitmap(renameFile);
				uploadImg = new UpLoadImg().execute();
			}
			break;
		case PhotoUtil.CAMERA_WITH_DATA:
			renameFile = PhotoUtil.getSaveFile(activity);
			if (photoFlag) {
				photo = Uri.fromFile(renameFile);
				cropPhoto();
			} else {
				compressBitmap(renameFile);
				uploadImg = new UpLoadImg().execute();
			}
			break;
		case PhotoUtil.PHOTO_CROP_WITH_DATA:
			renameFile = FileUtil.saveFile(photo, PhotoUtil.getSavePath("/JOB138/image/"), "temp.jpg", getActivity());
			compressBitmap(renameFile);
			uploadAvatar = new UpLoadAvatar().execute();
			break;
		}
	}

	// 图片压缩
	public void compressBitmap(File file) {
		photo = Uri.fromFile(file);
		Bitmap bitmap = PhotoUtil.loadPhoto(photo, getActivity(), 300, 0);
		PhotoUtil.saveBitmap(bitmap, file);
	}

	void cropPhoto() {
		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setDataAndType(photo, "image/*");
		// aspectX aspectY 是宽高的比例
		intent.putExtra("crop", "true");
		intent.putExtra("aspectX", 5);
		intent.putExtra("aspectY", 6);
		// outputX outputY 是裁剪图片宽高
		intent.putExtra("outputX", 400);
		intent.putExtra("outputY", 480);
		intent.putExtra("outputFormat", "JPEG");
		intent.putExtra("noFaceDetection", true);
		 //intent.putExtra("return-data", true);
		intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(FileUtil.saveFile(photo, PhotoUtil.getSavePath("/JOB138/image/"), "temp.jpg", getActivity())));
		startActivityForResult(intent, PhotoUtil.PHOTO_CROP_WITH_DATA);
	}

	public void addPhoto(List<Map<String, String>> list) {
		photoShowLayout.removeAllViews();
		final List<String> imageList = new ArrayList<String>();
		final List<String> smallImageList = new ArrayList<String>();
		for (Map<String, String> map : list) {
			imageList.add(map.get("Image"));
			smallImageList.add(map.get("SmallImage"));
		}
		for (int i = 0; i < list.size(); i++) {
			final ImageView imageView = new ImageView(activity);
			LinearLayout.LayoutParams params = new LayoutParams(AndroidUtil.dpToPx(50, getActivity()), AndroidUtil.dpToPx(60, getActivity()));
			params.setMargins(AndroidUtil.dpToPx(5, activity), 0, AndroidUtil.dpToPx(5, activity), 0);
			imageView.setBackgroundResource(R.drawable.gray_border);
			imageView.setPadding(2, 2, 2, 2);
			imageView.setLayoutParams(params);
			imageView.setScaleType(ScaleType.CENTER_CROP);
			String url = list.get(i).get("SmallImage");
			// String url =
			// "http://t12.baidu.com/it/u=2252355338,565609814&fm=58";
			imageLoader.DisplayImage(url, imageView, R.drawable.image_avatar_0, 0, 0);
			photoShowLayout.addView(imageView);
			imageView.setTag(Integer.valueOf(i));
			imageView.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View arg0) {
					Intent intent = new Intent(getActivity(), ViewImageActivity.class);
					intent.putStringArrayListExtra(ViewImageActivity.IMAGE, (ArrayList<String>) imageList);
					intent.putStringArrayListExtra(ViewImageActivity.SMALL_IMAGE, (ArrayList<String>) smallImageList);
					intent.putExtra(ViewImageActivity.POSITION, (Integer) imageView.getTag());
					startActivity(intent);
				}
			});
		}
	}

	/**
	 * 获取会员信息
	 */
	public class GetMemberMessageTask extends AsyncTask<Void, Integer, MemberMessage> {
		protected void onPreExecute() {
			activity.showLoadiing();
		};

		@Override
		protected MemberMessage doInBackground(Void... arg0) {
			try {
				Log.e("memberId", PersonInfoSave.memberInfo.getMemberId() + "");
				MemberMessage message = ResumeInfoService.getPersonalInfo(PersonInfoSave.memberInfo.getMemberId(), PersonInfoSave.resumeInfo.getId());
				return message;
			} catch (MyHttpException e) {
				Log.e("MyHttpException", e.getErrorCode());
				UIUtil.toast(activity, e.getErrorMessage());
			}
			return null;
		}

		@Override
		protected void onPostExecute(MemberMessage memberInfo) {
			activity.hideLoading();
			if (memberInfo == null)
				return;
			PersonInfoSave.saveMemberMessage(memberInfo);
			resetMessage();
		}
	}

	public void resetMessage() {
		emailView.setText(PersonInfoSave.memberInfo.getEmail());
		topTelView.setText(PersonInfoSave.memberInfo.getMobile());
		if (!TextUtils.isEmpty(PersonInfoSave.memberInfo.getStatus()))
			topStatusView.setText(PersonInfoSave.memberInfo.getStatus().split("\\|")[0]);
		else
			topStatusView.setText("未设置");
		if (!TextUtils.isEmpty(PersonInfoSave.memberInfo.getAvatar()))
			imageLoader.DisplayImage(PersonInfoSave.memberInfo.getAvatar(), avaterView, R.drawable.image_avatar_0, AndroidUtil.dpToPx(50, activity),
					AndroidUtil.dpToPx(60, activity));
		if (!TextUtils.isEmpty(PersonInfoSave.resumeInfo.getDate()) && !TextUtils.isEmpty(PersonInfoSave.resumeInfo.getRefreshDate())) {
			resumeTimeView.setVisibility(View.VISIBLE);
			resumeTimeView.setText("创建时间: " + PersonInfoSave.resumeInfo.getDate() + "    刷新时间: " + PersonInfoSave.resumeInfo.getRefreshDate());
		}
	}

	public void initRefreshDialog() {
		refreshDialog = activity.initDialog(R.layout.dialog_refresh_tips);
		refreshTimeView = (TextView) refreshDialog.findViewById(R.id.textView2);
		ImageButton closeButton = (ImageButton) refreshDialog.findViewById(R.id.close_button);
		closeButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				refreshDialog.dismiss();
			}
		});
	}

	public void initSecrecyDialog() {
		secercyDialog = activity.initDialog(R.layout.dialog_apply_tips);
		dialogTitleView = (TextView) secercyDialog.findViewById(R.id.title);
		ImageButton closeButton = (ImageButton) secercyDialog.findViewById(R.id.close_button);
		closeButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				secercyDialog.dismiss();
			}
		});
		ImageButton applyButton = (ImageButton) secercyDialog.findViewById(R.id.applyButton);
		applyButton.setImageResource(R.drawable.button_done_notice);
		applyButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				goSecrecyTask = new GoSecrecyTask();
				goSecrecyTask.execute();
				secercyDialog.dismiss();
			}
		});
	}

	public void initApplyDialog() {
		applyDialog = activity.initDialog(R.layout.dialog_apply_tips);
		ImageButton closeButton = (ImageButton) applyDialog.findViewById(R.id.close_button);
		closeButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				applyDialog.dismiss();
			}
		});
		ImageButton applyButton = (ImageButton) applyDialog.findViewById(R.id.applyButton);
		applyButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				goAdvanceTask = new GoAdvanceTask();
				goAdvanceTask.execute();
				applyDialog.dismiss();
			}
		});
		TextView titleView = (TextView) applyDialog.findViewById(R.id.title);
		titleView.setText("工作经验5年以上可申请高级人才, 请确认申请");
	}

	/**
	 * 获取简历信息
	 */
	public class GetResumeMessageTask extends AsyncTask<Void, Integer, ResumeInfo> {
		protected void onPreExecute() {
			activity.showLoadiing();
		};

		@Override
		protected ResumeInfo doInBackground(Void... arg0) {
			try {
				Log.e("memberId", PersonInfoSave.memberInfo.getMemberId() + "");
				ResumeInfo resumeInfo = ResumeInfoService.getMyResume(PersonInfoSave.resumeInfo.getId());
				percent = ResumeInfoService.getResumePercent(PersonInfoSave.memberInfo.getMemberId(), PersonInfoSave.resumeInfo.getId());
				resultIsOpen = ResumeManageService.getOpen(PersonInfoSave.resumeInfo.getId());
				return resumeInfo;
			} catch (MyHttpException e) {
				Log.e("MyHttpException", e.getErrorCode());
				UIUtil.toast(activity, e.getErrorMessage());
			}
			return null;
		}

		@Override
		protected void onPostExecute(ResumeInfo resumeInfo) {
			activity.hideLoading();
			if (resumeInfo != null) {
				PersonInfoSave.resumeInfo.setDate(resumeInfo.getDate());
				PersonInfoSave.resumeInfo.setRefreshDate(resumeInfo.getRefreshDate());
				PersonInfoSave.resumeInfo.setPercent(percent);
				myResumeView.setText(Html.fromHtml("我的简历<font color='red'> (" + percent + "%) </font>"));
				resetMessage();
				if (resultIsOpen == 1) {
					secrecyButton.setImageResource(R.drawable.public_btn);
				} else {
					secrecyButton.setImageResource(R.drawable.secrecy_btn);
				}
			} else {
				myResumeView.setText("创建简历");
			}
			myResumeLayout.setOnClickListener(PersonalCenterFragment.this);
		}
	}

	/**
	 * 获取简历信息
	 */
	public class GetIndexPeronTask extends AsyncTask<Void, Integer, MemberMessage> {
		protected void onPreExecute() {
			activity.showLoadiing();
		};

		@Override
		protected MemberMessage doInBackground(Void... arg0) {
			try {
				Log.e("memberId", PersonInfoSave.memberInfo.getMemberId() + "");
				MemberMessage memberMessage = IndexPageService.getMemberMessage(PersonInfoSave.memberInfo.getMemberId());
				return memberMessage;
			} catch (MyHttpException e) {
				Log.e("MyHttpException", e.getErrorCode());
				UIUtil.toast(activity, e.getErrorMessage());
			}
			return null;
		}

		@Override
		protected void onPostExecute(MemberMessage memberMessage) {
			activity.hideLoading();
			if (memberMessage != null) {
				PersonInfoSave.saveMemberMessage(memberMessage);
				inviteView.setText(Html.fromHtml((PersonInfoSave.memberInfo.getNoInterview() == 0 ? "" : "<font color='red'>" + PersonInfoSave.memberInfo.getNoInterview()+ "</font>" + "/") + String.valueOf(PersonInfoSave.memberInfo.getInterview())));
				applyView.setText(PersonInfoSave.memberInfo.getCandidates() == 0 ? "" : PersonInfoSave.memberInfo.getCandidates() + "");
				favoriteView.setText(PersonInfoSave.memberInfo.getFavorite() == 0 ? "" : PersonInfoSave.memberInfo.getFavorite() + "");
				seemView.setText(PersonInfoSave.memberInfo.getSee() == 0 ? "" : PersonInfoSave.memberInfo.getSee() + "");
				leaveView.setText(Html.fromHtml((PersonInfoSave.memberInfo.getNoComments() == 0 ? "" : "<font color='red'>" + PersonInfoSave.memberInfo.getNoComments() + "</font>" + "/") + String.valueOf(PersonInfoSave.memberInfo.getComments())));
			}
		}
	}

	/**
	 * 刷新简历
	 */
	public class GoRefreshTask extends AsyncTask<Void, Integer, Integer> {
		protected void onPreExecute() {
			getView().findViewById(R.id.loading).setVisibility(View.VISIBLE);
		};

		@Override
		protected Integer doInBackground(Void... arg0) {
			try {
				int resumeId = ResumeManageService.refreshResume(PersonInfoSave.resumeInfo.getId());
				return resumeId;
			} catch (MyHttpException e) {
				Log.e("MyHttpException", e.getErrorCode());
				UIUtil.toast(activity, e.getErrorMessage());
			}
			return null;
		}

		@Override
		protected void onPostExecute(Integer result) {
			getView().findViewById(R.id.loading).setVisibility(View.GONE);
			if (result != null && result != 0) {

				String nowDate = f.format(new Date());
				PersonInfoSave.resumeInfo.setRefreshDate(nowDate);
				if (refreshDialog == null) {
					initRefreshDialog();
				}
				refreshTimeView.setText("更新时间 : " + nowDate);
				refreshDialog.show();
				resetMessage();
			}
		}
	}

	/**
	 * 保密公开简历
	 */
	public class GoSecrecyTask extends AsyncTask<Void, Integer, String> {
		protected void onPreExecute() {
			getView().findViewById(R.id.loading).setVisibility(View.VISIBLE);
			if (resultIsOpen == 1) {
				resultIsOpen = 0;
			} else {
				resultIsOpen = 1;
			}

		};

		@Override
		protected String doInBackground(Void... arg0) {
			try {
				String result = ResumeManageService.updateOpen(PersonInfoSave.resumeInfo.getUserName(), PersonInfoSave.resumeInfo.getId(), resultIsOpen);
				return result;
			} catch (MyHttpException e) {
				Log.e("MyHttpException", e.getErrorCode());
				UIUtil.toast(activity, e.getErrorMessage());
			}
			return null;
		}

		@Override
		protected void onPostExecute(String result) {
			getView().findViewById(R.id.loading).setVisibility(View.GONE);
			if (result != null) {
				UIUtil.toast(activity, result);
				if (resultIsOpen == 1) {
					secrecyButton.setImageResource(R.drawable.public_btn);
				} else {
					secrecyButton.setImageResource(R.drawable.secrecy_btn);
				}
			}
		}
	}

	/**
	 * 申请高级
	 */
	public class GoAdvanceTask extends AsyncTask<Void, Integer, Integer> {
		protected void onPreExecute() {
			getView().findViewById(R.id.loading).setVisibility(View.VISIBLE);
		};

		@Override
		protected Integer doInBackground(Void... arg0) {
			try {
				int resumeId = ResumeManageService.applyAdvance(PersonInfoSave.resumeInfo.getId());
				return resumeId;
			} catch (MyHttpException e) {
				Log.e("MyHttpException", e.getErrorCode());
				UIUtil.toast(activity, e.getErrorMessage());
			}
			return null;
		}

		@Override
		protected void onPostExecute(Integer result) {
			getView().findViewById(R.id.loading).setVisibility(View.GONE);
			if (result != null) {
				UIUtil.toast(activity, "我们已收到您的申请！将在2个工作日内完成审核，请您注意查收邮箱");
			}
		}
	}

	// 上传头像图片
	public class UpLoadAvatar extends AsyncTask<Void, Void, String> {
		@Override
		protected void onPreExecute() {
			activity.showHoldLoading();
			UIUtil.toast(activity, "头像上传中,请稍后...");
			super.onPreExecute();
		}

		@Override
		protected String doInBackground(Void... params) {
			try {
				Log.d("renameFile", renameFile.toString());
				String imageUrl = UploadPhotoService.updateOpen(PersonInfoSave.memberInfo.getMemberId(), 1, renameFile.length(), "jpg", renameFile);
				return imageUrl;
			} catch (MyHttpException e) {
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPostExecute(String result) {
			UIUtil.toast(activity, "头像上传成功");
			PersonInfoSave.memberInfo.setAvatar(result);
			activity.hideHoldLoading();
			if (!TextUtils.isEmpty(PersonInfoSave.memberInfo.getAvatar()))
				imageLoader.DisplayImage(PersonInfoSave.memberInfo.getAvatar(), avaterView, R.drawable.image_avatar_0, AndroidUtil.dpToPx(50, activity),
						AndroidUtil.dpToPx(60, activity));
			super.onPostExecute(result);
		}

	}

	public class UpLoadImg extends AsyncTask<Void, Void, List<Map<String, String>>> {
		@Override
		protected void onPreExecute() {
			activity.showHoldLoading();
			UIUtil.toast(activity, "个人风采上传中,请稍后...");
			super.onPreExecute();
		}

		@Override
		protected List<Map<String, String>> doInBackground(Void... params) {
			try {
				Log.d("renameFile", renameFile.toString());
				UploadPhotoService.updateOpen(PersonInfoSave.memberInfo.getMemberId(), 2, renameFile.length(), "jpg", renameFile);
				List<Map<String, String>> photoList = ResumeInfoService.getPersonImage(PersonInfoSave.memberInfo.getMemberId());
				return photoList;
			} catch (MyHttpException e) {
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPostExecute(List<Map<String, String>> result) {
			UIUtil.toast(activity, "个人风采上传成功");
			noPicLayout.setVisibility(View.GONE);
			picLayout.setVisibility(View.VISIBLE);
			activity.hideHoldLoading();
			addPhoto(result);
			super.onPostExecute(result);
		}

	}

	@Override
	public void onResume() {
		if (!CacheService.isLogined)
			activity.changeFragmentAnOnResume(LoginFragment.TAG, null);
		resetMessage();
		super.onResume();
	}

}
