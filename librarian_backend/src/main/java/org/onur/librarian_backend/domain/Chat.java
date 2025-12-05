package org.onur.librarian_backend.domain;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.time.Instant;

@Entity
public class Chat {
    @Id
    @GeneratedValue
    private UUID id;
    private String title;
    @OneToMany(mappedBy = "chat", cascade = CascadeType.ALL, orphanRemoval = true)
    private final List<Message> messages = new ArrayList<>();
    private Instant updatedAt;

    public Chat() {}

    public Chat(Message message) {
        addMessage(message);
        this.title = generateTitle(messages);
        this.updatedAt = Instant.now();
    }

    public String getTitle() { return title; }
    public List<Message> getMessages() { return messages; }

    private String generateTitle(List<Message> messages) {
        return "Dummy Title for the Chat";
    }

    public UUID getId() { return id; }
    public Instant getUpdatedAt() { return updatedAt; }

    public void addMessage(Message message) {
        messages.add(message);
        message.setChat(this);

        updatedAt = Instant.now();
    }
}