package com.chat.app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.chat.app.model.Users;
import com.chat.app.service.UserService;

@Controller
public class UserController {

	@Autowired
	private UserService userService;
	
	@MessageMapping("/user.addUser")
	@SendTo("/user/public")
	public Users addUsers(@Payload Users u) {
		userService.saveUser(u);
		return u;
	}
	
	@MessageMapping("/user.disconnect")
	@SendTo("/user/public")
	public Users disconnect(@Payload Users u) {
		userService.disconnect(u);
		return u;
	}
	
	@GetMapping("/users")
	public ResponseEntity<List<Users>> findConnectedUsers() {
//		return new ResponseEntity<>(userService.findConnectedUser(),HttpStatus.OK);
		return new ResponseEntity<>(userService.findAllUsers(),HttpStatus.OK);
	}
}
