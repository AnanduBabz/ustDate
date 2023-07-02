package com.example.ustdate.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import com.example.ustdate.botConfiguration.MyTelegramBot;
import com.example.ustdate.entity.User;
import com.example.ustdate.repository.UserRepository;

@RequestMapping("notification")
@RestController
public class NotificationController {

	@Autowired
	UserRepository userRepo;
	
	@PostMapping
	public void notification(@RequestBody String message) {
		List<User> users = userRepo.findAll();
		for(User user:users) {
			sendMessage(user.getPhoneNumber(),message);
		}
	}
	
	private void sendMessage(String chatId,String message) {
		MyTelegramBot bot = new MyTelegramBot();
		SendMessage mess = new SendMessage();
		mess.setChatId(chatId);
		mess.setText(message);
		bot.immediateMessage(mess);
	}
	
}
