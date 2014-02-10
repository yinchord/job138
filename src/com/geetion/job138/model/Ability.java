package com.geetion.job138.model;

import java.io.Serializable;

public class Ability implements Serializable {
	private int Id;
	private String Certificate;
	private String Development;
	private String Techniques;

	public int getId() {
		return Id;
	}

	public void setId(int id) {
		Id = id;
	}

	public String getCertificate() {
		return Certificate;
	}

	public void setCertificate(String certificate) {
		Certificate = certificate;
	}

	public String getDevelopment() {
		return Development;
	}

	public void setDevelopment(String development) {
		Development = development;
	}

	public String getTechniques() {
		return Techniques;
	}

	public void setTechniques(String techniques) {
		Techniques = techniques;
	}

}
