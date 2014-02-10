package com.geetion.job138.service;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.client.HttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import com.geetion.job138.util.HttpUtil;
import com.geetion.job138.util.MyHttpException;

public class UploadPhotoService extends BaseService {
	private final static String UPLOAD_PHOTO = SERVER_URL + "/Image/Upload";

	public static String updateOpen(int memberId, int type, long length, String imageType, File file) throws MyHttpException {
		Map<String, File> files = new HashMap<String, File>();
		files.put("Stream", file);
		String json;
		try {
//			json = HttpUtil.post(UPLOAD_PHOTO + "/" + memberId + "/" + type + "/" + length + "/" + imageType, null, files);
			json = HttpUtil.post(UPLOAD_PHOTO + "/" + memberId + "/" + type + "/" + length + "/" + imageType, file);
			JSONObject jsonObject;
			jsonObject = new JSONObject(json);
			boolean state = jsonObject.optBoolean("State");
			if (state) {
				JSONObject valueObject = jsonObject.optJSONObject("Value");
				String imageUrl = valueObject.optString("ImageUrl");
				return imageUrl;
			} else {
				throw new MyHttpException(jsonObject.optString("Message"), jsonObject.optString("Message"));
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return null;
	}
}
