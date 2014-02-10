package com.geetion.job138.service;

import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import com.geetion.job138.model.Company;
import com.geetion.job138.model.MemberMessage;
import com.geetion.job138.model.PageUtil;
import com.geetion.job138.util.HttpUtil;
import com.geetion.job138.util.MyHttpException;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

public class IndexPageService extends BaseService {
	private final static String PERSONAL_LOGIN_MESSAGE = SERVER_URL + "/Work/IndexPerson";
	private final static String VIP_COMPANY_LIST = SERVER_URL + "/Job/VipCompany";
	private final static String CALENDAR = SERVER_URL + "/Common/Calendar";
		
	/*****
	 * 
	 * 获取首页的个人信息（登录后）
	 * 
	 * @param memberId
	 *        会员编号
	 * @return MemberMessage
	 *         
	 * @throws MyHttpException
	 *****/
	public static MemberMessage getMemberMessage(int memberId) throws MyHttpException {
		String json = HttpUtil.get(PERSONAL_LOGIN_MESSAGE + "/" + String.valueOf(memberId), null);
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
	
	/*****
	 * 
	 * 获取首页的公司列表
	 * 
	 * @param pageSize
	 *        条数
	 * @param pageIndex
	 *        页数
	 * @param workAdd
	 *        地区名（不用带市县区，如：”广州市“传”广州“就可以了）       
	 * @return MemberMessage
	 *         
	 * @throws MyHttpException
	 *****/
	public static List<Company> getVipCompanyList(PageUtil pageUtil, String workAdd) throws MyHttpException {
		String url = VIP_COMPANY_LIST + "/" + String.valueOf(pageUtil.getPageSize()) + "/" + String.valueOf(pageUtil.getPageNo()) + "/" + String.valueOf(workAdd);
		String json = HttpUtil.get(url, null);
		JSONObject jsonObject;
		try {
			jsonObject = new JSONObject(json);
			boolean state = jsonObject.optBoolean("State");
			if (state) {
				JSONObject valueJson = jsonObject.optJSONObject("Value");
				int totalPageSize = valueJson.optInt("Pages");
				pageUtil.setPageCount(totalPageSize);
				Gson gson = new GsonBuilder().serializeNulls().create();
				String listCompany = valueJson.optString("ListCompanyModel");
				List<Company> list = gson.fromJson(listCompany, new TypeToken<List<Company>>() {
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
	
	/*****
	 * 
	 * 获取农历日期
	 *       
	 * @throws MyHttpException
	 *****/
	public static String getCalendar() throws MyHttpException {
		String json = HttpUtil.get(CALENDAR, null);
		JSONObject jsonObject;
		try {
			jsonObject = new JSONObject(json);
			boolean state = jsonObject.optBoolean("State");
			if (state) {
				JSONObject valueJson = jsonObject.optJSONObject("Value");
				String Calendar = valueJson.optString("Calendar");
				return Calendar;
			} else {
				throw new MyHttpException(jsonObject.optString("Message"), jsonObject.optString("Message"));
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return null;
	}
}
