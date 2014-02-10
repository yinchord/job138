package com.geetion.job138.service;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;
import com.geetion.job138.model.FeedBack;
import com.geetion.job138.util.HttpUtil;
import com.geetion.job138.util.MyHttpException;

public class FeedBackService extends BaseService {
	private final static String ADD_FEEDBACK = SERVER_URL + "/update/Feedback/1";

	/**
	 * 提交反馈
	 * 
	 * @param feedBack
	 * @return
	 * @throws MyHttpException
	 */
	public static int addFeedBack(FeedBack feedBack) throws MyHttpException {
		Map<String, String> params = new HashMap<String, String>();
		params.put("info", feedBack.getInfo());
		params.put("memberLogin", feedBack.getMemberLogin());

		String json = HttpUtil.postJSON(ADD_FEEDBACK, params);
		JSONObject jsonObject;
		try {
			jsonObject = new JSONObject(json);
			boolean state = jsonObject.optBoolean("State");
			if (state) {
				JSONObject valueObject = jsonObject.optJSONObject("Value");
				int stateBack = valueObject.optInt("State");
				return stateBack;
			} else {
				throw new MyHttpException(jsonObject.optString("Message"), jsonObject.optString("Message"));
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return 0;
	}
}
