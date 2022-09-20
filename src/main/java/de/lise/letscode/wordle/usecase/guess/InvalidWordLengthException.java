package de.lise.letscode.wordle.usecase.guess;

public class InvalidWordLengthException extends RuntimeException {
    public InvalidWordLengthException(String message) {
        super(message);
    }
}
