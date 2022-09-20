package de.lise.letscode.wordle.usecase.guess;

import de.lise.letscode.wordle.usecase.guess.Score.Result;
import de.lise.letscode.wordle.usecase.guess.port.WordByIdPort;

import javax.enterprise.context.ApplicationScoped;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@ApplicationScoped
public class GuessWord {
    private final WordByIdPort wordByIdPort;

    public GuessWord(WordByIdPort wordByIdPort) {
        this.wordByIdPort = wordByIdPort;
    }

    public Optional<ScoreList> guessWord(String id, String guessedWord) {
        if (guessedWord == null || guessedWord.length() != 5) {
            throw new InvalidWordLengthException("Guessed word must be exactly 5 characters long.");
        }
        return wordByIdPort.findWordById(id).map(word -> calculateScore(word.toLowerCase(Locale.GERMAN), guessedWord.toLowerCase(Locale.GERMAN)));
    }

    private ScoreList calculateScore(String word, String guessedWord) {
        List<Score> scores = new ArrayList<>();
        Set<Character> wordCharacters = getCharacters(word);
        for (int i = 0; i < word.length(); i++) {
            Result result;
            if (word.charAt(i) == guessedWord.charAt(i)) {
                result = Result.CORRECT_SPOT;
            } else if (wordCharacters.contains(guessedWord.charAt(i))) {
                result = Result.WRONG_SPOT;
            } else {
                result = Result.NOT_CONTAINED;
            }
            scores.add(new Score(i + 1, result));
        }
        return new ScoreList(scores);
    }

    private Set<Character> getCharacters(String word) {
        return word.chars()
                .mapToObj(c -> (char) c)
                .collect(Collectors.toSet());
    }
}
