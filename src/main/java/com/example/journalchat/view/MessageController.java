package com.example.journalchat.view;

import com.example.journalchat.core.entity.MessageDTO;
import com.example.journalchat.core.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/message")
public class MessageController {

    @Autowired
    private MessageService messageService;

    @PostMapping("/send")
    public ResponseEntity<MessageDTO> sendMessage(@RequestBody MessageDTO message){
        MessageDTO sentMessage = messageService.sendMessage(message.getFromId(), message.getToId(), message.getMessage());
        return new ResponseEntity<>(sentMessage, HttpStatus.CREATED);
    }

    @GetMapping("")
    public ResponseEntity<List<MessageDTO>> getMessagesFromId(@RequestParam String fromId) {
        List<MessageDTO> conversation = messageService.getMessagesFromId(fromId);
        return new ResponseEntity<>(conversation, HttpStatus.OK);
    }

    @GetMapping("/chat")
    public ResponseEntity<List<MessageDTO>> getMessagesFromIdAndToId(@RequestParam String fromId, @RequestParam String toId) {
        List<MessageDTO> conversation = messageService.getMessagesFromIdAndToId(fromId,toId);
        return new ResponseEntity<>(conversation, HttpStatus.OK);
    }


}
