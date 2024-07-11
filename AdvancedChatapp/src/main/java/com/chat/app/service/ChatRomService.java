package com.chat.app.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chat.app.model.ChatRoom;
import com.chat.app.repository.ChatRoomRepo;

@Service
public class ChatRomService {
	
	@Autowired
	private ChatRoomRepo repo;

	
	public Optional<String> getChatRoomId(String senderId,String recipientId, boolean createNewRoomIfNotExists){
		return repo.findBySenderIdAndRecipientId(senderId,recipientId)
				.map(ChatRoom::getChatId)
				.or(()->{
			if(createNewRoomIfNotExists) {
				var chatId = createChatId(senderId,recipientId);
				return Optional.of(chatId);
			}
			return Optional.empty();
		});
	}


	private String createChatId(String senderId, String recipientId) {
		var chatId = String.format("%s_%s",senderId,recipientId);
		ChatRoom senderRecipient = new ChatRoom(chatId,senderId,recipientId);
		ChatRoom recipientSender = new ChatRoom(chatId,recipientId,senderId);
		
		repo.save(senderRecipient);
		repo.save(recipientSender);
		return chatId;
	}
}
