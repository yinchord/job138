package com.geetion.job138.model;

import java.io.Serializable;

public class EduExperience implements Serializable {
	private int Id;
	private int EduId;
	private String StartYear;
	private String StartMonth;
	private String EndYear;
	private String EndMonth;
	private String School;
	private String Education;
	private String Profession;
	private String Detail;
	private String UserName;

	public int getId() {
		return Id;
	}

	public void setId(int id) {
		Id = id;
	}

	public int getEduId() {
		return EduId;
	}

	public void setEduId(int eduId) {
		EduId = eduId;
	}

	public String getStartYear() {
		return StartYear;
	}

	public void setStartYear(String startYear) {
		StartYear = startYear;
	}

	public String getStartMonth() {
		return StartMonth;
	}

	public void setStartMonth(String startMonth) {
		StartMonth = startMonth;
	}

	public String getEndYear() {
		return EndYear;
	}

	public void setEndYear(String endYear) {
		EndYear = endYear;
	}

	public String getEndMonth() {
		return EndMonth;
	}

	public void setEndMonth(String endMonth) {
		EndMonth = endMonth;
	}

	public String getSchool() {
		return School;
	}

	public void setSchool(String school) {
		School = school;
	}

	public String getEducation() {
		return Education;
	}

	public void setEducation(String education) {
		Education = education;
	}

	public String getProfession() {
		return Profession;
	}

	public void setProfession(String profession) {
		Profession = profession;
	}

	public String getDetail() {
		return Detail;
	}

	public void setDetail(String detail) {
		Detail = detail;
	}

	public String getUserName() {
		return UserName;
	}

	public void setUserName(String userName) {
		UserName = userName;
	}

}
