package de.lise.letscode.wordle.usecase.pick.port;

import java.util.Optional;

public interface RandomWordPort {
    Optional<String> pickId();
}
