package com.geetion.job138.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.geetion.job138.model.Comment;
import com.geetion.job138.model.JobInfo;
import com.geetion.job138.model.MemberMessage;
import com.geetion.job138.model.PageUtil;
import com.geetion.job138.util.HttpUtil;
import com.geetion.job138.util.MyHttpException;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

public class JobManageService extends BaseService {
	private final static String WORK_APPLY = SERVER_URL + "/work/Apply/List";
	private final static String WORK_INTERVIEW = SERVER_URL + "/work/Interview/List";
	private final static String WORK_FAVORITE = SERVER_URL + "/work/Favorite/List";
	private final static String RESUME_READED = SERVER_URL + "/work/See/List";
	private final static String COMMENTS_LIST = SERVER_URL + "/work/Comments/List";
	private final static String INTERVIEW_NOREAD = SERVER_URL + "/work/Interview/NoRead";
	private final static String JOB_MANAGAGE_INFO = SERVER_URL + "/work/WorkInfo";

	private final static String APPLY_POSITION = SERVER_URL + "/Work/Apply/1";
	private final static String FAVORITE_POSITION = SERVER_URL + "/Work/Favorite/1";
	private final static String TEL_RECORD = SERVER_URL + "/Work/TelRecord/1";
	private final static String REPLY_COMMENTS = SERVER_URL + "/Work/Comments/Reply/1";
	private final static String DEL_COMMENTS = SERVER_URL + "/Work/Comments/Del/1";

	/***
	 * 获取应聘职位列表
	 * 
	 * @param pageUtil
	 * @param memberId
	 *            用户会员ID
	 * @return 1、Company：公司名 2、Time：应聘时间 3、HireId：职位ID 4、HireName：职位名
	 *         5、Id：应聘记录ID 6、Area：地区 7、Pay：月薪 8、Pages:页数
	 * @throws MyHttpException
	 */
	public static List<JobInfo> getWorkApply(PageUtil pageUtil, int memberId) throws MyHttpException {
		String url = WORK_APPLY + "/" + pageUtil.getPageSize() + "/" + pageUtil.getPageNo() + "/" + String.valueOf(memberId);
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
				String ListOtherHire = valueObject.optString("ListFavorite");
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

	/***
	 * 获取邀请面试列表
	 * 
	 * @param pageUtil
	 * @param memberId
	 *            用户会员ID
	 * @return 1、Company：公司名 2、Time：应聘时间 3、HireId：职位ID 4、HireName：职位名
	 *         5、Id：邀请面试ID 6、isRead：是否已读（1为已读，0为未读） 7、Pages:页数
	 * @throws MyHttpException
	 */
	public static List<JobInfo> getWorkInterview(PageUtil pageUtil, int memberId) throws MyHttpException {
		String url = WORK_INTERVIEW + "/" + pageUtil.getPageSize() + "/" + pageUtil.getPageNo() + "/" + String.valueOf(memberId);
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
				String ListOtherHire = valueObject.optString("ListInterview");
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

	/***
	 * 获取收藏夹列表
	 * 
	 * @param pageUtil
	 * @param memberId
	 *            用户会员ID
	 * @return 1、Company：公司名 2、Time：应聘时间 3、HireId：职位ID 4、HireName：职位名
	 *         5、Id：应聘记录ID 6、Area：地区 7、Pay：月薪 8、Pages:页数
	 * @throws MyHttpException
	 */
	public static List<JobInfo> getWorkFavorite(PageUtil pageUtil, int memberId) throws MyHttpException {
		String url = WORK_FAVORITE + "/" + pageUtil.getPageSize() + "/" + pageUtil.getPageNo() + "/" + String.valueOf(memberId);
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
				String ListOtherHire = valueObject.optString("ListFavorite");
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

	/***
	 * 获取简历被看列表
	 * 
	 * @param pageUtil
	 * @param memberId
	 *            用户会员ID
	 * @return 1、Company：公司名 2、Time：应聘时间 3、Id：公司ID
	 * @throws MyHttpException
	 */
	public static List<JobInfo> getResumeReaded(PageUtil pageUtil, int memberId) throws MyHttpException {
		String url = RESUME_READED + "/" + pageUtil.getPageSize() + "/" + pageUtil.getPageNo() + "/" + String.valueOf(memberId);
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
				String ListOtherHire = valueObject.optString("ListSeeResume");
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

	/***
	 * 获取评论留言列表
	 * 
	 * @param pageUtil
	 * @param memberId
	 *            用户会员ID
	 * @return
	 * @throws MyHttpException
	 */
	public static List<Comment> getCommentsList(PageUtil pageUtil, int memberId) throws MyHttpException {
		String url = COMMENTS_LIST + "/" + pageUtil.getPageSize() + "/" + pageUtil.getPageNo() + "/" + String.valueOf(memberId);
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
				String ListOtherHire = valueObject.optString("ListGuestBook");
				List<Comment> list = gson.fromJson(ListOtherHire, new TypeToken<List<Comment>>() {
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
	 * 获取邀请面试未读数
	 * 
	 * @param memberId
	 * @return
	 * @throws MyHttpException
	 */
	public static int getInterviewNoRead(int memberId) throws MyHttpException {
		String url = INTERVIEW_NOREAD + "/" + String.valueOf(memberId);
		String json = HttpUtil.get(url, null);
		JSONObject jsonObject;
		try {
			jsonObject = new JSONObject(json);
			boolean state = jsonObject.optBoolean("State");
			if (state) {
				JSONObject valueJson = jsonObject.optJSONObject("Value");
				int num = valueJson.optInt("Nums");
				return num;
			} else {
				throw new MyHttpException(jsonObject.optString("Message"), jsonObject.optString("Message"));
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return -1;
	}

	/***
	 * 获取作管理信息
	 * 
	 * @param memberId
	 * @return
	 * @throws MyHttpException
	 */
	public static MemberMessage getJobManageInfo(int memberId) throws MyHttpException {
		String json = HttpUtil.get(JOB_MANAGAGE_INFO + "/" + String.valueOf(memberId), null);
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
	 * 应聘职位
	 * 
	 * @param UserName
	 * @param HireId
	 * @param Rid
	 * @return
	 * @throws MyHttpException
	 */
	public static int applyPosition(String UserName, int HireId, int Rid) throws MyHttpException {
		Map<String, String> params = new HashMap<String, String>();
		params.put("UserName", UserName);
		params.put("HireId", String.valueOf(HireId));
		params.put("Rid", String.valueOf(Rid));
		String json = HttpUtil.postJSON(APPLY_POSITION, params);
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

	/**
	 * 收藏职位
	 * 
	 * @param UserName
	 * @param HireId
	 * @return
	 * @throws MyHttpException
	 */
	public static int favoritePosition(String UserName, int HireId) throws MyHttpException {
		Map<String, String> params = new HashMap<String, String>();
		params.put("UserName", UserName);
		params.put("HireId", String.valueOf(HireId));
		String json = HttpUtil.postJSON(FAVORITE_POSITION, params);
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

	/**
	 * 拨打电话记录
	 * 
	 * @param UserName
	 * @param HireId
	 * @param Cid
	 * @param Tel
	 * @return
	 * @throws MyHttpException
	 */
	public static int addTelRecord(String UserName, int HireId, int Cid, int Tel) throws MyHttpException {
		Map<String, String> params = new HashMap<String, String>();
		params.put("UserName", UserName);
		params.put("HireId", String.valueOf(HireId));
		params.put("Cid", String.valueOf(Cid));
		params.put("Tel", String.valueOf(Tel));
		String json = HttpUtil.postJSON(TEL_RECORD, params);
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

	/**
	 * 回复留言评价
	 * 
	 * @param UserName
	 * @param Content
	 * @param Id
	 * @return
	 * @throws MyHttpException
	 */
	public static int replyComments(String UserName, String Content, int Id) throws MyHttpException {
		Map<String, String> params = new HashMap<String, String>();
		params.put("UserName", UserName);
		params.put("Content", Content);
		params.put("Id", String.valueOf(Id));
		String json = HttpUtil.postJSON(REPLY_COMMENTS, params);
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

	/**
	 * 删除留言评价
	 * 
	 * @param id
	 * @return
	 * @throws MyHttpException
	 */
	public static int delComments(int id) throws MyHttpException {
		String json = HttpUtil.get(DEL_COMMENTS + "/" + String.valueOf(id), null);
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
