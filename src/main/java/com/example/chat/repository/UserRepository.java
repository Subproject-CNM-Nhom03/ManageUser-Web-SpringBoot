package com.example.chat.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.chat.entity.User;

import javax.transaction.Transactional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
	User getUserByPhoneNumber(String phoneNumber);
	
	@Query(value = "select * from users where userName like :search", nativeQuery = true)
	List<User> findUserBySearch(@Param("search") String search);
	
	@Query(value = "select * from users where email = :x", nativeQuery = true)
	User getUserbyEmail(@Param("x") String email);
	
	@Query(value = "select * from users order by userId DESC", nativeQuery = true)
	List<User> findAllMoiNhat();
	//Lâm thêm ngày 25/10/2021
	@Query(value = "select * from users where email = :search except select * from users where userId = :id", nativeQuery = true)
	User existsEmail(@Param("search") String email,@Param("id") int userid);
	
	@Query(value = "select * from users where email = :search", nativeQuery = true)
	User existsEmailSignup(@Param("search") String email);
	
	@Query(value = "select * from users where phoneNumber = :search", nativeQuery = true)
	User existsPhoneSignup(@Param("search") String phone);

	//12/11
	@Modifying
	@Transactional
	@Query(value = "update users set userName = :userName,email = :email,gender = :gender where userId = :userId", nativeQuery = true)
	void updateUserProfile(@Param("userId") int userId, @Param("userName") String userName, @Param("email") String email, @Param("gender") Boolean gender);

	//hao 12/11
	@Modifying
	@Transactional
	@Query(value = "update users set password=:password where phoneNumber =:phoneNumber", nativeQuery = true)
	void resetPassword(@Param("phoneNumber") String phoneNumber,@Param("password") String password);
}
