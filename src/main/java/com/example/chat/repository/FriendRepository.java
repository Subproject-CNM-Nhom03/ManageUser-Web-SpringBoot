package com.example.chat.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.chat.entity.Friend;
import com.example.chat.entity.User;

import javax.transaction.Transactional;

@Repository
public interface FriendRepository extends JpaRepository<Friend, Integer>{
	public List<Friend> getFriendBySender(User sender);
	public List<Friend> getFriendByRecevice(User recevice);

	@Modifying
	@Transactional
	@Query(value = "update friend set block = :block, blockId = :blockId where friendId = :friendId", nativeQuery = true)
	void updateFriendBlock(@Param("friendId") int friendId, @Param("block") Boolean block, @Param("blockId") String blockId);

	@Modifying
	@Transactional
	@Query(value = "update friend set status =:status where friendId =:friendId", nativeQuery = true)
	void updateFriendStatus(@Param("friendId") int friendId, @Param("status") Boolean status);

}
