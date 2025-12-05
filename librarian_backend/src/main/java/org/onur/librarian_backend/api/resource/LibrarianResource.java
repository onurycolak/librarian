package org.onur.librarian_backend.api;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/librarian")
public class LibrarianResource {

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String librarian() {
        return "You just got a librarian";
    }
}
