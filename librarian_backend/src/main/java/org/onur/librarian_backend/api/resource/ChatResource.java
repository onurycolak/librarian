package org.onur.librarian_backend.api.resource;

import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;
import java.util.ArrayList;
import java.util.UUID;

import org.onur.librarian_backend.api.request.ChatRequest;
import org.onur.librarian_backend.api.response.MessageResponse;
import org.onur.librarian_backend.application.ChatService;
import org.onur.librarian_backend.domain.Chat;
import org.onur.librarian_backend.domain.Message;
import org.onur.librarian_backend.api.response.ChatResponse;
import org.onur.librarian_backend.api.response.ChatSummaryResponse;
import org.onur.librarian_backend.domain.Role;

@Path("/api/librarian/chat")
public class ChatResource {
    @Inject
    ChatService chatService;

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response chat(@Valid ChatRequest request) {
        Message answer = chatService.converse(request);


        return Response.status(Response.Status.OK).entity(MessageResponse.from(answer)).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/recents")
    public Response recents() {
        List<Chat> recentChats = chatService.getRecentsByUserId();

        return Response.ok(ChatSummaryResponse.from(recentChats)).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{chatId}")
    public Response recentChat(@PathParam("chatId") UUID chatId) {
        Chat recentChat = chatService.getChat(chatId);

        return Response.ok(ChatResponse.from(recentChat)).build();
    }
}
