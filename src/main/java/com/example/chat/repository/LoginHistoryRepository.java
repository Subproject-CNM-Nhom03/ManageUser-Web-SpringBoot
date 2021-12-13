package com.example.chat.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.chat.entity.LoginHistory;


@Repository
public interface LoginHistoryRepository extends JpaRepository<LoginHistory, Integer>{

}
