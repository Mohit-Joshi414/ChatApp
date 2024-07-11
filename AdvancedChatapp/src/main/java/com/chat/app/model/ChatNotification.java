package com.chat.app.model;


public class ChatNotification {

	private int id;
	private String senderId;
	private String recipientId;
	private String content;
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
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
	public ChatNotification() {
		super();
		// TODO Auto-generated constructor stub
	}
	public ChatNotification(int id, String senderId, String recipientId, String content) {
		super();
		this.id = id;
		this.senderId = senderId;
		this.recipientId = recipientId;
		this.content = content;
	}
	@Override
	public String toString() {
		return "ChatNotification [id=" + id + ", senderId=" + senderId + ", recipientId=" + recipientId + ", content="
				+ content + "]";
	}
	
	
	
}
