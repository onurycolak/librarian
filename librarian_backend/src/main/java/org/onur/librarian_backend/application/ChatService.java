package org.onur.librarian_backend.application;

import io.quarkus.security.identity.SecurityIdentity;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;
import org.onur.librarian_backend.api.request.ChatRequest;
import org.onur.librarian_backend.api.response.ChatResponse;
import org.onur.librarian_backend.api.response.ChatSummaryResponse;
import org.onur.librarian_backend.api.response.ErrorResponse;
import org.onur.librarian_backend.api.response.MessageResponse;
import org.onur.librarian_backend.domain.Chat;
import org.onur.librarian_backend.domain.Message;
import org.onur.librarian_backend.domain.Role;
import org.onur.librarian_backend.integration.ai.openrouter.OpenRouterClient;
import org.onur.librarian_backend.repository.ChatRepository;

import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class ChatService {
    @Inject
    EntityManager entityManager;
    @Inject
    ChatRepository chatRepository;
    @Inject
    SecurityIdentity identity;
    UUID ownerId = UUID.fromString("00000000-0000-0000-0000-000000000002");
    @Inject
    OpenRouterClient openRouterClient;

    @Transactional
    public Message converse(ChatRequest request) {
        Message message = new Message(request.getMessage(), Role.User);
        UUID chatId = request.getChatId();

        if (chatId == null) {
            Chat conversation = new Chat(message, ownerId);

            Message response = new Message(openRouterClient.ask(conversation), Role.Assistant);

            conversation.addMessage(response);

            entityManager.persist(conversation);

            return response;

        } else {
            Chat conversation = chatRepository.findById(chatId);

            if (conversation.getOwnerId() != ownerId) {
                throw new WebApplicationException(
                        Response.status(Response.Status.FORBIDDEN)
                                .entity(new ErrorResponse("No ownership to target chat for this user"))
                                .type("application/json")
                                .build()
                );
            }

            Message response = new Message(openRouterClient.ask(conversation), Role.Assistant);

            conversation.addMessage(response);

            entityManager.persist(conversation);

            return response;
        }
    }

    public List<Chat> getRecentsByUserId() {
        return chatRepository.findByUserId(ownerId);
    }

    public Chat getChat (UUID chatId) {
        Chat chat = chatRepository.findById(chatId);

        if (chat == null) {
            throw new WebApplicationException(
                    Response.status(Response.Status.NOT_FOUND)
                            .entity(new ErrorResponse("Chat not found"))
                            .type("application/json")
                            .build()
            );
        }

        return chat;
    }
}
