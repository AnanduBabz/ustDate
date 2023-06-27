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
	private String connectedChatId;

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getConnectedChatId() {
		return connectedChatId;
	}

	public void setConnectedChatId(String connectedChatId) {
		this.connectedChatId = connectedChatId;
	}
	
	public ActiveChat newChat(Long userId){
		ActiveChat chat = new ActiveChat();
		chat.setUserId(userId);
		return chat;
	}
	
	
}
