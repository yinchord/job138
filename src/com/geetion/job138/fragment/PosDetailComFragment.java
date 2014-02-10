package com.geetion.job138.fragment;

import cn.sharesdk.onekeyshare.OnekeyShare;

import com.liqi.job.R;
import com.geetion.job138.activity.BaseActivity;
import com.geetion.job138.activity.MapActivity;
import com.geetion.job138.imagecache.ImageLoader;
import com.geetion.job138.model.Company;
import com.geetion.job138.model.JobInfo;
import com.geetion.job138.model.ResumeInfo;
import com.geetion.job138.service.JobInfoService;
import com.geetion.job138.service.JobManageService;
import com.geetion.job138.service.PersonInfoSave;
import com.geetion.job138.util.MyHttpException;
import com.geetion.job138.util.UIUtil;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class PosDetailComFragment extends BaseFragment implements OnClickListener {
	public static String TAG = PosDetailComFragment.class.getName();
	private ImageButton btShare, phone;
	private LinearLayout addresslayout;
	private ImageView imgAddress;
	private AsyncTask candidatesTask;
	private Company company;
	private BaseActivity activity;
	private TextView contact, address, companyname, introduce, info;
	private ImageView logo;
	private ResumeInfo resumeInfo;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_pos_com, container, false);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		initView();
		initData();
		super.onActivityCreated(savedInstanceState);
	}

	@Override
	public void onDestroy() {
		if (candidatesTask != null) {
			candidatesTask.cancel(true);
		}
		super.onDestroy();
	}

	public void initView() {
		addresslayout = (LinearLayout) getActivity().findViewById(R.id.addresslayout);
		//imgAddress = (ImageView) getActivity().findViewById(R.id.imgAddress);
		btShare = (ImageButton) getActivity().findViewById(R.id.btShare);
		phone = (ImageButton) getActivity().findViewById(R.id.phone);
		contact = (TextView) getActivity().findViewById(R.id.contact);
		address = (TextView) getActivity().findViewById(R.id.address);
		companyname = (TextView) getActivity().findViewById(R.id.companyname);
		introduce = (TextView) getActivity().findViewById(R.id.introduce);
		info = (TextView) getActivity().findViewById(R.id.info);
		logo = (ImageView) getActivity().findViewById(R.id.logo);
		addresslayout.setOnClickListener(this);
		address.setOnClickListener(this);
		btShare.setOnClickListener(this);
		phone.setOnClickListener(this);
		activity = (BaseActivity) getActivity();
	}

	void initData() {
		resumeInfo = PersonInfoSave.resumeInfo;
		Bundle bundle = getArguments();
		if (bundle != null) {
			company = (Company) bundle.get("company");
		}
		if (company != null) {
			if (company.getMap() == 0) {
				address.setCompoundDrawables(null, null, null, null);
				address.setClickable(false);
			}
			companyname.setText(company.getCompanyName());
			address.setText(company.getAddress());
			contact.setText("联系人：" + company.getContact());
			String intS = company.getIntroduce();
			if (intS != null)
				introduce.setText(Html.fromHtml(intS));
			info.setText("\n成立日期：" + company.getDate() + "\n员工人数：" + company.getWorker());
			new ImageLoader(getActivity()).DisplayImage(company.getLogo(), logo, R.drawable.nopic, 0, 0);
		}
	}

	@Override
	public void onClick(View v) {
		if (v == address) {
			if (company.getMap() != 0) {
				Intent intent = new Intent(getActivity(), MapActivity.class);
				intent.putExtra("x", company.getLable()[0]);
				intent.putExtra("y", company.getLable()[1]);
				intent.putExtra("address", company.getAddress());
				startActivity(intent);
			}

		} else if (v == btShare) {
			final OnekeyShare oks = new OnekeyShare();
			oks.disableSSOWhenAuthorize();
			oks.setText(company.getCompanyName() + "正在使用#138中国美容人才网#招聘美容人才，如果您感兴趣请关注:" + "http://m.138job.com/Job/Company-" + company.getId());
			oks.setTitleUrl("http://sharesdk.cn");
			oks.setUrl("http://www.sharesdk.cn");
			oks.setSiteUrl("http://sharesdk.cn");
			oks.setNotification(R.drawable.ic_launcher, getActivity().getString(R.string.app_name));
			oks.setSite(getActivity().getString(R.string.app_name));
			oks.show(getActivity());
		} else if (v == phone) {
			call(company.getTel());
		}
	}

	public void call(String phoneNum) {
		Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phoneNum));
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		startActivity(intent);
	}
}