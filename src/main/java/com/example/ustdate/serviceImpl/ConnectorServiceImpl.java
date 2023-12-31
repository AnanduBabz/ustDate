package com.example.ustdate.serviceImpl;


import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.ustdate.RequestDTO.MessengerDTO;
import com.example.ustdate.RequestDTO.UserRequestDTO;
import com.example.ustdate.botConfiguration.MyTelegramBot;
import com.example.ustdate.entity.Selection;
import com.example.ustdate.entity.User;
import com.example.ustdate.entity.UserRegistering;
import com.example.ustdate.repository.SelectionRepository;
import com.example.ustdate.repository.UserRegistrationRepository;
import com.example.ustdate.repository.UserRepository;
import com.example.ustdate.responseDTO.InterMediateResponseDTO;
import com.example.ustdate.service.ConnectorService;
import com.example.ustdate.service.MenuService;
import com.example.ustdate.service.UserService;
import com.example.ustdate.controller.ChatController;
import com.example.ustdate.controller.LogicServiceController;

import org.springframework.stereotype.Service;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

@Service
public class ConnectorServiceImpl implements ConnectorService {
	
	@Autowired
	LogicServiceController logic;
	
	@Autowired
	UserService service;
	
	@Autowired
	UserRegistrationRepository registerRepo;
	
	@Autowired
	UserRepository userRepo;

	@Autowired
	ChatController chatController;
	
	@Autowired
	SelectionRepository selectionRepository;
	
	@Autowired
	MenuService menuService;

	@Override
	public SendMessage intermediate(String userName,String chatId,String messageText) {
		Boolean existUser = checkUser(userName);
		SendMessage message = new SendMessage();
		ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
		keyboardMarkup.setResizeKeyboard(true);
		if(existUser) {
			KeyboardRow menu = new KeyboardRow();
			menu.add(new KeyboardButton("NEWCHAT"));
			menu.add(new KeyboardButton("INVITE_YOUR_CRUSH"));
			keyboardMarkup.setKeyboard(List.of(menu));
			message.setReplyMarkup(keyboardMarkup); 
			MessengerDTO mess = logic.channel(userName, chatId, messageText);
			MyTelegramBot bot = new MyTelegramBot();
			SendMessage messageToSend = new SendMessage();
			messageToSend.setChatId(mess.getToChatId());
			messageToSend.setText(mess.getToMessage());
			message.setChatId(mess.getFromChatId());
			message.setText(mess.getFromMessage());
			if(mess.getTo()!=null) {
				bot.immediateMessage(messageToSend);
			}
			return message;
		}else {
			if(!registerRepo.existsById(userName)) {
				UserRegistering register = new UserRegistering();
				register.setName(userName);
				register.setChatId(chatId);
				register.setStep("first");
				registerRepo.save(register);
			}else {
				UserRegistering register = registerRepo.findById(userName).get();
				if(register.getGender()==null&&register.getStep().equals("first")) {
					register.setStep("second");
					registerRepo.save(register);
					KeyboardRow gender = new KeyboardRow();
					gender.add(new KeyboardButton("M"));
					gender.add(new KeyboardButton("F"));
					gender.add(new KeyboardButton("O"));
					keyboardMarkup.setKeyboard(List.of(gender));
					message.setReplyMarkup(keyboardMarkup); 		
					message.setText("Tell me your Gender : M/F?");
					return message;
				}else if(register.getGenderPref()==null&&register.getStep().equals("second")) {
					register.setGender(messageText);
					register.setStep("third");
					registerRepo.save(register);
					KeyboardRow gender = new KeyboardRow();
					gender.add(new KeyboardButton("M"));
					gender.add(new KeyboardButton("F"));
					gender.add(new KeyboardButton("O"));
					keyboardMarkup.setKeyboard(List.of(gender));
					message.setReplyMarkup(keyboardMarkup); 	
					keyboardMarkup.setOneTimeKeyboard(true);
					message.setText("Tell me your Gender preference : M/F?");
					return message;
				}else if(register.getAge()==null&&register.getStep().equals("third")) {
					register.setGenderPref(messageText);
					register.setStep("four");
					registerRepo.save(register);
					keyboardMarkup.setKeyboard(Collections.emptyList());
					message.setReplyMarkup(keyboardMarkup); 
					message.setReplyMarkup(keyboardMarkup); 		
					message.setText("Tell me your age?");
					return message;
				}else if(register.getAgePref()==null&&register.getStep().equals("four")) {
					register.setAge(messageText);
					register.setStep("five");
					registerRepo.save(register);
					KeyboardRow age = new KeyboardRow();
					age.add(new KeyboardButton("18-20"));
					age.add(new KeyboardButton("20-22"));
					age.add(new KeyboardButton("22-25"));
					age.add(new KeyboardButton("25-30"));
					age.add(new KeyboardButton("30<"));
					keyboardMarkup.setKeyboard(List.of(age));
					keyboardMarkup.setOneTimeKeyboard(true);
					message.setReplyMarkup(keyboardMarkup); 		
					message.setText("Tell me your age prefernce range?");
					return message;
				}else {
					register.setAgePref(messageText);
					UserRequestDTO req= new UserRequestDTO(register);
					service.save(req);
					registerRepo.delete(register);
					keyboardMarkup.setKeyboard(Collections.emptyList());
					message.setReplyMarkup(keyboardMarkup); 
					message.setText("you are all set to go dear!!!");
					return message;
				}
			}
			message.setText("Hi Welcome to Kazhakoottam dating , let me know your details and intrest");
			return message;
		}
	}

	private Boolean checkUser(String userName) {
		if(service.getUserByUserName(userName)!=null) {
			return true;
		}
		return false;
	}
	
	private void sendMessage(String chatId,String message) {
		MyTelegramBot bot = new MyTelegramBot();
		SendMessage mess = new SendMessage();
		mess.setChatId(chatId);
		mess.setText(message);
		bot.immediateMessage(mess);
	}

}
