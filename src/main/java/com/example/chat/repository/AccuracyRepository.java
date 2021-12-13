package com.example.chat.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.chat.entity.Accuracy;

@Repository
public interface AccuracyRepository extends JpaRepository<Accuracy, Integer> {

}
