package com.geetion.job138.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.geetion.job138.model.MemberMessage;
import com.geetion.job138.model.ResumeBaseInfo;
import com.geetion.job138.model.ResumeInfo;
import com.geetion.job138.util.HttpUtil;
import com.geetion.job138.util.MyHttpException;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class ResumeInfoService extends BaseService {
	private final static String PERSONAL_IMAGE = SERVER_URL + "/resume/PersonImage";
	private final static String PERSONAL_INFO = SERVER_URL + "/resume/PersonInfo";
	private final static String MY_RESUME = SERVER_URL + "/resume/MyResume";
	private final static String RESUME_INFO = SERVER_URL + "/Resume/ResumeInfo";
	private final static String RESUME_INTGRITY =SERVER_URL + "/Resume/Percent";

	/**
	 * 获取风采照片
	 * 
	 * @param memberId
	 *            用户会员ID
	 * @return 风采照片的大小图URL的键值对数组，用get("Image")获取大图url，get("SmallImage")获取小图url
	 * @throws MyHttpException
	 */
	public static List<Map<String, String>> getPersonImage(int memberId) throws MyHttpException {
		String url = PERSONAL_IMAGE + "/" + String.valueOf(memberId);
		String json = HttpUtil.get(url, null);
		JSONObject jsonObject;
		try {
			jsonObject = new JSONObject(json);
			boolean state = jsonObject.optBoolean("State");
			if (state) {
				JSONArray valueJson = jsonObject.optJSONArray("Value");
				if (valueJson.length() > 0) {
					
					List<Map<String, String>> list = new ArrayList<Map<String, String>>();
					for (int i = 0; i < valueJson.length(); i++) {
						Map<String, String> images = new HashMap<String, String>();
						String Image = valueJson.optJSONObject(i).optString("Image");
						String SmallImage = valueJson.optJSONObject(i).optString("SmallImage");
						images.put("Image", Image);
						images.put("SmallImage", SmallImage);
						list.add(images);
					}
					return list;
				} else {
					return null;
				}
			} else {
				throw new MyHttpException(jsonObject.optString("Message"), jsonObject.optString("Message"));
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 获取用户个人信息
	 * 
	 * @param memberId
	 *            用户会员ID
	 * @param resumeId
	 *            简历ID（没有简历值为0）
	 * @return
	 * @throws MyHttpException
	 */
	public static MemberMessage getPersonalInfo(int memberId, int resumeId) throws MyHttpException {
		String url = PERSONAL_INFO + "/" + String.valueOf(memberId) + "/" + String.valueOf(resumeId);
		String json = HttpUtil.get(url, null);
		JSONObject jsonObject;
		try {
			jsonObject = new JSONObject(json);
			boolean state = jsonObject.optBoolean("State");
			if (state) {
				String valueString = jsonObject.optString("Value");
				Gson gson = new GsonBuilder().serializeNulls().create();
				MemberMessage object = gson.fromJson(valueString, MemberMessage.class);
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
	 * 获取我的简历
	 * 
	 * @param resumeId
	 *            简历ID
	 * @return 1、Date：创建时间 2、RefreshDate：刷新时间 3、UserName：用户账号
	 * @throws MyHttpException
	 */
	public static ResumeInfo getMyResume(int resumeId) throws MyHttpException {
		String url = MY_RESUME + "/" + String.valueOf(resumeId);
		String json = HttpUtil.get(url, null);
		JSONObject jsonObject;
		try {
			jsonObject = new JSONObject(json);
			boolean state = jsonObject.optBoolean("State");
			if (state) {
				String valueString = jsonObject.optString("Value");
				Gson gson = new GsonBuilder().serializeNulls().create();
				ResumeInfo object = gson.fromJson(valueString, ResumeInfo.class);
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
	 * 获取简历填写完成情况
	 * @param resumeId
	 * @return
	 * @throws MyHttpException
	 */
	public static ResumeBaseInfo getResumeBaseInfo(int resumeId) throws MyHttpException {
		String url = RESUME_INFO + "/" + String.valueOf(resumeId);
		String json = HttpUtil.get(url, null);
		JSONObject jsonObject;
		try {
			jsonObject = new JSONObject(json);
			boolean state = jsonObject.optBoolean("State");
			if (state) {
				String valueString = jsonObject.optString("Value");
				Gson gson = new GsonBuilder().serializeNulls().create();
				ResumeBaseInfo object = gson.fromJson(valueString, ResumeBaseInfo.class);
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
	 * 获取简历完善度
	 * @param memberId
	 * @param resumeId
	 * @return
	 * @throws MyHttpException
	 */
	public static String getResumePercent(int memberId, int resumeId) throws MyHttpException {
		String url = RESUME_INTGRITY + "/" + String.valueOf(memberId) + "/" + String.valueOf(resumeId);
		String json = HttpUtil.get(url, null);
		JSONObject jsonObject;
		try {
			jsonObject = new JSONObject(json);
			boolean state = jsonObject.optBoolean("State");
			if (state) {
				JSONObject valueJson = jsonObject.optJSONObject("Value");
				String Percent = valueJson.optString("Percent");
				return Percent;
			} else {
				throw new MyHttpException(jsonObject.optString("Message"), jsonObject.optString("Message"));
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return null;
	}
}
