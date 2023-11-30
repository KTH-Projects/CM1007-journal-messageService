package com.example.journalchat.persistance.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "Message")
@Data // Lombok annotation to generate getters, setters, toString, equals, and hashCode methods
@NoArgsConstructor // Generates a no-args constructor
@AllArgsConstructor
public class MessageDB {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "fromId")
    private String fromId;

    @Column(name = "toId")
    private String toId;

    @Column(name = "message")
    private String message;


}
