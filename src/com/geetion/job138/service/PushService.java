package com.geetion.job138.service;

import java.util.List;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.geetion.job138.model.JobInfo;
import com.geetion.job138.util.HttpUtil;
import com.geetion.job138.util.MyHttpException;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

public class PushService extends BaseService {
	private final static String SETTING_PREFERNECES = "setting_push";
	private final static String INTERVIEW_PUSH = SERVER_URL + "/Work/Interview/Post";
	private final static String ORDER_PUSH = SERVER_URL + "/Job/OrderHire/1/";
	public static boolean isInterview, isOrderHir;
	private static Context context;
	
	public static void savePush(boolean isInterview, boolean isOrderHir){
		SharedPreferences pref = context.getSharedPreferences(SETTING_PREFERNECES, Context.MODE_APPEND);
		Editor editor = pref.edit();
		editor.putBoolean("isInterview", isInterview);
		editor.putBoolean("isOrderHir", isOrderHir);
		editor.commit();
		PushService.isInterview = isInterview;
		PushService.isOrderHir = isOrderHir;
	}
	
	public static void init(Context context){
		PushService.context = context;
		SharedPreferences pref = context.getSharedPreferences(SETTING_PREFERNECES, Context.MODE_APPEND);
		PushService.isInterview = pref.getBoolean("isInterview", false);
		PushService.isOrderHir = pref.getBoolean("isOrderHir", false);
	}
	

	/**
	 * 获取面试通知
	 * 
	 * @param memberId
	 * @return
	 * @throws MyHttpException
	 */
	public static List<JobInfo> getInterviewPush(int memberId) throws MyHttpException {
		String json = HttpUtil.get(INTERVIEW_PUSH + "/" + String.valueOf(memberId), null);
		JSONObject jsonObject;
		try {
			jsonObject = new JSONObject(json);
			boolean state = jsonObject.optBoolean("State");
			if (state) {
				String valueString = jsonObject.optString("Value");
				Gson gson = new GsonBuilder().serializeNulls().create();
				List<JobInfo> list = gson.fromJson(valueString, new TypeToken<List<JobInfo>>() {
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
	 * 获取职位订阅
	 * 
	 * @param memberId
	 * @return
	 * @throws MyHttpException
	 */
	public static List<JobInfo> getOrderHire(int memberId) throws MyHttpException {
		String json = HttpUtil.get(ORDER_PUSH + "/" + String.valueOf(memberId), null);
		JSONObject jsonObject;
		try {
			jsonObject = new JSONObject(json);
			boolean state = jsonObject.optBoolean("State");
			if (state) {
				String valueString = jsonObject.optString("Value");
				Gson gson = new GsonBuilder().serializeNulls().create();
				List<JobInfo> list = gson.fromJson(valueString, new TypeToken<List<JobInfo>>() {
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
}
