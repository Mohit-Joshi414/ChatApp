package com.chat.app.model;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class ChatMessage {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int Id;
	private String chatId;
	private String senderId;
	private String recipientId;
	private String content;
	private Date timestamp;


	public int getId() {
		return Id;
	}
	public void setId(int id) {
		Id = id;
	}
	public String getChatId() {
		return chatId;
	}
	public void setChatId(String chatId) {
		this.chatId = chatId;
	}
	public String getSenderId() {
		return senderId;
	}
	public void setSenderId(String senderId) {
		this.senderId = senderId;
	}
	public String getRecipientId() {
		return recipientId;
	}
	public void setRecipientId(String recipientId) {
		this.recipientId = recipientId;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Date getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}
	public ChatMessage(String senderId, String recipientId, String content,
			Date timestamp) {
		super();
		this.senderId = senderId;
		this.recipientId = recipientId;
		this.content = content;
		this.timestamp = timestamp;
	}
	public ChatMessage() {
		super();
		// TODO Auto-generated constructor stub
	}
	@Override
	public String toString() {
		return "ChatMessage [Id=" + Id + ", chatId=" + chatId + ", senderId=" + senderId + ", recipientId="
				+ recipientId + ", content=" + content + ", timestamp=" + timestamp + "]";
	}
	
	
}
