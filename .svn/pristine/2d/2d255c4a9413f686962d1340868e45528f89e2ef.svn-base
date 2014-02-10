package com.geetion.job138.service;

import org.json.JSONException;
import org.json.JSONObject;

import android.R.string;

import com.geetion.job138.model.JobInfo;
import com.geetion.job138.util.HttpUtil;
import com.geetion.job138.util.MyHttpException;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

public class BallotService extends BaseService {
	private final static String BALLOT = SERVER_URL + "/Job/Ballot";

	/**
	 * 
	 * @param appType
	 *            手机操作系统（Android = 1,iPhoneOS = 2,Symbian = 3,WinPhone = 4）
	 * @param workAdd
	 *            地区ID
	 * @param resumeId
	 *            简历ID（未登录为0）
	 * @return
	 * @throws MyHttpException
	 */
	public static JobInfo getBallotJob(int resumeId, String workAdd) throws MyHttpException {
		String url = BALLOT + "/1/" + String.valueOf(resumeId) + "/" + workAdd;
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

}
