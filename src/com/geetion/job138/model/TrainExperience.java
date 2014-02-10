package com.geetion.job138.model;

import java.io.Serializable;

public class TrainExperience implements Serializable {
	private int Id;
	private int TrainId;
	private String StartYear;
	private String StartMonth;
	private String EndYear;
	private String EndMonth;
	private String Train;
	private String Course;
	private String Address;
	private String Certificate;
	private String Detail;
	private String UserName;

	public int getId() {
		return Id;
	}

	public void setId(int id) {
		Id = id;
	}

	public int getTrainId() {
		return TrainId;
	}

	public void setTrainId(int trainId) {
		TrainId = trainId;
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

	public String getTrain() {
		return Train;
	}

	public void setTrain(String train) {
		Train = train;
	}

	public String getCourse() {
		return Course;
	}

	public void setCourse(String course) {
		Course = course;
	}

	public String getAddress() {
		return Address;
	}

	public void setAddress(String address) {
		Address = address;
	}

	public String getCertificate() {
		return Certificate;
	}

	public void setCertificate(String certificate) {
		Certificate = certificate;
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

	public String getEndMonth() {
		return EndMonth;
	}

	public void setEndMonth(String endMonth) {
		EndMonth = endMonth;
	}

}
