package com.chat.app.service;

import java.util.List;

import javax.sound.midi.VoiceStatus;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chat.app.model.Status;
import com.chat.app.model.Users;
import com.chat.app.repository.UserRepository;

@Service
public class UserService {
	
	@Autowired
	private UserRepository repo;
	
	public void saveUser(Users user) {
		user.setStatus(Status.ONLINE);
		repo.save(user);
	}
	
	public void disconnect(Users user) {
		Users u = repo.findById(user.getNickname()).orElse(null);
		if(u!=null) {
			u.setStatus(Status.OFFLINE);
			repo.save(u);
		}
	}
	
	public List<Users> findAllUsers(){
		return repo.findAll();
	}
	
	public List<Users> findConnectedUser(){
		return repo.findAllByStatus(Status.ONLINE);
	}
}
