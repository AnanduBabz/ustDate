package com.example.ustdate.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "user_registration")
@Getter
@Setter
public class UserRegistering {

	@Column
	private String chatId;
	
	@Id
	@Column
	private String name;
	
	@Column
	private String gender;
	
	@Column
	private String genderPref;
	
	@Column
	private String age;
	
	@Column
	private String agePref;

	@Column
	private String step;
	
	

	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}

	public String getAgePref() {
		return agePref;
	}

	public void setAgePref(String agePref) {
		this.agePref = agePref;
	}

	public String getStep() {
		return step;
	}

	public void setStep(String step) {
		this.step = step;
	}
	
	public String getChatId() {
		return chatId;
	}

	public void setChatId(String chatId) {
		this.chatId = chatId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getGenderPref() {
		return genderPref;
	}

	public void setGenderPref(String genderPref) {
		this.genderPref = genderPref;
	}
	
}
