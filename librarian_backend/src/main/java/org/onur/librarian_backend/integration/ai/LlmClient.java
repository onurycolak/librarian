package org.onur.librarian_backend.integration.ai;

import org.onur.librarian_backend.domain.Chat;

public interface LlmClient {
    public String ask(Chat conversation);
    public  String evaluate();
}
