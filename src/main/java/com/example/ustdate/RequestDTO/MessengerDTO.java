package com.example.ustdate.RequestDTO;

public class MessengerDTO {
	
	private Long from;
	
	private Long to;
	
	private String fromMessage;
	
	private String toMessage;
	
	private String fromChatId;
	
	private String toChatId;

	public Long getFrom() {
		return from;
	}

	public void setFrom(Long from) {
		this.from = from;
	}

	public Long getTo() {
		return to;
	}

	public void setTo(Long to) {
		this.to = to;
	}

	public String getFromMessage() {
		return fromMessage;
	}

	public void setFromMessage(String fromMessage) {
		this.fromMessage = fromMessage;
	}

	public String getToMessage() {
		return toMessage;
	}

	public void setToMessage(String toMessage) {
		this.toMessage = toMessage;
	}

	public String getFromChatId() {
		return fromChatId;
	}

	public void setFromChatId(String fromChatId) {
		this.fromChatId = fromChatId;
	}

	public String getToChatId() {
		return toChatId;
	}

	public void setToChatId(String toChatId) {
		this.toChatId = toChatId;
	}

	
}
