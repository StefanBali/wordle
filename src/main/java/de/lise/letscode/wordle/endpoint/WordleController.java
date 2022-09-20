package de.lise.letscode.wordle.endpoint;

import de.lise.letscode.wordle.endpoint.dto.ScoreDto;
import de.lise.letscode.wordle.usecase.guess.GuessWord;
import de.lise.letscode.wordle.usecase.guess.InvalidWordLengthException;
import de.lise.letscode.wordle.usecase.pick.RandomWord;
import org.jboss.resteasy.reactive.RestQuery;
import org.jboss.resteasy.reactive.RestResponse;
import org.jboss.resteasy.reactive.RestResponse.ResponseBuilder;
import org.jboss.resteasy.reactive.server.ServerExceptionMapper;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import java.util.List;

import static org.jboss.resteasy.reactive.RestResponse.Status.BAD_REQUEST;
import static org.jboss.resteasy.reactive.RestResponse.notFound;
import static org.jboss.resteasy.reactive.RestResponse.status;

@Path("/api/wordle/")
public class WordleController {
    private final RandomWord randomWord;
    private final GuessWord guessWord;

    public WordleController(RandomWord randomWord, GuessWord guessWord) {
        this.randomWord = randomWord;
        this.guessWord = guessWord;
    }

    @ServerExceptionMapper
    public RestResponse<String> mapException(InvalidWordLengthException ex) {
        return status(BAD_REQUEST, ex.getMessage());
    }

    @GET
    @Path("pick")
    public RestResponse<String> pickRandomWordId() {
        return randomWord.pickId()
                .map(wordId -> ResponseBuilder.ok(wordId).build())
                .orElse(notFound());
    }

    @GET
    @Path("{id}/guess")
    public RestResponse<List<ScoreDto>> guessWord(String id, @RestQuery String word) {
        throw new UnsupportedOperationException();
    }
}
