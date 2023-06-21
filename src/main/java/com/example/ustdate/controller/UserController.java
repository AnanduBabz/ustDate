package com.example.ustdate.controller;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.ustdate.RequestDTO.UserRequestDTO;
import com.example.ustdate.entity.ActiveChat;
import com.example.ustdate.entity.User;
import com.example.ustdate.repository.ActiveChatRepository;
import com.example.ustdate.repository.UserRepository;
import com.example.ustdate.service.UserService;

@RestController
@RequestMapping("user")
public class UserController {

	@Autowired
	UserService service;
	
	@GetMapping
	public List<User> getAll() {
		return service.getAll();
	}

	@PostMapping
	public User save(@RequestBody UserRequestDTO user) {
		return service.save(user);
	}

	@PostMapping("/all")
	public String save(@RequestBody List<User> user) {
		return service.save(user);
	}

}
