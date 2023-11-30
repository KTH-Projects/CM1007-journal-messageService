package com.example.journalchat.persistance.repository;

import com.example.journalchat.persistance.entity.MessageDB;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MessageRepository extends JpaRepository<MessageDB, Long> {
    List<MessageDB> findByFromId(String Id);

}
