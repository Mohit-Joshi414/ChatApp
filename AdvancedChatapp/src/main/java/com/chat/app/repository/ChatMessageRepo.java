package com.chat.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.chat.app.model.ChatMessage;

@Repository
public interface ChatMessageRepo extends JpaRepository<ChatMessage, String>{

	
	List<ChatMessage> findByChatId(String chatId);
}
