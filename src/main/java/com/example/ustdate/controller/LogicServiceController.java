package com.example.ustdate.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import com.example.ustdate.RequestDTO.MessengerDTO;
import com.example.ustdate.botConfiguration.MyTelegramBot;
import com.example.ustdate.entity.ActiveChat;
import com.example.ustdate.entity.PreviousSelection;
import com.example.ustdate.entity.User;
import com.example.ustdate.repository.ActiveChatRepository;
import com.example.ustdate.repository.PreviousSelectionRepository;
import com.example.ustdate.repository.UserRepository;

@Service
public class LogicServiceController {

	@Autowired
	UserRepository userRepo;

	@Autowired
	ActiveChatRepository activeChatRepo;
	
	@Autowired
	PreviousSelectionRepository previousSelectionRepo;

	public MessengerDTO channel(String userName, String chatId, String messageText) {
		MessengerDTO reply = new MessengerDTO();
		if (messageText.equals("NEWCHAT")) {
			reply = randomId(userName, chatId);
		}else {
			reply = sendChatGenerator(userName, chatId, messageText);
		}
		return reply;
	}

	private MessengerDTO randomId(String userName, String chatId) {
		User user = getUserId(userName);
		ActiveChat connectedChat = activeChatRepo.findByConnectedChatId(chatId);
		removeExistingChat(user.getId());
		MessengerDTO reply = new MessengerDTO();
		reply.setFrom(user.getId());
		reply.setFromChatId(user.getPhoneNumber());
		reply.setFromMessage("connected to new chat");
		User connectedUser = getRandomUser(user);
		changeActiveChatStatus(connectedChat.getUserId());
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
		sendMessage(chat.getConnectedChatId(),"you have been removed from current chat");
		chat.setConnectedChatId(null);
		activeChatRepo.save(chat);
	}
	
	private void sendMessage(String chatId,String message) {
		MyTelegramBot bot = new MyTelegramBot();
		SendMessage mess = new SendMessage();
		mess.setChatId(chatId);
		mess.setText(message);
		bot.immediateMessage(mess);
	}

	private User getRandomUser(User user) {
		List<User> sortedUsers = userRepo.findByGenderAndInChat(user.getGenderPref(),false);
		if(sortedUsers.isEmpty()) {
			return null;
		}
		List<Long> ids = new ArrayList<>();
		for (User use : sortedUsers) {
			ids.add(use.getId());
		}
		Random rand = new Random();
		Long randomId = ids.get(rand.nextInt(ids.size()));
		User randomUser = userRepo.findById(randomId).get();
		randomUser.setInChat(true);
		userRepo.save(randomUser);
		return randomUser;
	}

	private MessengerDTO sendChatGenerator(String userName, String chatId, String messageText) {
		MessengerDTO reply = new MessengerDTO();
		User user = getUserId(userName);
		ActiveChat chat = activeChatRepo.findById(user.getId()).get();
		if(chat.getConnectedChatId().isEmpty()||chat.getConnectedChatId()!=null) {
			reply.setFrom(user.getId());
			reply.setFromChatId(user.getPhoneNumber());
			reply.setFromMessage("message delivered...");
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
	
	private void changeActiveChatStatus(Long userId) {
		Optional<User> user= userRepo.findById(userId);
		if(user.isPresent()) {
			user.get().setInChat(user.get().getInChat()?false:true);
			userRepo.save(user.get());
		}
	}
	
}
