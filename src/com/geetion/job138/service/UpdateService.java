package com.geetion.job138.service;

import java.util.HashMap;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import com.geetion.job138.model.UpdateMessage;
import com.geetion.job138.util.HttpUtil;
import com.geetion.job138.util.MyHttpException;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class UpdateService extends BaseService {
	private final static String UPDATE = SERVER_URL + "/update/1";

	/**
	 * 检测是否有更新
	 * 
	 * @param context
	 * @return
	 * @throws MyHttpException
	 */
	public static UpdateMessage checkUpdate(Context context) throws MyHttpException {
		PackageManager pm = context.getPackageManager();// context为当前Activity上下文
		PackageInfo pi;
		try {
			pi = pm.getPackageInfo(context.getPackageName(), 0);
			String version = pi.versionName;
			Map<String, String> params = new HashMap<String, String>();
			params.put("version", version);
			String json = HttpUtil.postJSON(UPDATE, params);
			JSONObject jsonObject;
			jsonObject = new JSONObject(json);
			boolean state = jsonObject.optBoolean("State");
			if (state) {
				String valueString = jsonObject.optString("Value");
				Gson gson = new GsonBuilder().serializeNulls().create();
				UpdateMessage updateMessage = gson.fromJson(valueString, UpdateMessage.class);
				return updateMessage;
			} else {
				throw new MyHttpException(jsonObject.optString("Message"), jsonObject.optString("Message"));
			}
		} catch (JSONException e) {
			e.printStackTrace();
		} catch (NameNotFoundException e1) {
			e1.printStackTrace();
		}
		return null;
	}
}
