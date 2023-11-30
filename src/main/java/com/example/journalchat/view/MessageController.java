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

    @GetMapping("")
    public ResponseEntity<List<Message>> getMessagesFromId(@RequestParam String fromId) {
        List<Message> conversation = messageService.getMessagesFromId(fromId);
        return new ResponseEntity<>(conversation, HttpStatus.OK);
    }

    @GetMapping("/chat")
    public ResponseEntity<List<Message>> getMessagesFromIdAndToId(@RequestParam String fromId, @RequestParam String toId) {
        List<Message> conversation = messageService.getMessagesFromIdAndToId(fromId,toId);
        return new ResponseEntity<>(conversation, HttpStatus.OK);
    }


}
