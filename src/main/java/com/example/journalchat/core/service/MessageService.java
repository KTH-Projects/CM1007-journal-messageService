package com.example.journalchat.core.service;

import com.example.journalchat.core.entity.Message;
import com.example.journalchat.persistance.entity.MessageDB;
import com.example.journalchat.persistance.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MessageService {

    @Autowired
    private MessageRepository messageRepository;

    public Message sendMessage(String fromId, String toId, String message) {

        MessageDB messageToSave = new MessageDB();
        messageToSave.setFromId(fromId);
        messageToSave.setToId(toId);
        messageToSave.setMessage(message);

        messageRepository.save(messageToSave);

        return new Message(fromId,toId,message);
    }

    public List<Message> getMessagesFromId(String fromId) {
        ArrayList<Message> messageArrayList = new ArrayList<>();
        for(MessageDB m : messageRepository.findByFromId(fromId)){
            messageArrayList.add(new Message(m.getFromId(),m.getToId(),m.getMessage()));
        }
        return messageArrayList;
    }

    public List<Message> getMessagesFromIdAndToId(String fromId, String toId) {
        ArrayList<Message> messageArrayList = new ArrayList<>();
        for(MessageDB m : messageRepository.findByFromIdAndToId(fromId,toId)){
            messageArrayList.add(new Message(m.getFromId(),m.getToId(),m.getMessage()));
        }
        return messageArrayList;
    }
}
