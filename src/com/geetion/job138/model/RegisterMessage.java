package com.geetion.job138.model;

import java.io.Serializable;

public class RegisterMessage implements Serializable {
	private String UserName;
	private String UserPwd;
	private String Email;
	private String Mobile;
	private String Pname;
	private String Sex;
	private String Birth;
	private String Position;
	private String WorkAdd;
	private String Pay;
	private String HireId;

	public String getUserName() {
		return UserName;
	}

	public void setUserName(String userName) {
		UserName = userName;
	}

	public String getUserPwd() {
		return UserPwd;
	}

	public void setUserPwd(String userPwd) {
		UserPwd = userPwd;
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

	public String getPname() {
		return Pname;
	}

	public void setPname(String pname) {
		Pname = pname;
	}

	public String getSex() {
		return Sex;
	}

	public void setSex(String sex) {
		Sex = sex;
	}

	public String getBirth() {
		return Birth;
	}

	public void setBirth(String birth) {
		Birth = birth;
	}

	public String getPosition() {
		return Position;
	}

	public void setPosition(String position) {
		Position = position;
	}

	public String getWorkAdd() {
		return WorkAdd;
	}

	public void setWorkAdd(String workAdd) {
		WorkAdd = workAdd;
	}

	public String getPay() {
		return Pay;
	}

	public void setPay(String pay) {
		Pay = pay;
	}

	public String getHireId() {
		return HireId;
	}

	public void setHireId(String hireId) {
		HireId = hireId;
	}

}
