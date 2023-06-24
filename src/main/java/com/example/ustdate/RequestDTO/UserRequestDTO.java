package com.example.ustdate.RequestDTO;

import com.example.ustdate.entity.UserRegistering;

import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRequestDTO {
	
	private String name;
	
	private String phoneNumber;
	
	private String gender;
	
	private String genderPref;
	
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

	public UserRequestDTO(UserRegistering register) {
		this.setGender(register.getGender());
		this.setGenderPref(register.getGenderPref());
		this.setName(register.getName());
		this.setPhoneNumber(register.getChatId());
		this.setAge(register.getAge());
		this.setAgePref(register.getAgePref());
	}
	
	
}
