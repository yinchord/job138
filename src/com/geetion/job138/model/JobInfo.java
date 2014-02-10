package com.geetion.job138.model;

import java.io.Serializable;

public class JobInfo implements Serializable {
	/**
	 * 1、HireId:职位Id 2、HireName：职位名 3、CompanyName:公司名 , 4、Area:地区 , 5、Pay：月薪
	 * 6、AnnounceDate：发布时间 7、Contact：联系人 8、Tel：电话 9、Address：地址
	 * 10、Map：是否标识地图（1为是，0为否） 11、CompanyId:公司ID 12、EndDate:招聘期限 13、Experience:
	 * 工作经验 14、Introduce:职位简介 15、Number:招聘人数 16、Type:签类型(上上签为1，中上签为2)
	 * 18、Time：应聘时间 17、Id:应聘记录ID 18、isRead：是否已读（1为已读，0为未读）
	 * 19、Company：公司名（接口命名不一致）
	 */

	private int HireId;
	private String HireName;
	private String CompanyName;
	private String Area;
	private String Pay;
	private String AnnounceDate;
	private String Contact;
	private String Tel;
	private String Address;
	private int Map;
	private int CompanyId;
	private String EndDate;
	private String Experience;
	private String Introduce;
	private String Number;
	private int Type;
	private String Time;
	private int Id;
	private int isRead;
	private String Company;
	private Float[] lable = new Float[2];

	public Float[] getLable() {
		return lable;
	}

	public void setLable(Float[] lable) {
		this.lable = lable;
	}

	public int getHireId() {
		return HireId;
	}

	public void setHireId(int hireId) {
		HireId = hireId;
	}

	public String getHireName() {
		return HireName;
	}

	public void setHireName(String hireName) {
		HireName = hireName;
	}

	public String getCompanyName() {
		return CompanyName;
	}

	public void setCompanyName(String companyName) {
		CompanyName = companyName;
	}

	public String getArea() {
		return Area;
	}

	public void setArea(String area) {
		Area = area;
	}

	public String getPay() {
		return Pay;
	}

	public void setPay(String pay) {
		Pay = pay;
	}

	public String getAnnounceDate() {
		return AnnounceDate;
	}

	public void setAnnounceDate(String announceDate) {
		AnnounceDate = announceDate;
	}

	public String getContact() {
		return Contact;
	}

	public void setContact(String contact) {
		Contact = contact;
	}

	public String getTel() {
		return Tel;
	}

	public void setTel(String tel) {
		Tel = tel;
	}

	public String getAddress() {
		return Address;
	}

	public void setAddress(String address) {
		Address = address;
	}

	public int getMap() {
		return Map;
	}

	public void setMap(int map) {
		Map = map;
	}

	public int getCompanyId() {
		return CompanyId;
	}

	public void setCompanyId(int companyId) {
		CompanyId = companyId;
	}

	public String getEndDate() {
		return EndDate;
	}

	public void setEndDate(String endDate) {
		EndDate = endDate;
	}

	public String getExperience() {
		return Experience;
	}

	public void setExperience(String experience) {
		Experience = experience;
	}

	public String getIntroduce() {
		return Introduce;
	}

	public void setIntroduce(String introduce) {
		Introduce = introduce;
	}

	public String getNumber() {
		return Number;
	}

	public void setNumber(String number) {
		Number = number;
	}

	public int getType() {
		return Type;
	}

	public void setType(int type) {
		Type = type;
	}

	public String getTime() {
		return Time;
	}

	public void setTime(String time) {
		Time = time;
	}

	public int getId() {
		return Id;
	}

	public void setId(int id) {
		Id = id;
	}

	public int getIsRead() {
		return isRead;
	}

	public void setIsRead(int isRead) {
		this.isRead = isRead;
	}

	public String getCompany() {
		return Company;
	}

	public void setCompany(String company) {
		Company = company;
	}

}
