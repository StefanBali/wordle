package de.lise.letscode.wordle.usecase.guess.port;

import java.util.Optional;

public interface WordByIdPort {
    Optional<String> findWordById(String id);
}
