package com.example.ustdate.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Table
@Entity
public class ActiveChat {

	@Id
	@Column
	private Long userId;
	
	@Column
	private Long connectedUserId;
	
	@Column
	private String chatId;

	
	public String getChatId() {
		return chatId;
	}

	public void setChatId(String chatId) {
		this.chatId = chatId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getConnectedUserId() {
		return connectedUserId;
	}

	public void setConnectedUserId(Long connectedUserId) {
		this.connectedUserId = connectedUserId;
	}
	
	public ActiveChat newChat(Long userId){
		ActiveChat chat = new ActiveChat();
		chat.setUserId(userId);
		return chat;
	}
	
	
}
