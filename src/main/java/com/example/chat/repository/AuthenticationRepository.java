package com.example.chat.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.chat.entity.Authentication;
import com.example.chat.entity.User;

@Repository
public interface AuthenticationRepository extends JpaRepository<Authentication, Integer>{
	List<Authentication> getListAuthenticationByUser(User user);
}
