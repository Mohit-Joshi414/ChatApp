package com.chat.app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.chat.app.model.ChatMessage;
import com.chat.app.model.ChatNotification;
import com.chat.app.service.ChatMessageService;

@Controller
public class ChatMessageController {
	@Autowired
	private SimpMessagingTemplate messagingTemplate;
	@Autowired
	private ChatMessageService messageService;
	

	public ChatMessageController(SimpMessagingTemplate messagingTemplate, ChatMessageService messageService) {
		super();
		this.messagingTemplate = messagingTemplate;
		this.messageService = messageService;
	}
	

	public ChatMessageController(SimpMessagingTemplate messagingTemplate) {
		super();
		this.messagingTemplate = messagingTemplate;
	}

	

	public ChatMessageController() {
		super();
		// TODO Auto-generated constructor stub
	}


	@MessageMapping("/chat")
	public void processMessage(@Payload ChatMessage msg) {
		ChatMessage savedMessage = messageService.saveMessage(msg);
		messagingTemplate.convertAndSendToUser(msg.getRecipientId(), "/queue/messages",new ChatNotification(savedMessage.getId(),savedMessage.getSenderId(),savedMessage.getRecipientId(),savedMessage.getContent()));
		
	}
	
	@GetMapping("/messages/{senderId}/{recipientId}")
	public ResponseEntity<List<ChatMessage>> findChatMessages(@PathVariable("senderId") String senderId,@PathVariable("recipientId") String recipientId) {
		return ResponseEntity.ok(messageService.findChatMessages(senderId, recipientId));
	}
}
