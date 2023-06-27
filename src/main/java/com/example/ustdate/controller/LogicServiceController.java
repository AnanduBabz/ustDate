package com.example.ustdate.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import com.example.ustdate.RequestDTO.MessengerDTO;
import com.example.ustdate.botConfiguration.MyTelegramBot;
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
		removeExistingChat(user.getId());
		MessengerDTO reply = new MessengerDTO();
		reply.setFrom(user.getId());
		reply.setFromChatId(user.getPhoneNumber());
		reply.setFromMessage("connected to new chat");
		User connectedUser = getRandomUser(user);
		if(connectedUser==null) {
			reply.setFromMessage("Sorry no match is free as of now!!!");
			return reply;
		}
		reply.setTo(connectedUser.getId());
		reply.setToChatId(connectedUser.getPhoneNumber());
		reply.setToMessage("connected to new chat");
		ActiveChat chat = new ActiveChat();
		chat.setUserId(user.getId());
		chat.setConnectedChatId(connectedUser.getPhoneNumber());
		ActiveChat chat2 = new ActiveChat();
		chat2.setUserId(connectedUser.getId());
		chat2.setConnectedChatId(user.getPhoneNumber());
		activeChatRepo.save(chat);
		activeChatRepo.save(chat2);
		return reply;
	}

	private void removeExistingChat(Long userId) {
		MyTelegramBot bot = new MyTelegramBot();
		ActiveChat chat = activeChatRepo.findById(userId).get();
		SendMessage message = new SendMessage();
		message.setChatId(chat.getConnectedChatId());
		message.setText("you have been removed from current chat");
		bot.immediateMessage(message);
	}

	private User getRandomUser(User user) {
		List<User> sortedUsers = userRepo.findByGender(user.getGenderPref());
		if(sortedUsers.isEmpty()) {
			return null;
		}
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
		ActiveChat chat = activeChatRepo.findById(user.getId()).get();
		if(chat.getConnectedChatId().isEmpty()||chat.getConnectedChatId()!=null) {
			reply.setFrom(user.getId());
			reply.setFromChatId(user.getPhoneNumber());
			reply.setFromMessage(">>");
			User connectedUser = getConnectedUser(user.getId());
			reply.setTo(connectedUser.getId());
			reply.setToChatId(connectedUser.getPhoneNumber());
			reply.setToMessage(messageText);
		}else {
			reply = randomId(userName, chatId);
		}
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
