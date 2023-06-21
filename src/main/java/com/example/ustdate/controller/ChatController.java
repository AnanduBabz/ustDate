package com.example.ustdate.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.ustdate.entity.ActiveChat;
import com.example.ustdate.entity.User;
import com.example.ustdate.repository.ActiveChatRepository;
import com.example.ustdate.repository.UserRepository;

@RequestMapping("chat")
@RestController
public class ChatController {
	
	@Autowired
	UserRepository userRepo;
	
	@Autowired
	ActiveChatRepository activeChatRepo;
	
	@GetMapping("/random/{id}")
	public User chatSelect(@PathVariable Long id) {
		User user = userRepo.findById(id).get();
		user.setInChat("TRUE");
		userRepo.save(user);
		List<User> sortedUsers = userRepo.findByGenderAndInChat(user.getGenderPref(),"FALSE");
		//filter from connected account table also
		List<Long>ids = new ArrayList<>();
		for(User use:sortedUsers) {
			ids.add(use.getId());
		}
		 Random rand = new Random();
		 Long randomId = ids.get(rand.nextInt(ids.size()));
		 User outputUser = userRepo.findById(randomId).get();
		 ActiveChat chat =  activeChatRepo.findById(id).get();
		 chat.setConnectedUserId(randomId);
		 ActiveChat chat2 =  activeChatRepo.findById(randomId).get();
		 chat2.setConnectedUserId(id);
		 chat2.setChatId(chat.getChatId());
		 activeChatRepo.save(chat);
		 activeChatRepo.save(chat2);
		 //save to connected account table
		return outputUser;
	}
	
	@GetMapping()
	public List<ActiveChat>all(){
		return activeChatRepo.findAll();
	}
	
	@PostMapping("/send/{id}")
	public String send(@PathVariable Long id,@RequestBody String message) {
		User user = userRepo.findById(id).get();
		ActiveChat chat =  activeChatRepo.findById(id).get();
		//send message connected user phone number;
		return null;
	}

}
