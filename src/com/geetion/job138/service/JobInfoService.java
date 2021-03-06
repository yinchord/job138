package com.geetion.job138.service;

import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.geetion.job138.model.Company;
import com.geetion.job138.model.JobInfo;
import com.geetion.job138.model.PageUtil;
import com.geetion.job138.util.HttpUtil;
import com.geetion.job138.util.MyHttpException;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

public class JobInfoService extends BaseService {
	private final static String COMPANY_INFO = SERVER_URL + "/Job/Company";
	private final static String JOB_INFO = SERVER_URL + "/Job/Hire";
	private final static String OTHER_JOB_INFO = SERVER_URL + "/Job/OtherHire";
	private final static String MAP_LABLE = SERVER_URL + "/Job/Map";
	
	/**
	 * 获取公司简介
	 * @param appType
	 * 		    手机操作系统（Android = 1,iPhoneOS = 2,Symbian = 3,WinPhone = 4）
	 * @param id
	 * 		    公司ID
	 * @return
	 * @throws MyHttpException
	 */
	public static Company getCompanyInfo(int appType, int id) throws MyHttpException {
		String url = COMPANY_INFO + "/" + appType + "/" + id;
		String json = HttpUtil.get(url, null);
		JSONObject jsonObject;
		try {
			jsonObject = new JSONObject(json);
			boolean state = jsonObject.optBoolean("State");
			if (state) {
				String valueString = jsonObject.optString("Value");
				Gson gson = new GsonBuilder().serializeNulls().create();
				Company object = gson.fromJson(valueString, Company.class);
				return object;
			} else {
				throw new MyHttpException(jsonObject.optString("Message"), jsonObject.optString("Message"));
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 获取职位详情
	 * @param appType
	 * 		    手机操作系统（Android = 1,iPhoneOS = 2,Symbian = 3,WinPhone = 4）
	 * @param id
	 * 		    职位ID
	 * @return
	 * @throws MyHttpException
	 */
	public static JobInfo getJobInfo(int appType, int id) throws MyHttpException {
		String url = JOB_INFO + "/" + String.valueOf(appType) + "/" + String.valueOf(id);
		String json = HttpUtil.get(url, null);
		JSONObject jsonObject;
		try {
			jsonObject = new JSONObject(json);
			boolean state = jsonObject.optBoolean("State");
			if (state) {
				String valueString = jsonObject.optString("Value");
				Gson gson = new GsonBuilder().serializeNulls().create();
				JobInfo object = gson.fromJson(valueString, JobInfo.class);
				return object;
			} else {
				throw new MyHttpException(jsonObject.optString("Message"), jsonObject.optString("Message"));
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 获取其他职位
	 * @param appType
	 * 		    手机操作系统（Android = 1,iPhoneOS = 2,Symbian = 3,WinPhone = 4）
	 * @param pageUtil
	 * @param memberId
	 * 	            公司ID
	 * @return
	 * @throws MyHttpException
	 */
	public static List<JobInfo> getOtherJobInfo(int appType, PageUtil pageUtil, int memberId) throws MyHttpException {
		String url = OTHER_JOB_INFO + "/" + String.valueOf(appType) + "/" + pageUtil.getPageSize() + "/" + pageUtil.getPageNo() + "/" + String.valueOf(memberId);
		String json = HttpUtil.get(url, null);
		JSONObject jsonObject;
		try {
			jsonObject = new JSONObject(json);
			boolean state = jsonObject.optBoolean("State");
			if (state) {
				JSONObject valueObject = jsonObject.optJSONObject("Value");
				int totalPageSize = valueObject.optInt("Pages");
				pageUtil.setPageCount(totalPageSize);
				Gson gson = new GsonBuilder().serializeNulls().create();
				String ListOtherHire = valueObject.optString("ListOtherHire");
				List<JobInfo> list = gson.fromJson(ListOtherHire, new TypeToken<List<JobInfo>>() {
				}.getType());
				return list;
			} else {
				throw new MyHttpException(jsonObject.optString("Message"), jsonObject.optString("Message"));
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 获取地图标驻
	 * @param appType
	 * 		    手机操作系统（Android = 1,iPhoneOS = 2,Symbian = 3,WinPhone = 4）
	 * @param id
	 * 	            公司ID
	 * @return
	 * @throws MyHttpException
	 */
	public static Float[] getMapLable(int appType, int id) throws MyHttpException {
		String url = MAP_LABLE + "/" + String.valueOf(appType) + "/" + String.valueOf(id);
		String json = HttpUtil.get(url, null);
		JSONObject jsonObject;
		try {
			jsonObject = new JSONObject(json);
			boolean state = jsonObject.optBoolean("State");
			if (state) {
				JSONObject valueJson = jsonObject.optJSONObject("Value");
				Float x = Float.valueOf(valueJson.optString("x"));
				Float y = Float.valueOf(valueJson.optString("y"));
				Float[] lable  = new Float[2];
				lable[0] = x;
				lable[1] = y;
				return lable;
			} else {
				throw new MyHttpException(jsonObject.optString("Message"), jsonObject.optString("Message"));
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return null;
	}	
}
