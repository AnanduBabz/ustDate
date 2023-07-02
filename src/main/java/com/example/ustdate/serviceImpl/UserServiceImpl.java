package com.example.ustdate.serviceImpl;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import com.example.ustdate.RequestDTO.UserRequestDTO;
import com.example.ustdate.entity.ActiveChat;
import com.example.ustdate.entity.User;
import com.example.ustdate.repository.ActiveChatRepository;
import com.example.ustdate.repository.UserRepository;
import com.example.ustdate.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	UserRepository userRepo;

	@Autowired
	ActiveChatRepository activeChatRepository;

	public User save(UserRequestDTO user) {
		User newUser = new User();
		BeanUtils.copyProperties(user, newUser);
		newUser.setInChat(false);
		newUser = userRepo.save(newUser);
		ActiveChat chat = new ActiveChat();
		chat = chat.newChat(newUser.getId());
		activeChatRepository.save(chat);
		return newUser;
	}
	
	public List<User> getAll() {
		return userRepo.findAll();
	}
	
	public String save(List<User> user) {
		user = userRepo.saveAll(user);
		for (User use : user) {
			ActiveChat chat = new ActiveChat();
			chat = chat.newChat(use.getId());
			activeChatRepository.save(chat);
		}
		return "saved successfully";
	}

	@Override
	public User getUserByUserName(String userName) {
		User user= userRepo.findByName(userName).orElse(null);
		if(user!=null) {
			return user;
		}
		return null;
	}

	
	
}
