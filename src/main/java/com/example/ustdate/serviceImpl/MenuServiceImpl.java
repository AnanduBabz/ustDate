package com.example.ustdate.serviceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.ustdate.entity.ActiveChat;
import com.example.ustdate.entity.User;
import com.example.ustdate.repository.ActiveChatRepository;
import com.example.ustdate.repository.UserRepository;
import com.example.ustdate.responseDTO.InterMediateResponseDTO;
import com.example.ustdate.service.MenuService;

@Service
public class MenuServiceImpl implements MenuService {

	@Autowired
	UserRepository userRepo;
	
	@Autowired
	ActiveChatRepository activeChatRepo;

	@Override
	public InterMediateResponseDTO suggestion(InterMediateResponseDTO message) {
		message.setChatId("1280533615");
		return message;
	}

	@Override
	public InterMediateResponseDTO newChat(InterMediateResponseDTO message) {
		User user = userRepo.findAllByPhoneNumber(message.getChatId());
		List<User> sortedUsers = userRepo.findByGenderAndInChat(user.getGenderPref(), "FALSE");
		List<Long> ids = new ArrayList<>();
		for (User use : sortedUsers) {
			ids.add(use.getId());
		}
		Random rand = new Random();
		Long randomId = ids.get(rand.nextInt(ids.size()));
		User connectedUser= userRepo.findById(randomId).get();
		ActiveChat chat = new ActiveChat();
		chat.setUserId(randomId);
//		chat.setChatId(user.getPhoneNumber());
		activeChatRepo.save(chat);
		ActiveChat chat2 = new ActiveChat();
		chat2.setUserId(user.getId());
//		chat2.setChatId(connectedUser.getPhoneNumber());
		activeChatRepo.save(chat2);
		message.setMessage("Connected");
		//send notification to connected user also
		return null;
	}

	@Override
	public InterMediateResponseDTO chat(InterMediateResponseDTO message) {
		User user = userRepo.findAllByPhoneNumber(message.getChatId());
		ActiveChat chat = activeChatRepo.findById(user.getId()).get();
//		message.setChatId(chat.getChatId());
		return message;
	}

}
