package com.geetion.job138.model;

public class MemberMessage {
	/***
	 * 1、Interview:面试邀请 , 2、NoInterview:面试邀请(未读) , 3、Comments:评论留言 ,
	 * 4、NoComments:评论留言(未读) , 5、Favorite: 收藏职位, 6、Candidates:应聘职位 , 7、See:简历被看
	 * , 8、Avatar： 头像 9、Email：邮箱 10、Mobile：电话 11、Status：求职状态 12、UserName：用户账号
	 ***/

	private int MemberId;
	private int Interview;
	private int NoInterview;
	private int Comments;
	private int NoComments;
	private int Favorite;
	private int Candidates;
	private int See;
	private String Avatar;
	private String Email;
	private String Mobile;
	private String Status;
	private String UserName;

	public int getMemberId() {
		return MemberId;
	}

	public void setMemberId(int memberId) {
		MemberId = memberId;
	}

	public int getInterview() {
		return Interview;
	}

	public void setInterview(int interview) {
		Interview = interview;
	}

	public int getNoInterview() {
		return NoInterview;
	}

	public void setNoInterview(int noInterview) {
		NoInterview = noInterview;
	}

	public int getComments() {
		return Comments;
	}

	public void setComments(int comments) {
		Comments = comments;
	}

	public int getNoComments() {
		return NoComments;
	}

	public void setNoComments(int noComments) {
		NoComments = noComments;
	}

	public int getCandidates() {
		return Candidates;
	}

	public void setCandidates(int candidates) {
		Candidates = candidates;
	}

	public int getSee() {
		return See;
	}

	public void setSee(int see) {
		See = see;
	}

	public String getAvatar() {
		return Avatar;
	}

	public void setAvatar(String avatar) {
		Avatar = avatar;
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

	public String getStatus() {
		return Status;
	}

	public void setStatus(String status) {
		Status = status;
	}

	public String getUserName() {
		return UserName;
	}

	public void setUserName(String userName) {
		UserName = userName;
	}

	public int getFavorite() {
		return Favorite;
	}

	public void setFavorite(int favorite) {
		Favorite = favorite;
	}

}
