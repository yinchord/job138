package com.geetion.job138.fragment.resume;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.liqi.job.R;
import com.geetion.job138.activity.MyResumeActivity;
import com.geetion.job138.fragment.BaseFragment;
import com.geetion.job138.model.CityInfo;
import com.geetion.job138.model.ResumeInfo;
import com.geetion.job138.service.CacheService;
import com.geetion.job138.service.PersonInfoSave;
import com.geetion.job138.service.ResumeManageService;
import com.geetion.job138.service.SearchJobService;
import com.geetion.job138.service.SettingService;
import com.geetion.job138.util.FuncUtil;
import com.geetion.job138.util.MyHttpException;
import com.geetion.job138.util.UIUtil;
import com.geetion.job138.util.UIUtil.OnSelectAlter;
import com.geetion.job138.widget.custom.SelectCityPop;
import com.geetion.job138.widget.custom.SelectCityPop.SelectCity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

public class BaseInfoFragment extends BaseFragment {
	public static String TAG = BaseInfoFragment.class.getName();
	private MyResumeActivity activity;
	private ImageButton closeButton;
	private GoBaseInfoTask task;
	private EditText usernameView, hightView, cardNumView, workAgeView;
	private TextView sexView, educView, nationView, marriageView, nativeView, locView, lifeLocView, cardView, graduateView, birthView;
	private ImageButton saveButton;
	private SelectCityPop cityPop, livePop;
	private LayoutInflater inflater;
	private ResumeInfo updateResume = new ResumeInfo();
	private UpadateBaseInfoTask updateTask;
	public static boolean isGetData = false;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_resume_baseinfo, container, false);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		activity = (MyResumeActivity) getActivity();
		init();
		super.onActivityCreated(savedInstanceState);
	}

	public void init() {
		closeButton = (ImageButton) getView().findViewById(R.id.close_button);
		closeButton.setOnClickListener(this);
		usernameView = (EditText) getView().findViewById(R.id.username);
		birthView = (TextView) getView().findViewById(R.id.birth);
		birthView.setOnClickListener(this);
		hightView = (EditText) getView().findViewById(R.id.hight);
		cardNumView = (EditText) getView().findViewById(R.id.card_num);
		workAgeView = (EditText) getView().findViewById(R.id.work_age);
		graduateView = (TextView) getView().findViewById(R.id.graduate);
		graduateView.setOnClickListener(this);
		sexView = (TextView) getView().findViewById(R.id.sex);
		sexView.setOnClickListener(this);
		educView = (TextView) getView().findViewById(R.id.educ);
		educView.setOnClickListener(this);
		nationView = (TextView) getView().findViewById(R.id.nation);
		nationView.setOnClickListener(this);
		marriageView = (TextView) getView().findViewById(R.id.marriage);
		marriageView.setOnClickListener(this);
		nativeView = (TextView) getView().findViewById(R.id.native_register);
		nativeView.setOnClickListener(this);
		locView = (TextView) getView().findViewById(R.id.loc);
		locView.setOnClickListener(this);
		cardView = (TextView) getView().findViewById(R.id.card);
		cardView.setOnClickListener(this);
		workAgeView = (EditText) getView().findViewById(R.id.work_age);
		saveButton = (ImageButton) getView().findViewById(R.id.save_button);
		saveButton.setOnClickListener(BaseInfoFragment.this);
		updateResume.setId(PersonInfoSave.resumeInfo.getId());
		updateResume.setUserName(PersonInfoSave.resumeInfo.getUserName());
		if (!isGetData)
			getData();
		else {
			updateResume.saveResumeBaseInfo(PersonInfoSave.resumeInfo);
			resetResumeInfo();
		}
		inflater = activity.getLayoutInflater();
		View cityView = inflater.inflate(R.layout.window_city, null);
		View liveView = inflater.inflate(R.layout.window_city, null);
		cityPop = new SelectCityPop(cityView, activity, true);
		cityPop.setSelectCity(new SelectCity() {
			@Override
			public void getCity(CityInfo cityInfo) {
				nativeView.setText(CacheService.provincesMap.get(cityInfo.getFid()).getName() + " " + cityInfo.getName());
				updateResume.setHuKouProvince(CacheService.provincesMap.get(cityInfo.getFid()).getName());
				updateResume.setHuKouCapital(cityInfo.getName());
			}

			@Override
			public void check(HashMap<Integer, CityInfo> chooseMap, HashMap<Integer, CityInfo> chooseCityMap) {
				// TODO Auto-generated method stub

			}
		});
		livePop = new SelectCityPop(liveView, activity, true);
		livePop.setSelectCity(new SelectCity() {
			@Override
			public void getCity(CityInfo cityInfo) {
				locView.setText(CacheService.provincesMap.get(cityInfo.getFid()).getName() + " " + cityInfo.getName());
				updateResume.setProvince(CacheService.provincesMap.get(cityInfo.getFid()).getName());
				updateResume.setCapital(cityInfo.getName());
			}

			@Override
			public void check(HashMap<Integer, CityInfo> chooseMap, HashMap<Integer, CityInfo> chooseCityMap) {
				// TODO Auto-generated method stub
				
			}
		});
	}

	public void resetResumeInfo() {
		usernameView.setText(PersonInfoSave.resumeInfo.getName());
		sexView.setText(CacheService.sexKeyMap.get(PersonInfoSave.resumeInfo.getSex()));
		birthView.setText(PersonInfoSave.resumeInfo.getBirth());
		educView.setText(CacheService.educKeyMap.get(PersonInfoSave.resumeInfo.getEdu()));
		graduateView.setText(PersonInfoSave.resumeInfo.getGraduate());
		if (!TextUtils.isEmpty(PersonInfoSave.resumeInfo.getHeight()))
			hightView.setText(PersonInfoSave.resumeInfo.getHeight());
		if (!TextUtils.isEmpty(CacheService.nationKeyMap.get(PersonInfoSave.resumeInfo.getNation())))
			nationView.setText(CacheService.nationKeyMap.get(PersonInfoSave.resumeInfo.getNation()));
		if (!TextUtils.isEmpty(CacheService.marriageKeyMap.get(PersonInfoSave.resumeInfo.getMarriage())))
			marriageView.setText(CacheService.marriageKeyMap.get(PersonInfoSave.resumeInfo.getMarriage()));
		if (!TextUtils.isEmpty(PersonInfoSave.resumeInfo.getHuKouProvince()))
			nativeView.setText(PersonInfoSave.resumeInfo.getHuKouProvince() + " " + PersonInfoSave.resumeInfo.getHuKouCapital());
		if (!TextUtils.isEmpty(PersonInfoSave.resumeInfo.getProvince()))
			locView.setText(PersonInfoSave.resumeInfo.getProvince() + " " + PersonInfoSave.resumeInfo.getCapital());
		// cardView.setText(CacheService.cardKeyMap.get(PersonInfoSave.resumeInfo.getCardType()));
		// cardNumView.setText("" + PersonInfoSave.resumeInfo.getIdCard());
		if (!TextUtils.isEmpty(PersonInfoSave.resumeInfo.getWorkYear()))
			workAgeView.setText("" + PersonInfoSave.resumeInfo.getWorkYear());
	}

	public void getData() {
		task = new GoBaseInfoTask();
		task.execute();
	}

	@Override
	public void onDestroy() {
		if (task != null)
			task.cancel(true);
		if (updateTask != null)
			updateTask.cancel(true);
		super.onDestroy();
	}

	@Override
	public void onClick(View v) {
		if (v == closeButton) {
			activity.hideSelectFragment();
		} else if (v == saveButton) {
			if (getUpdateInfo()) {
				updateTask = new UpadateBaseInfoTask();
				updateTask.execute();
			}
		} else if (v == sexView) {
			UIUtil.showSelectAlert(activity, CacheService.sexKeyMap.values().toArray(new String[0]), new OnSelectAlter() {
				public void onSelect(String select) {
					sexView.setText(select);
					updateResume.setSex(CacheService.sexNameMap.get(select));
				}
			});
		} else if (v == educView) {
			UIUtil.showSelectAlert(activity, CacheService.educKeyMap.values().toArray(new String[0]), new OnSelectAlter() {
				public void onSelect(String select) {
					educView.setText(select);
					updateResume.setEdu(CacheService.educNameMap.get(select));
				}
			});
		} else if (v == nationView) {
			UIUtil.showSelectAlert(activity, CacheService.nationKeyMap.values().toArray(new String[0]), new OnSelectAlter() {
				public void onSelect(String select) {
					nationView.setText(select);
					updateResume.setNation(CacheService.nationNameMap.get(select));
				}
			});
		} else if (v == marriageView) {
			UIUtil.showSelectAlert(activity, CacheService.marriageKeyMap.values().toArray(new String[0]), new OnSelectAlter() {
				public void onSelect(String select) {
					marriageView.setText(select);
					updateResume.setMarriage(CacheService.marriageNameMap.get(select));
				}
			});
		} else if (v == cardView) {
			// UIUtil.showSelectAlert(activity,
			// CacheService.cardKeyMap.values().toArray(new String[0]), new
			// OnSelectAlter() {
			// public void onSelect(String select) {
			// cardView.setText(select);
			// updateResume.setCardType(CacheService.cardNameMap.get(select));
			// }
			// });
		} else if (v == nativeView) {
			cityPop.showPop(nativeView);
		} else if (v == locView) {
			livePop.showPop(locView);
		} else if (v == birthView) {
			UIUtil.datePicker(activity, birthView);
		} else if (v == graduateView) {
			UIUtil.datePicker(activity, graduateView);
		}
	}

	public boolean getUpdateInfo() {
		String userName = usernameView.getText().toString();
		if (!FuncUtil.allChinese(userName) || userName.length() < 2 || userName.length() > 4) {
			UIUtil.toast(activity, "中文姓名必须为2~4个中文");
			return false;
		}
		if (TextUtils.isEmpty(sexView.getText().toString())) {
			UIUtil.toast(activity, "性别必须选择");
			return false;
		}
		String birth = birthView.getText().toString();
		if (TextUtils.isEmpty(birth)) {
			UIUtil.toast(activity, "出生日期不能为空");
			return false;
		}
		if (TextUtils.isEmpty(educView.getText().toString())) {
			UIUtil.toast(activity, "最高学历必须选择");
			return false;
		}
		String graduate = graduateView.getText().toString();
		if (TextUtils.isEmpty(graduate)) {
			UIUtil.toast(activity, "毕业时间不能为空");
			return false;
		}
		if (TextUtils.isEmpty(locView.getText().toString())) {
			UIUtil.toast(activity, "现所在地必须选择");
			return false;
		}
		String hight = hightView.getText().toString();
		String workYear = workAgeView.getText().toString();
		if (TextUtils.isEmpty(workYear)) {
			UIUtil.toast(activity, "美业职龄不能为空");
			return false;
		}
		updateResume.setName(userName);
		updateResume.setBirth(birth);
		updateResume.setGraduate(graduate);
		updateResume.setHeight(hight);
		updateResume.setWorkYear(workYear);
		return true;
	}

	public class GoBaseInfoTask extends AsyncTask<Void, Integer, ResumeInfo> {
		protected void onPreExecute() {
			saveButton.setClickable(false);
			activity.showLoadiing();
		};

		@Override
		protected ResumeInfo doInBackground(Void... arg0) {
			try {
				if (CacheService.nationKeyMap.isEmpty())
					ResumeManageService.getNation();
				if (PersonInfoSave.resumeInfo.getId() != 0) {
					ResumeInfo resumeInfo = ResumeManageService.getBaseInfo(PersonInfoSave.resumeInfo.getId(), PersonInfoSave.memberInfo.getMemberId());
					return resumeInfo;
				}
			} catch (MyHttpException e) {
				Log.e("MyHttpException", e.getErrorCode());
				UIUtil.toast(activity, e.getErrorMessage());
			}
			return null;
		}

		@Override
		protected void onPostExecute(ResumeInfo result) {
			activity.hideLoading();
			saveButton.setClickable(true);
			if (result != null) {
				PersonInfoSave.saveResumeBaseInfo(result);
				updateResume = result;
				resetResumeInfo();
				isGetData = true;
			}
		}
	}

	public class UpadateBaseInfoTask extends AsyncTask<Void, Integer, Integer> {
		protected void onPreExecute() {
			saveButton.setClickable(false);
			activity.showLoadiing();
		};

		@Override
		protected Integer doInBackground(Void... arg0) {
			try {
				if (CacheService.nationKeyMap.isEmpty())
					ResumeManageService.getNation();
				Integer result = ResumeManageService.newOrUpdateBaseInfo(updateResume);
				return result;
			} catch (MyHttpException e) {
				Log.e("MyHttpException", e.getErrorCode());
				UIUtil.toast(activity, e.getErrorMessage());
			}
			return null;
		}

		@Override
		protected void onPostExecute(Integer result) {
			activity.hideLoading();
			saveButton.setClickable(true);
			if (result != null) {
				PersonInfoSave.updateSaveID(activity, result);
				updateResume.setId(result);
				PersonInfoSave.saveResumeBaseInfo(updateResume);
				UIUtil.toast(activity, "修改成功");
				activity.changeFragmentAnOnResume(ContactInfoFragment.TAG, null);
				TextView contactInfoView=(TextView)activity.findViewById(R.id.contact_info_show);
				activity.cleanAndSelect(R.id.contact_info, contactInfoView);
			}
		}
	}
}
