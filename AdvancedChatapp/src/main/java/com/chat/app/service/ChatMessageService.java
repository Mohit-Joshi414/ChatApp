package com.chat.app.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chat.app.model.ChatMessage;
import com.chat.app.repository.ChatMessageRepo;

@Service
public class ChatMessageService {

	
	@Autowired
	private ChatMessageRepo repo;
	
	@Autowired
	private ChatRomService chatRomService;
	
	public ChatMessage saveMessage(ChatMessage msg) {
		var chatId = chatRomService.getChatRoomId(msg.getSenderId(), msg.getRecipientId(), true).orElseThrow();
		msg.setChatId(chatId);
		repo.save(msg);
		return msg;
	}
	
	public List<ChatMessage> findChatMessages(String senderId,String recipientId){
		var chatId = chatRomService.getChatRoomId(senderId,recipientId,false);
		return chatId.map(repo::findByChatId).orElse(new ArrayList<>());
	}
}


