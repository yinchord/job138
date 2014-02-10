package com.geetion.job138.test;

import java.util.HashMap;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.geetion.job138.activity.WelcomeActivity;
import com.geetion.job138.model.CityInfo;
import com.geetion.job138.model.Comment;
import com.geetion.job138.model.Company;
import com.geetion.job138.model.JobInfo;
import com.geetion.job138.model.JobType;
import com.geetion.job138.model.LoginRegisterMessage;
import com.geetion.job138.model.MemberMessage;
import com.geetion.job138.model.PageUtil;
import com.geetion.job138.model.ResumeBaseInfo;
import com.geetion.job138.model.ResumeInfo;
import com.geetion.job138.model.Station;
import com.geetion.job138.model.WorkExperience;
import com.geetion.job138.service.BallotService;
import com.geetion.job138.service.IndexPageService;
import com.geetion.job138.service.JobInfoService;
import com.geetion.job138.service.JobManageService;
import com.geetion.job138.service.LoginRegisterService;
import com.geetion.job138.service.ResumeInfoService;
import com.geetion.job138.service.ResumeManageService;
import com.geetion.job138.service.SearchJobService;
import com.geetion.job138.service.StationTypeSave;
import com.geetion.job138.util.HttpUtil;
import com.geetion.job138.util.MyHttpException;

import android.R.interpolator;
import android.app.Activity;
import android.test.ActivityInstrumentationTestCase2;
import android.util.Log;

public class MyTest extends ActivityInstrumentationTestCase2<WelcomeActivity> {
	private Activity activity;
	private final static String TAG = MyTest.class.getName();

	public MyTest() {
		super("com.geetion.job138", WelcomeActivity.class);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		activity = getActivity();
	}

	public void testPreconditions() {
		// Map<String, String> map = new HashMap<String, String>();
		// map.put("UserName", "138jobper");
		// map.put("UserPwd", "138138");
		// try {
		// String jsonString =
		// HttpUtil.postJSON("http://apijob.138mr.com/User/Login/1", map);
		// Log.e("tag", jsonString);
		// } catch (MyHttpException e) {
		// e.printStackTrace();
		// }
		try {
			//LoginRegisterMessage message = LoginRegisterService.login("138jobper", "138138");
			//ResumeManageService.
			//String s = "";
			
			//Log.e("message", message.getUserName());

			//List<WorkExperience> list = ResumeManageService.getWorkExorienceList(458735);
			//MemberMessage message = IndexPageService.getMemberMessage(559746);
			PageUtil pageUtil = new PageUtil(10, 1);
			pageUtil.setPageSize(10);
			pageUtil.setPageNo(1);
//			ResumeInfo resumeInfo = ResumeInfoService.getMyResume(458735);
//			List<JobType> list = SearchJobService.getJobType();
//			StationTypeSave.saveJobType(list);
			
			String p = ResumeInfoService.getResumePercent(559746, 458735);
//			ResumeBaseInfo info = ResumeInfoService.getResumeBaseInfo(458735);
//			MemberMessage persnol = JobManageService.getJobManageInfo(559746);
//			int num = JobManageService.getInterviewNoRead(559746);
	//		String image = ResumeInfoService.getPersonImage(559746).get(0).get("Image");
	//		List<Comment> comments = JobManageService.getCommentsList(pageUtil, 559746);
//			JobInfo ballot = BallotService.getBallotJob(1, 0, "广州");
	//		float[][] f = JobInfoServicer.getMapLable(1, 81548);
	//		List<JobInfo> jobInfos = JobManageService.getResumeReaded(pageUtil, 559746);
	//		Company company = JobInfoServicer.getCompanyInfo(1, 81548);
	//		JobInfo jobInfo = JobInfoServicer.getJobInfo(1, 209676);
	//		List<Station> list = SearchJobService.quickSearch();
	//		List<String> list = SearchJobService.getJobKeywork("美白");
	//		List<Company> company = IndexPageService.getVipCompanyList(pageUtil, "广州");
			List<JobInfo> list = SearchJobService.searchJob(1, pageUtil, "美容", 2, 0, 1090);
	//		Log.e("word", message.getAvatar());
	//		Log.e("word", list.get(1).getArea());
	//		Log.e("word", company.get(1).getArea());
			Log.e("快速搜索关键词", list.get(0).getCompanyName());
	//		Log.e("公司简介", company.getIntroduce());
	//		Log.e("职位简介", jobInfo.getIntroduce());
	//		Log.e("其他职位", comments.get(0).getContent());
	//		Log.e("坐标", f + "");
	//		Log.e("事业签", ballot.getType() + "");
	//		Log.e("大图", image);
//			List<JobType> jobTypes = new ArrayList<JobType>();
//		    for(int i = 0; i < StationTypeSave.parentJobTypes.size(); i++) {
//		    	JobType jobType = StationTypeSave.parentJobTypes.get(i);
//		    	Log.e("父类：" , jobType.getName());
//		    	jobTypes = StationTypeSave.childJobtypes.get(jobType.getId());
//		    	for(int j = 0; j < jobTypes.size(); j++) {
//		    		Log.e("**", jobTypes.get(j).getName());
//		    	}
//		    }
			Log.e("完善度", p);
		} catch (MyHttpException e) {
			Log.e("error", e.getErrorMessage());
		}
	}

	@Override
	public void tearDown() {
		activity.finish();
	}
}
