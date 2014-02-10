package com.geetion.job138.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ResumeInfo implements Serializable {
	/**
	 * 基本信息 1、 Id： 简历id 2、UserName：会员账号 3、Name：中文姓名 4、 Sex：性别 5、Birth：出生日期
	 * 6、Edu：学历 7、Graduate： 毕业时间 8、Height：身高 9、Nation：民族 10、Marriage：婚姻状况
	 * 11、HuKouProvince： 户 籍省份 12、HuKouCapital： 户 籍城市 13、Province：现所在省份
	 * 14、Capital：现所在城市 15、CardType：证件类型 16、IdCard：证件编号 17、WorkYear：美业职龄
	 */
	private int Id;
	private String UserName;
	private String Name;
	private int Sex;
	private String Birth;
	private int Edu;
	private String Graduate;
	private String Height;
	private int Nation;
	private int Marriage;
	private String HuKouProvince;
	private String HuKouCapital;
	private String Province;
	private String Capital;
	//private int CardType;
	//private String IdCard;
	private String WorkYear;

	/**
	 * 联系方式 1、 Id： 简历id 2、Email：邮箱 3、Mobile：手机号码 4、Tel：固定电话 5、QQ：QQ 6、Weibo：微博
	 * 7、Address：地址
	 * 
	 * 
	 */
	private String Email;
	private String Mobile;
	private String Tel;
	private String QQ;
	private String Weibo;
	private String Address;

	/**
	 * 求职意向 1、JobType: 求职类型 2、Position: 意向岗位 id 3、PositionName:意向岗位 name,
	 * 4、Area:意向地区 id , 5、AreaName:意向地区 name , 6、Pay:月薪 , 7、Stay:提供住宿 ,
	 * 8、Travel:接受出差 , 9、WorkDate: 到岗时间, 10Request: 其他要求
	 * 
	 */

	private int JobType;
	private String Position;
	private String PositionName;
	private String Area;
	private String AreaName;
	private String Pay;
	private int Stay;
	private int Travel;
	private int WorkDate;
	private String Request;

	/**
	 * 工作经历
	 */
	private List<WorkExperience> workList = new ArrayList<WorkExperience>();

	/**
	 * 教育经历
	 * 
	 */
	private List<EduExperience> eduList = new ArrayList<EduExperience>();

	/**
	 * 1、Date：创建时间 2、RefreshDate：刷新时间 3.Advance 高级会员
	 */
	private String Date;
	private String RefreshDate;
	private int Advance;

	/**
	 * 培训经历
	 */
	private List<TrainExperience> trainList = new ArrayList<TrainExperience>();

	/**
	 * 语言能力
	 */

	private List<Lang> langList = new ArrayList<Lang>();

	/**
	 * 简历完善度
	 */
	private String percent;

	/**
	 * 自我评价
	 */
	private String Appraise;
	private String Sumup;

	/**
	 * 技能
	 */
	private Ability ability;

	public int getId() {
		return Id;
	}

	public void setId(int id) {
		Id = id;
	}

	public String getUserName() {
		return UserName;
	}

	public void setUserName(String userName) {
		UserName = userName;
	}

	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}

	public int getSex() {
		return Sex;
	}

	public void setSex(int sex) {
		Sex = sex;
	}

	public String getBirth() {
		return Birth;
	}

	public void setBirth(String birth) {
		Birth = birth;
	}

	public int getEdu() {
		return Edu;
	}

	public void setEdu(int edu) {
		Edu = edu;
	}

	public String getGraduate() {
		return Graduate;
	}

	public void setGraduate(String graduate) {
		Graduate = graduate;
	}

	public String getHeight() {
		return Height;
	}

	public void setHeight(String height) {
		Height = height;
	}

	public int getNation() {
		return Nation;
	}

	public void setNation(int nation) {
		Nation = nation;
	}

	public int getMarriage() {
		return Marriage;
	}

	public void setMarriage(int marriage) {
		Marriage = marriage;
	}

	public String getHuKouProvince() {
		return HuKouProvince;
	}

	public void setHuKouProvince(String huKouProvince) {
		HuKouProvince = huKouProvince;
	}

	public String getHuKouCapital() {
		return HuKouCapital;
	}

	public void setHuKouCapital(String huKouCapital) {
		HuKouCapital = huKouCapital;
	}

	public String getProvince() {
		return Province;
	}

	public void setProvince(String province) {
		Province = province;
	}

	public String getCapital() {
		return Capital;
	}

	public void setCapital(String capital) {
		Capital = capital;
	}

//	public int getCardType() {
//		return CardType;
//	}
//
//	public void setCardType(int cardType) {
//		CardType = cardType;
//	}
//
//	public String getIdCard() {
//		return IdCard;
//	}
//
//	public void setIdCard(String idCard) {
//		IdCard = idCard;
//	}

	public String getWorkYear() {
		return WorkYear;
	}

	public void setWorkYear(String workYear) {
		WorkYear = workYear;
	}

	public String getEmail() {
		return Email;
	}

	public void setEmail(String email) {
		Email = email;
	}

	public String getMobile() {
		return Mobile;
	}

	public void setMobile(String mobile) {
		Mobile = mobile;
	}

	public String getTel() {
		return Tel;
	}

	public void setTel(String tel) {
		Tel = tel;
	}

	public String getQQ() {
		return QQ;
	}

	public void setQQ(String qQ) {
		QQ = qQ;
	}

	public String getWeibo() {
		return Weibo;
	}

	public void setWeibo(String weibo) {
		Weibo = weibo;
	}

	public String getAddress() {
		return Address;
	}

	public void setAddress(String address) {
		Address = address;
	}

	public int getJobType() {
		return JobType;
	}

	public void setJobType(int jobType) {
		JobType = jobType;
	}

	public String getPosition() {
		return Position;
	}

	public void setPosition(String position) {
		Position = position;
	}

	public String getPositionName() {
		return PositionName;
	}

	public void setPositionName(String positionName) {
		PositionName = positionName;
	}

	public String getArea() {
		return Area;
	}

	public void setArea(String area) {
		Area = area;
	}

	public String getAreaName() {
		return AreaName;
	}

	public void setAreaName(String areaName) {
		AreaName = areaName;
	}

	public String getPay() {
		return Pay;
	}

	public void setPay(String pay) {
		Pay = pay;
	}

	public int getStay() {
		return Stay;
	}

	public void setStay(int stay) {
		Stay = stay;
	}

	public int getTravel() {
		return Travel;
	}

	public void setTravel(int travel) {
		Travel = travel;
	}

	public int getWorkDate() {
		return WorkDate;
	}

	public void setWorkDate(int workDate) {
		WorkDate = workDate;
	}

	public String getRequest() {
		return Request;
	}

	public void setRequest(String request) {
		Request = request;
	}

	public List<WorkExperience> getWorkList() {
		return workList;
	}

	public void setWorkList(List<WorkExperience> workList) {
		this.workList.clear();
		this.workList.addAll(workList);
	}

	public List<EduExperience> getEduList() {
		return eduList;
	}

	public void setEduList(List<EduExperience> eduList) {
		this.eduList.clear();
		this.eduList.addAll(eduList);
	}

	public List<TrainExperience> getTrainList() {
		return trainList;
	}

	public void setTrainList(List<TrainExperience> trainList) {
		this.trainList.clear();
		this.trainList.addAll(trainList);
	}

	public List<Lang> getLangList() {
		return langList;
	}

	public void setLangList(List<Lang> langList) {
		this.langList.clear();
		this.langList.addAll(langList);
	}

	public String getDate() {
		return Date;
	}

	public void setDate(String date) {
		Date = date;
	}

	public String getRefreshDate() {
		return RefreshDate;
	}

	public void setRefreshDate(String refreshDate) {
		RefreshDate = refreshDate;
	}

	public int getAdvance() {
		return Advance;
	}

	public void setAdvance(int advance) {
		Advance = advance;
	}

	public String getPercent() {
		return percent;
	}

	public void setPercent(String percent) {
		this.percent = percent;
	}

	public String getAppraise() {
		return Appraise;
	}

	public void setAppraise(String appraise) {
		Appraise = appraise;
	}

	public String getSumup() {
		return Sumup;
	}

	public void setSumup(String sumup) {
		Sumup = sumup;
	}

	public void saveResumeBaseInfo(ResumeInfo resumeBaseInfo) {
		setName(resumeBaseInfo.getName());
		setSex(resumeBaseInfo.getSex());
		setBirth(resumeBaseInfo.getBirth());
		setEdu(resumeBaseInfo.getEdu());
		setGraduate(resumeBaseInfo.getGraduate());
		setHeight(resumeBaseInfo.getHeight());
		setNation(resumeBaseInfo.getNation());
		setMarriage(resumeBaseInfo.getMarriage());
		setHuKouProvince(resumeBaseInfo.getHuKouProvince());
		setHuKouCapital(resumeBaseInfo.getHuKouCapital());
		setCapital(resumeBaseInfo.getCapital());
		setProvince(resumeBaseInfo.getProvince());
		//setCardType(resumeBaseInfo.getCardType());
		//setIdCard(resumeBaseInfo.getIdCard());
		setWorkYear(resumeBaseInfo.getWorkYear());
	}

	public void saveResumeContactInfo(ResumeInfo resumeContact) {
		setEmail(resumeContact.getEmail());
		setMobile(resumeContact.getMobile());
		setTel(resumeContact.getTel());
		setQQ(resumeContact.getQQ());
		setWeibo(resumeContact.getWeibo());
		setAddress(resumeContact.getAddress());
	}

	public void saveResumeJobIntentInfo(ResumeInfo intentContact) {
		setJobType(intentContact.getJobType());
		setPosition(intentContact.getPosition());
		setPositionName(intentContact.getPositionName());
		setArea(intentContact.getArea());
		setAreaName(intentContact.getAreaName());
		setPay(intentContact.getPay());
		setStay(intentContact.getStay());
		setTravel(intentContact.getTravel());
		setWorkDate(intentContact.getWorkDate());
		setRequest(intentContact.getRequest());
	}

	public void saveAppraise(ResumeInfo appraiseInfo) {
		setAppraise(appraiseInfo.getAppraise());
		setSumup(appraiseInfo.getSumup());
	}

	public Ability getAbility() {
		return ability;
	}

	public void setAbility(Ability ability) {
		this.ability = ability;
	}

}
