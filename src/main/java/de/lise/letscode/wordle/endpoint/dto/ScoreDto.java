package de.lise.letscode.wordle.endpoint.dto;

import de.lise.letscode.wordle.usecase.guess.Score;

import java.util.List;

public record ScoreDto(
        int position,
        String result) {
    public static List<ScoreDto> fromScores(List<Score> scores) {
        return scores.stream()
                .map(score -> new ScoreDto(score.position(), score.result().name()))
                .toList();
    }
}
