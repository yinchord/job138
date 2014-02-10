package com.geetion.job138.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.geetion.job138.fragment.resume.AbilityInfoFragment;
import com.geetion.job138.fragment.resume.BaseInfoFragment;
import com.geetion.job138.fragment.resume.ContactInfoFragment;
import com.geetion.job138.fragment.resume.JobInfoFragment;
import com.geetion.job138.fragment.resume.JobIntentFragment;
import com.geetion.job138.fragment.resume.MyContentInfoFragment;
import com.geetion.job138.model.CityInfo;
import com.geetion.job138.model.LoginRegisterMessage;
import com.geetion.job138.model.MemberMessage;
import com.geetion.job138.model.ResumeInfo;
import com.geetion.job138.model.Search;
import com.geetion.job138.util.HttpUtil;
import com.geetion.job138.util.MyHttpException;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class SettingService extends BaseService {
	private final static String SETTING_PREFERNECES = "setting_preferneces";
	private final static String UPDATE_PWD = SERVER_URL + "/User/ChangePassword/1";
	private final static String UPDATE_MOBILE = SERVER_URL + "/Resume/Mobile/1";
	private final static String GET_STATE = SERVER_URL + "/Resume/Status";
	private final static String UPDATE_STATE = SERVER_URL + "/Resume/Status/Update/1";
	public static LoginRegisterMessage loginMessage = new LoginRegisterMessage();

	public static void saveloginMessage(Context context, LoginRegisterMessage message) {
		SharedPreferences pref = context.getSharedPreferences(SETTING_PREFERNECES, Context.MODE_APPEND);
		Editor editor = pref.edit();
		loginMessage.setMemberId(message.getMemberId());
		loginMessage.setResumeId(message.getResumeId());
		loginMessage.setUserName(message.getUserName());
		editor.putInt("MemberId", message.getMemberId());
		editor.putInt("ResumeId", message.getResumeId());
		editor.putString("UserName", message.getUserName());
		editor.commit();
	}

	public static void updateLoginId(Context context, int resumeId) {
		SharedPreferences pref = context.getSharedPreferences(SETTING_PREFERNECES, Context.MODE_APPEND);
		Editor editor = pref.edit();
		loginMessage.setMemberId(resumeId);
		editor.putInt("ResumeId", resumeId);
		editor.commit();
	}

	public static void setAutoLogin(Context context) {
		SharedPreferences pref = context.getSharedPreferences(SETTING_PREFERNECES, Context.MODE_APPEND);
		Editor editor = pref.edit();
		editor.putBoolean("autoLogin", true);
		editor.commit();
	}

	public static boolean isAutoLogin(Context context) {
		SharedPreferences pref = context.getSharedPreferences(SETTING_PREFERNECES, Context.MODE_APPEND);
		return pref.getBoolean("autoLogin", false);
	}

	public static void cleanLoginMessage(Context context) {
		loginMessage = new LoginRegisterMessage();
		SharedPreferences pref = context.getSharedPreferences(SETTING_PREFERNECES, Context.MODE_APPEND);
		Editor editor = pref.edit();
		editor.remove("MemberId");
		editor.remove("ResumeId");
		editor.remove("UserName");
		editor.remove("autoLogin");
		CacheService.isLogined = false;
		editor.commit();
	}

	public static void clearCache() {
		PersonInfoSave.resumeInfo = new ResumeInfo();
		PersonInfoSave.memberInfo = new MemberMessage();
		BaseInfoFragment.isGetData = false;
		AbilityInfoFragment.isGetData = false;
		ContactInfoFragment.isGetData = false;
		JobIntentFragment.isGetData = false;
		MyContentInfoFragment.isGetData = false;
	}

	public static void getLoginMessage(Context context) {
		loginMessage = new LoginRegisterMessage();
		SharedPreferences pref = context.getSharedPreferences(SETTING_PREFERNECES, Context.MODE_APPEND);
		loginMessage.setMemberId(pref.getInt("MemberId", 0));
		loginMessage.setResumeId(pref.getInt("ResumeId", 0));
		loginMessage.setUserName(pref.getString("UserName", ""));
	}

	public static void saveHelpShowed(Context context) {
		SharedPreferences pref = context.getSharedPreferences(SETTING_PREFERNECES, Context.MODE_APPEND);
		Editor editor = pref.edit();
		editor.putBoolean("help", true);
		editor.commit();
	}

	public static boolean isShareHelp(Context context) {
		SharedPreferences pref = context.getSharedPreferences(SETTING_PREFERNECES, Context.MODE_APPEND);
		return pref.getBoolean("share", false);
	}

	public static void saveShareShowed(Context context) {
		SharedPreferences pref = context.getSharedPreferences(SETTING_PREFERNECES, Context.MODE_APPEND);
		Editor editor = pref.edit();
		editor.putBoolean("share", true);
		editor.commit();
	}

	public static boolean isShowedHelp(Context context) {
		SharedPreferences pref = context.getSharedPreferences(SETTING_PREFERNECES, Context.MODE_APPEND);
		return pref.getBoolean("help", false);
	}

	public static void openOrCloseVoice(Context context) {
		SharedPreferences pref = context.getSharedPreferences(SETTING_PREFERNECES, Context.MODE_APPEND);
		Editor editor = pref.edit();
		editor.putBoolean("voice", !getShowVoice(context));
		editor.commit();
	}

	public static boolean getShowVoice(Context context) {
		SharedPreferences pref = context.getSharedPreferences(SETTING_PREFERNECES, Context.MODE_APPEND);
		return pref.getBoolean("voice", true);
	}

	public static void saveSearchHistory(Context context, Search search) {
		SharedPreferences pref = context.getSharedPreferences(SETTING_PREFERNECES, Context.MODE_APPEND);
		Editor editor = pref.edit();
		List<Search> saveList = new ArrayList<Search>();
		List<Search> list = loadSearchHistory(context);
		if (list != null) {
			if (list.size() >= 5) {
				list.remove(0);
				list.add(search);
			} else {

				list.add(search);
			}
			saveList.addAll(list);
		} else {
			saveList.add(search);
		}
		editor.putString("search", getSeachString(saveList));
		editor.commit();
	}

	public static List<Search> loadSearchHistory(Context context) {
		SharedPreferences pref = context.getSharedPreferences(SETTING_PREFERNECES, Context.MODE_APPEND);
		String json = pref.getString("search", null);
		if (json == null) {
			return new ArrayList<Search>();
		} else {
			Gson gson = new GsonBuilder().serializeNulls().create();
			List<Search> list = gson.fromJson(json, new TypeToken<List<Search>>() {
			}.getType());
			return list;
		}
	}

	public static String getSeachString(List<Search> saveList) {
		if (saveList.isEmpty()) {
			return null;
		} else {
			JSONArray jsonArray = new JSONArray();
			for (int i = 0; i < saveList.size(); i++) {
				Search search = saveList.get(i);
				try {
					JSONObject jsonObject = new JSONObject();
					jsonObject.put("keyWord", search.getKeyWord());
					jsonObject.put("keyWordType", search.getKeyWordType());
					jsonObject.put("positionId", search.getPositionId());
					jsonObject.put("positionName", search.getPositionName());
					jsonObject.put("areaId", search.getAreaId());
					jsonObject.put("areaName", search.getAreaName());
					jsonArray.put(jsonObject);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			return jsonArray.toString();
		}
	}

	public static String updatePWD(String UserName, String UserPwd, String NewPwd) throws MyHttpException {
		Map<String, String> params = new HashMap<String, String>();
		params.put("UserName", UserName);
		params.put("UserPwd", UserPwd);
		params.put("NewPwd", NewPwd);
		String json = HttpUtil.postJSON(UPDATE_PWD, params);
		JSONObject jsonObject;
		try {
			jsonObject = new JSONObject(json);
			boolean state = jsonObject.optBoolean("State");
			if (state) {
				JSONObject valueObject = jsonObject.optJSONObject("Value");
				String UserNameBack = valueObject.optString("UserName");
				return UserNameBack;
			} else {
				throw new MyHttpException(jsonObject.optString("Message"), jsonObject.optString("Message"));
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static int updateMobile(String UserName, String Rid, String Mobile) throws MyHttpException {
		Map<String, String> params = new HashMap<String, String>();
		params.put("UserName", UserName);
		params.put("Rid", Rid);
		params.put("Mobile", Mobile);
		String json = HttpUtil.postJSON(UPDATE_MOBILE, params);
		JSONObject jsonObject;
		try {
			jsonObject = new JSONObject(json);
			boolean state = jsonObject.optBoolean("State");
			if (state) {
				JSONObject valueObject = jsonObject.optJSONObject("Value");
				int resumeId = valueObject.optInt("Id");
				return resumeId;
			} else {
				throw new MyHttpException(jsonObject.optString("Message"), jsonObject.optString("Message"));
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return 0;
	}

	public static String getStatus(int MemberId) throws MyHttpException {
		String json = HttpUtil.get(GET_STATE + "/" + String.valueOf(MemberId), null);
		JSONObject jsonObject;
		try {
			jsonObject = new JSONObject(json);
			boolean state = jsonObject.optBoolean("State");
			if (state) {
				JSONObject valueObject = jsonObject.optJSONObject("Value");
				String status = valueObject.optString("Status");
				return status;
			} else {
				throw new MyHttpException(jsonObject.optString("Message"), jsonObject.optString("Message"));
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static int updateStatus(int MemberId, String status) throws MyHttpException {
		Map<String, String> params = new HashMap<String, String>();
		params.put("MemberId", String.valueOf(MemberId));
		params.put("Status", status);
		String json = HttpUtil.postJSON(UPDATE_STATE, params);
		JSONObject jsonObject;
		try {
			jsonObject = new JSONObject(json);
			boolean state = jsonObject.optBoolean("State");
			if (state) {
				JSONObject valueObject = jsonObject.optJSONObject("Value");
				int resumeId = valueObject.optInt("Id");
				return resumeId;
			} else {
				throw new MyHttpException(jsonObject.optString("Message"), jsonObject.optString("Message"));
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return 0;
	}
}
