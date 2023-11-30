package com.example.journalchat.view;

import com.example.journalchat.core.entity.Message;
import com.example.journalchat.core.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/message")
public class MessageController {

    @Autowired
    private MessageService messageService;

    @PostMapping("/send")
    public ResponseEntity<Message> sendMessage(@RequestBody Message message){
        Message sentMessage = messageService.sendMessage(message.getFromId(), message.getToId(), message.getMessage());
        return new ResponseEntity<>(sentMessage, HttpStatus.CREATED);
    }

    @GetMapping("/conversation")
    public ResponseEntity<List<Message>> getConversation(@RequestParam String fromId) {
        List<Message> conversation = messageService.getConversation(fromId);
        return new ResponseEntity<>(conversation, HttpStatus.OK);
    }


}
