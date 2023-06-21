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

@RestController
@RequestMapping("user")
public class UserController {

	@Autowired
	UserRepository userRepo;

	@Autowired
	ActiveChatRepository activeChatRepository;

	@GetMapping
	public List<User> getAll() {
		return userRepo.findAll();
	}

	@PostMapping
	public User save(@RequestBody UserRequestDTO user) {
		User newUser = new User();
		BeanUtils.copyProperties(user, newUser);
		newUser = userRepo.save(newUser);
		ActiveChat chat = new ActiveChat();
		chat = chat.newChat(newUser.getId());
		activeChatRepository.save(chat);
		return newUser;
	}

	@PostMapping("/all")
	public String save(@RequestBody List<User> user) {
		user = userRepo.saveAll(user);
		for (User use : user) {
			ActiveChat chat = new ActiveChat();
			chat = chat.newChat(use.getId());
			activeChatRepository.save(chat);
		}
		return "saved successfully";
	}

}
