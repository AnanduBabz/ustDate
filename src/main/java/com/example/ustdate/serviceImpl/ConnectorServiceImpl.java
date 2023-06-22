package com.example.ustdate.serviceImpl;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.ustdate.RequestDTO.UserRequestDTO;
import com.example.ustdate.entity.UserRegistering;
import com.example.ustdate.repository.UserRegistrationRepository;
import com.example.ustdate.service.ConnectorService;
import com.example.ustdate.service.UserService;
import org.springframework.stereotype.Service;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

@Service
public class ConnectorServiceImpl implements ConnectorService {
	
	@Autowired
	UserService service;
	
	@Autowired
	UserRegistrationRepository registerRepo;

	@Override
	public SendMessage intermediate(String userName,String chatId,String messageText) {
		Boolean existUser = checkUser(userName);
		SendMessage message = new SendMessage();
		ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
		keyboardMarkup.setResizeKeyboard(true);
		KeyboardRow gender = new KeyboardRow();
		gender.add(new KeyboardButton("M"));
		gender.add(new KeyboardButton("F"));
		gender.add(new KeyboardButton("O"));
		if(existUser) {
			//on registration?
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
					keyboardMarkup.setKeyboard(List.of(gender));
					message.setReplyMarkup(keyboardMarkup); 		
					message.setText("Tell me your Gender : M/F?");
					return message;
				}else if(register.getGenderPref()==null&&register.getStep().equals("second")) {
					register.setGender(messageText);
					register.setStep("third");
					registerRepo.save(register);
					keyboardMarkup.setKeyboard(List.of(gender));
					message.setReplyMarkup(keyboardMarkup); 		
					message.setText("Tell me your Gender preference : M/F?");
					return message;
				}else {
					register.setGenderPref(messageText);
					UserRequestDTO req= new UserRequestDTO(register);
					service.save(req);
					registerRepo.delete(register);
					message.setText("you are all set to go dear!!!");
					return message;
				}
			}
			message.setText("Hi Welcome to Kazhakoottam dating , let me know your details and intrest");
			return message;
		}
		return null;
	}

	private Boolean checkUser(String userName) {
		if(service.getUserByUserName(userName)!=null) {
			return true;
		}
		return false;
	}

}
