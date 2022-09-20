package de.lise.letscode.wordle.usecase.pick;

import de.lise.letscode.wordle.usecase.pick.port.RandomWordPort;

import javax.enterprise.context.ApplicationScoped;
import java.util.Optional;

@ApplicationScoped
public class RandomWord {
    private final RandomWordPort randomWordPort;

    public RandomWord(RandomWordPort randomWordPort) {
        this.randomWordPort = randomWordPort;
    }

    public Optional<String> pickId() {
        return randomWordPort.pickId();
    }
}
