package org.onur.librarian_backend.integration.ai.openrouter;

import jakarta.enterprise.context.ApplicationScoped;
import org.onur.librarian_backend.domain.Chat;
import org.onur.librarian_backend.integration.ai.LlmClient;

@ApplicationScoped
public class OpenRouterClient implements LlmClient {
    public String ask(Chat conversation) {
        return conversation.getMessages().getLast().getContent() + " indeed";
    }

    public String evaluate() {
        return "";
    }
}
