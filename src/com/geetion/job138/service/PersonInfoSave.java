package com.geetion.job138.service;

import java.util.List;

import android.content.Context;

import com.geetion.job138.fragment.resume.AbilityInfoFragment;
import com.geetion.job138.fragment.resume.BaseInfoFragment;
import com.geetion.job138.model.EduExperience;
import com.geetion.job138.model.Lang;
import com.geetion.job138.model.MemberMessage;
import com.geetion.job138.model.ResumeInfo;
import com.geetion.job138.model.TrainExperience;
import com.geetion.job138.model.WorkExperience;

public class PersonInfoSave {
	public static ResumeInfo resumeInfo = new ResumeInfo();
	public static MemberMessage memberInfo = new MemberMessage();

	public static void updateSaveID(Context context, int resumeId) {
		resumeInfo.setId(resumeId);
		SettingService.updateLoginId(context, resumeId);
	}

	public static void saveContact(ResumeInfo contactInfo) {
		if (contactInfo.getEmail() != null)
			resumeInfo.setEmail(contactInfo.getEmail());
		if (contactInfo.getMobile() != null)
			resumeInfo.setMobile(contactInfo.getMobile());
		if (contactInfo.getTel() != null)
			resumeInfo.setTel(contactInfo.getTel());
		if (contactInfo.getQQ() != null)
			resumeInfo.setQQ(contactInfo.getQQ());
		if (contactInfo.getWeibo() != null)
			resumeInfo.setWeibo(contactInfo.getWeibo());
		if (contactInfo.getAddress() != null)
			resumeInfo.setAddress(contactInfo.getAddress());
	}

	public static void saveMemberMessage(MemberMessage member) {
		if (member.getAvatar() != null)
			memberInfo.setAvatar(member.getAvatar());
		if (member.getEmail() != null)
			memberInfo.setEmail(member.getEmail());
		if (member.getMobile() != null)
			memberInfo.setMobile(member.getMobile());
		if (member.getUserName() != null)
			memberInfo.setUserName(member.getUserName());
		if (member.getStatus() != null)
			memberInfo.setStatus(member.getStatus());
		if (member.getNoInterview() != 0)
			memberInfo.setNoInterview(member.getNoInterview());
		if (member.getInterview() != 0)
			memberInfo.setInterview(member.getInterview());
		if (member.getNoComments() != 0)
			memberInfo.setNoComments(member.getNoComments());
		if (member.getFavorite() != 0)
			memberInfo.setFavorite(member.getFavorite());
		if (member.getCandidates() != 0)
			memberInfo.setCandidates(member.getCandidates());
		if (member.getSee() != 0)
			memberInfo.setSee(member.getSee());
		if (member.getComments() != 0)
			memberInfo.setComments(member.getComments());
	}

	public static void saveResumeBaseInfo(ResumeInfo resumeBaseInfo) {
		resumeInfo.setName(resumeBaseInfo.getName());
		resumeInfo.setSex(resumeBaseInfo.getSex());
		resumeInfo.setBirth(resumeBaseInfo.getBirth());
		resumeInfo.setEdu(resumeBaseInfo.getEdu());
		resumeInfo.setGraduate(resumeBaseInfo.getGraduate());
		resumeInfo.setHeight(resumeBaseInfo.getHeight());
		resumeInfo.setNation(resumeBaseInfo.getNation());
		resumeInfo.setMarriage(resumeBaseInfo.getMarriage());
		resumeInfo.setHuKouProvince(resumeBaseInfo.getHuKouProvince());
		resumeInfo.setHuKouCapital(resumeBaseInfo.getHuKouCapital());
		resumeInfo.setCapital(resumeBaseInfo.getCapital());
		resumeInfo.setProvince(resumeBaseInfo.getProvince());
		// resumeInfo.setCardType(resumeBaseInfo.getCardType());
		// resumeInfo.setIdCard(resumeBaseInfo.getIdCard());
		resumeInfo.setWorkYear(resumeBaseInfo.getWorkYear());
	}

	public static void saveResumeContactInfo(ResumeInfo resumeContact) {
		resumeInfo.setEmail(resumeContact.getEmail());
		resumeInfo.setMobile(resumeContact.getMobile());
		resumeInfo.setTel(resumeContact.getTel());
		resumeInfo.setQQ(resumeContact.getQQ());
		resumeInfo.setWeibo(resumeContact.getWeibo());
		resumeInfo.setAddress(resumeContact.getAddress());
	}

	public static void saveResumeJobIntentInfo(ResumeInfo intentContact) {
		resumeInfo.setJobType(intentContact.getJobType());
		resumeInfo.setPosition(intentContact.getPosition());
		resumeInfo.setPositionName(intentContact.getPositionName());
		resumeInfo.setArea(intentContact.getArea());
		resumeInfo.setAreaName(intentContact.getAreaName());
		resumeInfo.setPay(intentContact.getPay());
		resumeInfo.setStay(intentContact.getStay());
		resumeInfo.setTravel(intentContact.getTravel());
		resumeInfo.setWorkDate(intentContact.getWorkDate());
		resumeInfo.setRequest(intentContact.getRequest());
	}

	public static void saveEduList(List<EduExperience> list) {
		resumeInfo.setEduList(list);
	}

	public static void saveTrainList(List<TrainExperience> list) {
		resumeInfo.setTrainList(list);
	}

	public static void saveLangList(List<Lang> list) {
		resumeInfo.setLangList(list);
	}

	public static void saveWorkExperienceList(List<WorkExperience> list) {
		resumeInfo.setWorkList(list);
	}

	public static void saveResumeAppraise(ResumeInfo appraiseInfo) {
		resumeInfo.setAppraise(appraiseInfo.getAppraise());
		resumeInfo.setSumup(appraiseInfo.getSumup());
	}

	public static void clear() {
		resumeInfo = new ResumeInfo();
		memberInfo = new MemberMessage();
	}
}
