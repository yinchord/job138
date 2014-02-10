package com.geetion.job138.model;

import java.io.Serializable;

public class Search implements Serializable{

	/**
	 *1.keyWord
	 * 	            关键字
	 *2.keywordType
	 *        搜索类型（1为搜职位，2为搜公司）
	 *3.position
	 * 		    岗位ID
	 *4.area
	 *        地区ID
	 */ 
	private String keyWord;
	private int keyWordType;
	private JobType position;
	private CityInfo area;
	private int positionId;
	private String positionName;
	private int areaId;
	private String areaName;
	private static final long serialVersionUID = 1L;
	
	public Search(String keyWord, int keyWordType, JobType position, CityInfo area) {
		this.keyWord = keyWord;
		this.keyWordType = keyWordType;
		this.position = position;
		this.area = area;
		setPositionId();
		setPositionName();
		setAreaId();
		setAreaName();
	}

	public String getKeyWord() {
		return keyWord;
	}
	public void setKeyWord(String keyWord) {
		this.keyWord = keyWord;
	}
	public int getKeyWordType() {
		return keyWordType;
	}
	public void setKeyWordType(int keyWordType) {
		this.keyWordType = keyWordType;
	}

	public JobType getPosition() {
		return position;
	}

	public void setPosition(JobType position) {
		this.position = position;
	}

	public CityInfo getArea() {
		return area;
	}

	public void setArea(CityInfo area) {
		this.area = area;
	}

	public int getPositionId() {
		return positionId;
	}

	public void setPositionId() {
		if(position == null){
			this.positionId = 0;
		}else
		this.positionId = position.getId();
	}

	public String getPositionName() {
		return positionName;
	}

	public void setPositionName() {
		if(position == null) {
			this.positionName = "";
		} else
		this.positionName = position.getName();
	}

	public int getAreaId() {
		return areaId;
	}

	public void setAreaId() {
		if(area == null){
			this.areaId = 0;
		}else
			this.areaId = area.getId();
	}

	public String getAreaName() {
		return areaName;
	}

	public void setAreaName() {
		if(area == null) {
			this.areaName = "";
		} else
		this.areaName = area.getName();
	}
	
}
