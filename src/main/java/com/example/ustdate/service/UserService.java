package com.example.ustdate.service;

import java.util.List;

import org.springframework.web.bind.annotation.RequestBody;

import com.example.ustdate.RequestDTO.UserRequestDTO;
import com.example.ustdate.entity.User;

public interface UserService {

	public String save(List<User> user);
	
	public List<User> getAll();
	
	public User save(UserRequestDTO user);
	
	public User getUserByUserName(String userName);
	
}
