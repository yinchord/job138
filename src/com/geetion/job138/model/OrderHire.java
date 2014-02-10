package com.geetion.job138.model;

import java.io.Serializable;

public class OrderHire implements Serializable {
	private int HireId;
	private String HireName;
	private String CompanyName;
	private String Area;
	private String Pay;
	private String AnnounceDate;

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

}
