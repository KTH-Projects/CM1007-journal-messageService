package com.example.journalchat.core.entity;

import lombok.*;

@Data
    @NoArgsConstructor
    @AllArgsConstructor
public class MessageDTO {

    private String fromId;

    private String toId;

    private String message;
}
