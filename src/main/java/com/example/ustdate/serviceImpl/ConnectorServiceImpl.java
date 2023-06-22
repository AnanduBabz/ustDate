package com.example.ustdate.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.ustdate.RequestDTO.UserRequestDTO;
import com.example.ustdate.entity.UserRegistering;
import com.example.ustdate.repository.UserRegistrationRepository;
import com.example.ustdate.service.ConnectorService;
import com.example.ustdate.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class ConnectorServiceImpl implements ConnectorService {
	
	@Autowired
	UserService service;
	
	@Autowired
	UserRegistrationRepository registerRepo;

	@Override
	public String intermediate(String userName,String chatId,String messageText) {
		Boolean existUser = checkUser(userName);
		if(existUser) {
			//on registration?
		}else {
			if(!registerRepo.existsById(userName)) {
				UserRegistering register = new UserRegistering();
				register.setName(userName);
				register.setChatId(chatId);
				registerRepo.save(register);
			}else {
				UserRegistering register = registerRepo.findById(userName).get();
				if(register.getGender()==null) {
					return "Tell me your Gender : M/F?";
				}else if(register.getGenderPref()==null) {
					register.setGender(messageText);
					registerRepo.save(register);
					return "Tell me your Gender preference : M/F?";
				}else {
					register.setGenderPref(messageText);
					UserRequestDTO req= new UserRequestDTO(register);
					service.save(req);
					registerRepo.delete(register);
					return "you are all set to go dear!!!";
				}
			}
			return "Hi Welcome to Kazhakoottam dating , let me know your details and intrest";
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
