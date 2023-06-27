package com.example.ustdate.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.ustdate.entity.ActiveChat;

@Repository
public interface ActiveChatRepository extends JpaRepository<ActiveChat, Long> {

	ActiveChat findByConnectedChatId(String phoneNumber);

}
