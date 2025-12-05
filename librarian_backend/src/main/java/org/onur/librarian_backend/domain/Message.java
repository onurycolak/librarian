package org.onur.librarian_backend.domain;


import jakarta.persistence.*;

import java.time.Instant;
import java.util.UUID;

@Entity
public class Message {
    @Id
    @GeneratedValue
    private UUID id;
    private String content;
    private String role;
    @ManyToOne
    @JoinColumn(name = "chat_id")
    private Chat chat;
    private Instant updatedAt;

    public Message() {}

    public Message(String content, String role) {
        this.content = content;
        this.role = role;
        this.updatedAt = Instant.now();
    }

    public String getContent() { return content; }
    public String getRole() { return role; }
    public Instant getUpdatedAt() { return updatedAt; }

    public Chat getChat() {
        return chat;
    }

    void setChat(Chat chat) {
        this.chat = chat;
    }
}