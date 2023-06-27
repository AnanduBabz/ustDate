package com.example.ustdate.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.ustdate.RequestDTO.MessengerDTO;
import com.example.ustdate.entity.ActiveChat;
import com.example.ustdate.entity.User;
import com.example.ustdate.repository.ActiveChatRepository;
import com.example.ustdate.repository.UserRepository;

@Service
public class LogicServiceController {

	@Autowired
	UserRepository userRepo;

	@Autowired
	ActiveChatRepository activeChatRepo;

	public MessengerDTO channel(String userName, String chatId, String messageText) {
		MessengerDTO reply = new MessengerDTO();
		if (messageText.equals("NEWCHAT")) {
			reply = randomId(userName, chatId);
		} else {
			reply = sendChatGenerator(userName, chatId, messageText);
		}
		return reply;
	}

	private MessengerDTO randomId(String userName, String chatId) {
		User user = getUserId(userName);
		MessengerDTO reply = new MessengerDTO();
		reply.setFrom(user.getId());
		reply.setFromChatId(user.getPhoneNumber());
		reply.setFromMessage("connected to new chat");
		User connectedUser = getRandomUser(user);
		reply.setTo(connectedUser.getId());
		reply.setToChatId(connectedUser.getPhoneNumber());
		reply.setToMessage("connected to new chat");
		return reply;
	}

	private User getRandomUser(User user) {
		List<User> sortedUsers = userRepo.findByGender(user.getGenderPref());
		List<Long> ids = new ArrayList<>();
		for (User use : sortedUsers) {
			ids.add(use.getId());
		}
		Random rand = new Random();
		Long randomId = ids.get(rand.nextInt(ids.size()));
		return userRepo.findById(randomId).get();
	}

	private MessengerDTO sendChatGenerator(String userName, String chatId, String messageText) {
		MessengerDTO reply = new MessengerDTO();
		User user = getUserId(userName);
		reply.setFrom(user.getId());
		reply.setFromChatId(user.getPhoneNumber());
		reply.setFromMessage(">>");
		User connectedUser = getConnectedUser(user.getId());
		reply.setTo(connectedUser.getId());
		reply.setToChatId(connectedUser.getPhoneNumber());
		reply.setToMessage(messageText);
		return reply;
	}

	private User getConnectedUser(Long id) {
		ActiveChat chat = activeChatRepo.findById(id).get();
		return userRepo.findByPhoneNumber(chat.getConnectedChatId());
	}

	private User getUserId(String userName) {
		return userRepo.findByName(userName).get();
	}
}
