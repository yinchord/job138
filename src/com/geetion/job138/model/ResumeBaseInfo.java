package com.geetion.job138.model;

public class ResumeBaseInfo {
	/**
	 *  1、BaseInfo：基本信息
		2、Contact：联系方式
		3、QiZhi：求职意向
		4、Appraise：自我评价（1为已填写，0为未填写）
		5、Work：工作经验（1为已填写，0为未填写）
		6、Education： 教育经历（1为已填写，0为未填写）
		7、Training：培训经历（1为已填写，0为未填写）
		8、Lang：语言能力（1为已填写，0为未填写）
		9、JiNeng：专业技能（1为已填写，0为未填写）
	 */
	String BaseInfo;
	String Contact;
	String QiZhi;
	int Appraise;
	int Work;
	int Education;
	int Training;
	int Lang;
	int JiNeng;
	public String getBaseInfo() {
		return BaseInfo;
	}
	public void setBaseInfo(String baseInfo) {
		BaseInfo = baseInfo;
	}
	public String getContact() {
		return Contact;
	}
	public void setContact(String contact) {
		Contact = contact;
	}
	public String getQiZhi() {
		return QiZhi;
	}
	public void setQiZhi(String qiZhi) {
		QiZhi = qiZhi;
	}
	public int getAppraise() {
		return Appraise;
	}
	public void setAppraise(int appraise) {
		Appraise = appraise;
	}
	public int getWork() {
		return Work;
	}
	public void setWork(int work) {
		Work = work;
	}
	public int getEducation() {
		return Education;
	}
	public void setEducation(int education) {
		Education = education;
	}
	public int getTraining() {
		return Training;
	}
	public void setTraining(int training) {
		Training = training;
	}
	public int getLang() {
		return Lang;
	}
	public void setLang(int lang) {
		Lang = lang;
	}
	public int getJiNeng() {
		return JiNeng;
	}
	public void setJiNeng(int jiNeng) {
		JiNeng = jiNeng;
	}
	
	
}
