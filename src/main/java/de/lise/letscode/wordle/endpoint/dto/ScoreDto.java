package de.lise.letscode.wordle.endpoint.dto;

public record ScoreDto(
        int position,
        //Result must be "NOT_CONTAINED", "WRONG_SPOT" OR "CORRECT_SPOT"
        String result) {
}
