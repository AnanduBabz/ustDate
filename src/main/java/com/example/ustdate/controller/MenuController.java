package com.example.ustdate.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.ustdate.responseDTO.InterMediateResponseDTO;
import com.example.ustdate.service.MenuService;

@RestController
@RequestMapping("/menu")
public class MenuController {
	
	@Autowired
	MenuService menuService;
	
	// suggestions
	public InterMediateResponseDTO suggestion(InterMediateResponseDTO message) {
		return menuService.suggestion(message);
	}
	
	//chat
	public InterMediateResponseDTO chat(InterMediateResponseDTO message) {
		return menuService.chat(message);
	}
	
	//newchat
	public InterMediateResponseDTO newChat(InterMediateResponseDTO message) {
		return menuService.newChat(message);
	}

}
