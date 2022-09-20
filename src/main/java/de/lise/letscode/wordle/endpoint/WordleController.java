package de.lise.letscode.wordle.endpoint;

import org.jboss.resteasy.reactive.RestResponse;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

@Path("/api/wordle/")
public class WordleController {
    @GET
    @Path("pick")
    public RestResponse<String> pickRandomWordId() {
        throw new UnsupportedOperationException();
    }
}
