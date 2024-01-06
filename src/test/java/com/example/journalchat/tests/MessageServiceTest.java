package com.example.journalchat.tests;
import com.example.journalchat.core.entity.MessageDTO;
import com.example.journalchat.core.service.MessageService;
import com.example.journalchat.persistance.entity.MessageDB;
import com.example.journalchat.persistance.repository.MessageRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class MessageServiceTest {

    @InjectMocks
    private MessageService messageService;

    @Mock
    private MessageRepository messageRepository;

    @Test
    public void testSendMessage() {
        String fromId = "user1";
        String toId = "user2";
        String message = "Hello";

        MessageDTO result = messageService.sendMessage(fromId, toId, message);

        verify(messageRepository).save(any(MessageDB.class));
        assertEquals(fromId, result.getFromId());
        assertEquals(toId, result.getToId());
        assertEquals(message, result.getMessage());
    }

    @Test
    public void testGetMessagesFromId() {
        // Arrange
        String fromId = "user1";
        List<MessageDB> mockedMessages = new ArrayList<>();

        // Creating sample MessageDB instances
        mockedMessages.add(new MessageDB(1L, fromId, "user2", "Hello there!"));
        mockedMessages.add(new MessageDB(2L, fromId, "user3", "General Kenobi!"));
        mockedMessages.add(new MessageDB(3L, fromId, "user4", "You are a bold one."));

        // Mocking the behavior of messageRepository
        when(messageRepository.findByFromId(fromId)).thenReturn(mockedMessages);

        // Act
        List<MessageDTO> results = messageService.getMessagesFromId(fromId);

        // Assert
        assertEquals(mockedMessages.size(), results.size());
        for (int i = 0; i < mockedMessages.size(); i++) {
            assertEquals(mockedMessages.get(i).getFromId(), results.get(i).getFromId());
            assertEquals(mockedMessages.get(i).getToId(), results.get(i).getToId());
            assertEquals(mockedMessages.get(i).getMessage(), results.get(i).getMessage());
        }
    }

    @Test
    public void testGetMessagesFromIdAndToId() {
        // Arrange
        String fromId = "user1";
        String toId = "user2";
        List<MessageDB> mockedMessages = new ArrayList<>();

        // Creating sample MessageDB instances
        mockedMessages.add(new MessageDB(1L, fromId, toId, "Hello, how are you?"));
        mockedMessages.add(new MessageDB(2L, fromId, toId, "Are you available for a meeting?"));
        mockedMessages.add(new MessageDB(3L, fromId, toId, "Don't forget the documents."));

        // Mocking the behavior of messageRepository
        when(messageRepository.findByFromIdAndToId(fromId, toId)).thenReturn(mockedMessages);

        // Act
        List<MessageDTO> results = messageService.getMessagesFromIdAndToId(fromId, toId);

        // Assert
        assertEquals(mockedMessages.size(), results.size());
        for (int i = 0; i < mockedMessages.size(); i++) {
            assertEquals(mockedMessages.get(i).getFromId(), results.get(i).getFromId());
            assertEquals(mockedMessages.get(i).getToId(), results.get(i).getToId());
            assertEquals(mockedMessages.get(i).getMessage(), results.get(i).getMessage());
        }
    }

}
