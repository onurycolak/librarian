package org.onur.librarian_backend.api.response;

import org.onur.librarian_backend.domain.Chat;
import org.onur.librarian_backend.domain.Message;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

public class ChatResponse {
    private String title;
    private List<MessageResponse> messages;
    private UUID id;
    private Instant updatedAt;

    public static ChatResponse from(Chat chat) {
        ChatResponse response = new ChatResponse();

        response.title = chat.getTitle();
        response.messages = chat.getMessages().stream()
                .map(MessageResponse::from)
                .toList();
        response.id = chat.getId();
        response.updatedAt = chat.getUpdatedAt();
        return response;
    }

    public String getTitle() {
        return title;
    }

    public List<MessageResponse> getMessages() {
        return messages;
    }

    public UUID getId() {
        return id;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }
}
