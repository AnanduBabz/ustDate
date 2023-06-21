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
