package com.chat.app.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class ChatRoom {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String chatId;
	private String senderId;
	private String recipientId;


	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
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
	public ChatRoom(int id, String chatId, String senderId, String recipientId) {
		super();
		this.id = id;
		this.chatId = chatId;
		this.senderId = senderId;
		this.recipientId = recipientId;
	}
	
	
	public ChatRoom(String chatId, String senderId, String recipientId) {
		super();

		this.chatId = chatId;
		this.senderId = senderId;
		this.recipientId = recipientId;
	}
	public ChatRoom() {
		super();
		// TODO Auto-generated constructor stub
	}
	@Override
	public String toString() {
		return "ChatRoom [id=" + id + ", chatId=" + chatId + ", senderId=" + senderId + ", recipientId=" + recipientId
				+ "]";
	}
	
	
}
