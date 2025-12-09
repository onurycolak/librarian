package org.onur.librarian_backend.api.response;

import org.onur.librarian_backend.domain.Chat;
import org.onur.librarian_backend.domain.Message;
import org.onur.librarian_backend.domain.Role;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

public class MessageResponse {
    private String response;
    private UUID chatID;
    private UUID messageID;
    private Role role;
    private Instant updatedAt;

    public static MessageResponse from(Message message) {
        MessageResponse response = new MessageResponse();

        response.response = message.getContent();
        response.chatID = message.getChat().getId();
        response.messageID = message.getId();
        response.role = message.getRole();
        response.updatedAt = message.getUpdatedAt();

        return response;
    }

    public UUID getChatID() {
        return chatID;
    }

    public UUID getMessageID() {
        return messageID;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public Role getRole() {
        return role;
    }

    public String getResponse() {
        return response;
    }
}
