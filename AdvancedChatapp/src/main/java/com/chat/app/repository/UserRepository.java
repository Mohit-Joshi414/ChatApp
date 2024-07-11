package com.chat.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.chat.app.model.Status;
import com.chat.app.model.Users;

@Repository
public interface UserRepository extends JpaRepository<Users, String>{

	List<Users> findAllByStatus(Status status);

}
