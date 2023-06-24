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
@Table(name = "users")
@Getter
@Setter
public class User {
	
	@Id
	@Column
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column
	private String name;
	
	@Column
	private String phoneNumber;
	
	@Column
	private String gender;
	
	@Column
	private String genderPref;
	
	@Column
	private String inChat;
	
	@Column
	private String age;
	
	@Column
	private String agePref;
	

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

	public String getInChat() {
		return inChat;
	}

	public void setInChat(String inChat) {
		this.inChat = inChat;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
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
