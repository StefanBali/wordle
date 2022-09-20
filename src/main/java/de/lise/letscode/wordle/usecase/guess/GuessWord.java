package de.lise.letscode.wordle.usecase.guess;

import de.lise.letscode.wordle.usecase.guess.port.WordByIdPort;

import javax.enterprise.context.ApplicationScoped;
import java.util.Optional;

@ApplicationScoped
public class GuessWord {
    private final WordByIdPort wordByIdPort;

    public GuessWord(WordByIdPort wordByIdPort) {
        this.wordByIdPort = wordByIdPort;
    }

    public Optional<ScoreList> guessWord(String id, String guessedWord) {
        throw new UnsupportedOperationException();
    }
}
