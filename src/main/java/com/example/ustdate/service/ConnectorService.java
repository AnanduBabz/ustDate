package com.example.ustdate.service;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

public interface ConnectorService {
	
	public SendMessage intermediate(String userName, String chatId, String messageText);

}
