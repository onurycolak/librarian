package org.onur.librarian_backend.api.request;

import jakarta.validation.constraints.NotNull;
import java.util.UUID;

public class ChatRequest {
    private UUID chatId;
    @NotNull(message = "a message should be provided")
    private String message;

    public ChatRequest() {}

    public ChatRequest(UUID customerId) {
        this.chatId = customerId;
    }

    public UUID getChatId() {
        return chatId;
    }

    public String getMessage() {
        return message;
    }
}
