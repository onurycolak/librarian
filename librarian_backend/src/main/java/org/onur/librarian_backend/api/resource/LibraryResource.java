package org.onur.librarian_backend.api;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/library")
public class LibraryResource {

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String library() {
        return "You just got a library";
    }
}
