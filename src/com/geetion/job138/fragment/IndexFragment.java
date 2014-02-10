package com.geetion.job138.fragment;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.fourmob.panningview.PanningView;
import com.liqi.job.R;
import com.geetion.job138.activity.BaseActivity;
import com.geetion.job138.activity.JobInfoSingleDetailActivity;
import com.geetion.job138.activity.PersonalApplyActivity;
import com.geetion.job138.activity.PersonalFavoriteActivity;
import com.geetion.job138.activity.PersonalInviteActivity;
import com.geetion.job138.activity.PersonalLeaveActivity;
import com.geetion.job138.activity.PersonalSeemActivity;
import com.geetion.job138.activity.RegisterActivity;
import com.geetion.job138.activity.group.BaseGroupActivity;
import com.geetion.job138.adapter.PosIndexListAdapter;
import com.geetion.job138.asynctask.PushTask;
import com.geetion.job138.fragment.PersonalCenterFragment.UpLoadAvatar;
import com.geetion.job138.imagecache.ImageLoader;
import com.geetion.job138.model.Company;
import com.geetion.job138.model.JobInfo;
import com.geetion.job138.model.MemberMessage;
import com.geetion.job138.model.PageUtil;
import com.geetion.job138.service.CacheService;
import com.geetion.job138.service.IndexPageService;
import com.geetion.job138.service.PersonInfoSave;
import com.geetion.job138.service.PushService;
import com.geetion.job138.service.UploadPhotoService;
import com.geetion.job138.util.AndroidUtil;
import com.geetion.job138.util.FileUtil;
import com.geetion.job138.util.FuncUtil;
import com.geetion.job138.util.MyHttpException;
import com.geetion.job138.util.PhotoUtil;
import com.geetion.job138.util.UIUtil;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.ContactsContract.CommonDataKinds.Photo;
import android.support.v4.app.Fragment;
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
import android.widget.ListView;
import android.widget.TextView;

public class IndexFragment extends BaseFragment implements OnClickListener {
	public static String TAG = IndexFragment.class.getName();
	private BaseGroupActivity activity;
	private ImageButton searchButton, loginButton, postButton, btn2, btn3;
	private Button message1, message2, message3, message4, message5;
	private ImageView headImg;
	private PosIndexListAdapter adapter;
	private ListView listView;
	private View headerView, loginLayout, emptyView, messageLayout;
	private PanningView panningView;
	private TextView dateText;
	private IndexDateTask dataTask;
	private CompanyTask companyTask;
	private List<Company> list = new ArrayList<Company>();
	private GetMemberMessageTask memberTask;
	private Uri photo;
	private File picFile;
	private AsyncTask uploadAvatar;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_index, container, false);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		activity = (BaseGroupActivity) getActivity();
		initView();
		initData();
		super.onActivityCreated(savedInstanceState);
	}

	@Override
	public void onResume() {
		initMessageLayout();
		panningView.startPanning();
		super.onResume();
	}

	@Override
	public void onPause() {
		panningView.stopPanning();
		super.onPause();
	}

	@Override
	public void onDestroy() {
		if (dataTask != null) {
			dataTask.cancel(true);
		}
		if (companyTask != null) {
			companyTask.cancel(true);
		}
		if (memberTask != null)
			memberTask.cancel(false);
		if (uploadAvatar != null) {
			uploadAvatar.cancel(true);
		}
		super.onDestroy();
	}

	private void initView() {
		listView = (ListView) getView().findViewById(R.id.listView1);
		headerView = getActivity().getLayoutInflater().inflate(R.layout.header_index, null);
		emptyView = getActivity().getLayoutInflater().inflate(R.layout.gloabl_listview_empty, null);
		listView.addHeaderView(headerView);
		listView.addFooterView(emptyView);
		adapter = new PosIndexListAdapter(activity, list);
		listView.setAdapter(adapter);
		panningView = (PanningView) getView().findViewById(R.id.index_header_bg);
		loginLayout = headerView.findViewById(R.id.login_layout);
		messageLayout = headerView.findViewById(R.id.message_layout);
		dateText = (TextView) headerView.findViewById(R.id.date);
		headImg = (ImageView) headerView.findViewById(R.id.user_bg);
		searchButton = (ImageButton) headerView.findViewById(R.id.button_search);
		loginButton = (ImageButton) headerView.findViewById(R.id.button_login);
		postButton = (ImageButton) headerView.findViewById(R.id.button_post);
		message1 = (Button) headerView.findViewById(R.id.message_text1);
		message2 = (Button) headerView.findViewById(R.id.message_text2);
		message3 = (Button) headerView.findViewById(R.id.message_text3);
		message4 = (Button) headerView.findViewById(R.id.message_text4);
		message5 = (Button) headerView.findViewById(R.id.message_text5);
		btn2 = (ImageButton) activity.getParent().findViewById(R.id.btn2);
		btn3 = (ImageButton) activity.getParent().findViewById(R.id.btn3);
		headImg.setOnClickListener(this);
		searchButton.setOnClickListener(this);
		loginButton.setOnClickListener(this);
		postButton.setOnClickListener(this);
		message1.setOnClickListener(this);
		message2.setOnClickListener(this);
		message3.setOnClickListener(this);
		message4.setOnClickListener(this);
		message5.setOnClickListener(this);
	}

	private void initData() {
		dataTask = new IndexDateTask();
		dataTask.execute();
		companyTask = new CompanyTask();
		String city = CacheService.city;
		if (city == null)
			city = "0";
		companyTask.execute("5", "1", city);
		if (CacheService.isLogined()) {
			memberTask = new GetMemberMessageTask();
			memberTask.execute();
		}

	}

	@Override
	public void onClick(View v) {
		if (v == searchButton) {
			btn2.performClick();
		} else if (v == loginButton) {
			btn3.performClick();
		} else if (v == postButton) {
			Intent intent = new Intent(activity, RegisterActivity.class);
			startActivity(intent);
		} else if (v == message1) {
			Intent intent = new Intent(activity, PersonalInviteActivity.class);
			startActivity(intent);
		} else if (v == message2) {
			Intent intent = new Intent(activity, PersonalLeaveActivity.class);
			startActivity(intent);
		} else if (v == message3) {
			Intent intent = new Intent(activity, PersonalFavoriteActivity.class);
			startActivity(intent);
		} else if (v == message4) {
			Intent intent = new Intent(activity, PersonalApplyActivity.class);
			startActivity(intent);
		} else if (v == message5) {
			Intent intent = new Intent(activity, PersonalSeemActivity.class);
			startActivity(intent);
		} else if (v == headImg) {
			addPicture();
		}
	}

	public void addPicture() {
		{
			String title[] = new String[] { "照相", "相册", "取消" };
			AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
			builder.setItems(title, new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					switch (which) {
					case 0:
						PhotoUtil.takePhotoByCamera(IndexFragment.this);
						break;
					case 1:
						PhotoUtil.choesePhoto(IndexFragment.this);
						break;
					case 2:
						dialog.dismiss();
					}
				}
			});
			builder.show();
		}
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		if (resultCode != BaseActivity.RESULT_OK) {
			return;
		}
		switch (requestCode) {
		case PhotoUtil.PHOTO_PICKED_WITH_DATA:
			photo = data.getData();
			picFile = FileUtil.saveFile(photo, PhotoUtil.getSavePath("/JOB138/image/"), "temp", getActivity());
			cropPhoto();
			break;
		case PhotoUtil.CAMERA_WITH_DATA:
			picFile = PhotoUtil.getSaveFile(activity);
			photo = Uri.fromFile(picFile);
			cropPhoto();
			break;
		case PhotoUtil.PHOTO_CROP_WITH_DATA:
			picFile = FileUtil.saveFile(photo, PhotoUtil.getSavePath("/JOB138/image/"), "temp.jpg", getActivity());
			compressBitmap(picFile);
			uploadAvatar = new UpLoadAvatar().execute();
			break;
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
				String imageUrl = UploadPhotoService.updateOpen(PersonInfoSave.memberInfo.getMemberId(), 1, picFile.length(), "jpg", picFile);
				return imageUrl;
			} catch (MyHttpException e) {
				e.printStackTrace();
				UIUtil.toast(activity, e.getErrorMessage());
			}
			return null;
		}

		@Override
		protected void onPostExecute(String result) {
			if (result != null) {
				UIUtil.toast(activity, "头像上传成功");
				PersonInfoSave.memberInfo.setAvatar(result);
				if (!TextUtils.isEmpty(PersonInfoSave.memberInfo.getAvatar())) {
					ImageLoader imageLoader = new ImageLoader(activity);
					ImageView userImageView = (ImageView) headerView.findViewById(R.id.user_bg);
					imageLoader.DisplayImage(PersonInfoSave.memberInfo.getAvatar(), userImageView, R.drawable.image_avatar_0, AndroidUtil.dpToPx(54, activity),
							AndroidUtil.dpToPx(64, activity));
					super.onPostExecute(result);
				}
			}
			activity.hideHoldLoading();
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
		// intent.putExtra("return-data", true);
		intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(FileUtil.saveFile(photo, PhotoUtil.getSavePath("/JOB138/image/"), "temp.jpg", getActivity())));
		startActivityForResult(intent, PhotoUtil.PHOTO_CROP_WITH_DATA);
	}

	public class IndexDateTask extends AsyncTask<Void, Integer, String> {
		@Override
		protected String doInBackground(Void... params) {
			String date = FuncUtil.getDateString();
			try {
				String calendar = IndexPageService.getCalendar();
				date = date + "(" + calendar + ")";
				Log.e("日期", date);
				return date;
			} catch (MyHttpException e) {
				// TODO Auto-generated catch block
				Log.e("MyHttpException", e.getErrorCode());
				UIUtil.toast(activity, e.getErrorMessage());
			}
			return date;
		}

		@Override
		protected void onPostExecute(String date) {
			// TODO Auto-generated method stub
			dateText.setText(date);
		}

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
		}

	}

	public class CompanyTask extends AsyncTask<String, Integer, List<Company>> {
		@Override
		protected List<Company> doInBackground(String... params) {
			try {
				List<Company> list = IndexPageService.getVipCompanyList(new PageUtil(params[0], params[1]), params[2]);

				return list;
			} catch (MyHttpException e) {
				Log.e("MyHttpException", e.getErrorCode());
				UIUtil.toast(activity, e.getErrorMessage());

			}
			return null;
		}

		@Override
		protected void onPostExecute(List<Company> resultList) {
			// TODO Auto-generated method stub
			if (resultList != null && !resultList.isEmpty()) {
				listView.removeFooterView(emptyView);
				list.addAll(resultList);
				adapter.notifyDataSetChanged();
			} else {
				emptyView.findViewById(R.id.progressBar1).setVisibility(View.GONE);
				TextView textView = (TextView) emptyView.findViewById(R.id.title);
				textView.setText("暂无数据");
			}
		}

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub

		}
	}

	public class GetMemberMessageTask extends AsyncTask<Void, Integer, MemberMessage> {
		protected void onPreExecute() {
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
			if (memberMessage != null) {
				PersonInfoSave.saveMemberMessage(memberMessage);
				initMessageLayout();
			}
		}
	}

	private void initMessageLayout() {
		if (CacheService.isLogined()) {
			messageLayout.setVisibility(View.VISIBLE);
			loginLayout.setVisibility(View.GONE);
			ImageLoader imageLoader = new ImageLoader(activity);
			ImageView userImageView = (ImageView) headerView.findViewById(R.id.user_bg);
			imageLoader.DisplayImage(PersonInfoSave.memberInfo.getAvatar(), userImageView, R.drawable.image_avatar_0, AndroidUtil.dpToPx(54, activity),
					AndroidUtil.dpToPx(64, activity));
			message1.setText(Html.fromHtml("<big>" + PersonInfoSave.memberInfo.getInterview() + "</big><br/><small><small>面试通知</small></small>"));
			message2.setText(Html.fromHtml("<big>" + PersonInfoSave.memberInfo.getComments() + "</big><br/><small><small>评价留言</small></small>"));
			message3.setText(Html.fromHtml("<big>" + PersonInfoSave.memberInfo.getFavorite() + "</big><br/><small><small>职位收藏</small></small>"));
			message4.setText(Html.fromHtml("<big>" + PersonInfoSave.memberInfo.getCandidates() + "</big><br/><small><small>应聘记录</small></small>"));
			message5.setText(Html.fromHtml("<big>" + PersonInfoSave.memberInfo.getSee() + "</big><br/><small><small>简历被看</small></small>"));
			TextView noInterviewText = (TextView) headerView.findViewById(R.id.message_new1);
			TextView noCommentsText = (TextView) headerView.findViewById(R.id.message_new2);
			int noInterview = PersonInfoSave.memberInfo.getNoInterview();
			int noComments = PersonInfoSave.memberInfo.getNoComments();
			if (noInterview == 0) {
				noInterviewText.setVisibility(View.GONE);
			} else {
				noInterviewText.setVisibility(View.VISIBLE);
				noInterviewText.setText(FuncUtil.showNoRead(noInterview));
			}

			if (PersonInfoSave.memberInfo.getNoComments() == 0) {
				noCommentsText.setVisibility(View.GONE);
			} else {
				noCommentsText.setVisibility(View.VISIBLE);
				noCommentsText.setText(FuncUtil.showNoRead(noComments));
			}
		} else {
			messageLayout.setVisibility(View.GONE);
			loginLayout.setVisibility(View.VISIBLE);
		}
	}
}
