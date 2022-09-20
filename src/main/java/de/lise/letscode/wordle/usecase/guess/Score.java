package de.lise.letscode.wordle.usecase.guess;

public record Score(
        int position,
        Result result) {
    public enum Result {
        NOT_CONTAINED,
        WRONG_SPOT,
        CORRECT_SPOT;
    }
}
