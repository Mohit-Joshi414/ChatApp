package com.chat.app.model;



import jakarta.persistence.Entity;
import jakarta.persistence.Id;


@Entity
public class Users {
	@Id
	private String username;
	private String nickname;
	private Status status;
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public Status getStatus() {
		return status;
	}
	public void setStatus(Status status) {
		this.status = status;
	}
	public Users() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Users(String username, String nickname, Status status) {
		super();
		this.username = username;
		this.nickname = nickname;
		this.status = status;
	}
	@Override
	public String toString() {
		return "User [username=" + username + ", nickname=" + nickname + ", status=" + status + "]";
	}
	
	
}
