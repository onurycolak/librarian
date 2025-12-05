package org.onur.librarian_backend.api.response;

import org.onur.librarian_backend.domain.Chat;
import org.onur.librarian_backend.domain.Message;

import java.time.Instant;
import java.util.List;
import java.util.ArrayList;
import java.util.UUID;

public class ChatSummaryResponse {
    private String title;
    private String summary;
    private UUID id;
    private Instant updatedAt;

    public static List<ChatSummaryResponse> from(List<Chat> chats) {
        List<ChatSummaryResponse> responses = new ArrayList<ChatSummaryResponse>();

        for (Chat chat : chats) {
            ChatSummaryResponse response = new ChatSummaryResponse();

            response.title = chat.getTitle();
            response.summary = generateSummary(chat.getMessages());
            response.id = chat.getId();
            response.updatedAt = chat.getUpdatedAt();
            responses.add(response);
        }

        return responses;
    }

    public String getTitle() {
        return title;
    }

    public String getSummary() {
        return summary;
    }

    private static String generateSummary(List<Message> messages) {
        return "Dummy Summary for the Chat";
    }

    public UUID getId() {
        return id;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }
}
