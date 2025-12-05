package org.onur.librarian_backend.api.resource;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.core.Response;
import java.util.List;
import java.util.ArrayList;
import org.onur.librarian_backend.domain.Chat;
import org.onur.librarian_backend.domain.Message;
import org.onur.librarian_backend.api.response.ChatResponse;
import org.onur.librarian_backend.api.response.ChatSummaryResponse;

@Path("/api/librarian/chat")
public class ChatResource {
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response chat() {
        Chat chat = new Chat(new Message("Hello", "user"));
        chat.addMessage(new Message("Hello", "assistant"));


        return Response.ok(ChatResponse.from(chat)).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/recents")
    public Response recents() {
        List<Chat> chats = new ArrayList<>();
        Chat chat = new Chat(new Message("Hello 2", "user"));
        chat.addMessage(new Message("Hello2", "assistant"));

        chats.add(chat);

        return Response.ok(ChatSummaryResponse.from(chats)).build();
    }
}
