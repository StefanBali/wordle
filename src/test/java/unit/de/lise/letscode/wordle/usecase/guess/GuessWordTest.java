package de.lise.letscode.wordle.usecase.guess;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class GuessWordTest {
    @Test
    void throwExceptionIfWordIsNull() {
        //Arrange
        GuessWord guessWord = new GuessWord(id -> Optional.of("möhre"));

        //Act & Assert
        assertThrows(InvalidWordLengthException.class, () -> guessWord.guessWord("any", null));
    }

    @Test
    void throwExceptionIfWordHasMoreThan5Characters() {
        //Arrange
        GuessWord guessWord = new GuessWord(id -> Optional.of("möhre"));

        //Act & Assert
        assertThrows(InvalidWordLengthException.class, () -> guessWord.guessWord("any", "karotte"));
    }

    @Test
    void scoreIsCalculatedCorrectlyForCorrectGuess() {
        //Arrange
        GuessWord guessWord = new GuessWord(id -> Optional.of("möhre"));

        //Act
        Optional<ScoreList> scoreList = guessWord.guessWord("any", "möhre");

        //Assert
        assertThat(scoreList).hasValue(new ScoreList(List.of(
                new Score(1, Score.Result.CORRECT_SPOT),
                new Score(2, Score.Result.CORRECT_SPOT),
                new Score(3, Score.Result.CORRECT_SPOT),
                new Score(4, Score.Result.CORRECT_SPOT),
                new Score(5, Score.Result.CORRECT_SPOT))));
    }

    @Test
    void scoreIsCalculatedCorrectlyForIncorrectGuess() {
        //Arrange
        GuessWord guessWord = new GuessWord(id -> Optional.of("möhre"));

        //Act
        Optional<ScoreList> scoreList = guessWord.guessWord("any", "autos");

        //Assert
        assertThat(scoreList).hasValue(new ScoreList(List.of(
                new Score(1, Score.Result.NOT_CONTAINED),
                new Score(2, Score.Result.NOT_CONTAINED),
                new Score(3, Score.Result.NOT_CONTAINED),
                new Score(4, Score.Result.NOT_CONTAINED),
                new Score(5, Score.Result.NOT_CONTAINED))));
    }

    @Test
    void scoreIsCalculatedCorrectlyForPartiallyCorrectGuess() {
        //Arrange
        GuessWord guessWord = new GuessWord(id -> Optional.of("möhre"));

        //Act
        Optional<ScoreList> scoreList = guessWord.guessWord("any", "höhen");

        //Assert
        assertThat(scoreList).hasValue(new ScoreList(List.of(
                new Score(1, Score.Result.WRONG_SPOT),
                new Score(2, Score.Result.CORRECT_SPOT),
                new Score(3, Score.Result.CORRECT_SPOT),
                new Score(4, Score.Result.WRONG_SPOT),
                new Score(5, Score.Result.NOT_CONTAINED))));
    }

    @Test
    void scoreCalculationIsCaseInsensitive() {
        //Arrange
        GuessWord guessWord = new GuessWord(id -> Optional.of("MöHrE"));

        //Act
        Optional<ScoreList> scoreList = guessWord.guessWord("any", "mÖhRe");

        //Assert
        assertThat(scoreList).hasValue(new ScoreList(List.of(
                new Score(1, Score.Result.CORRECT_SPOT),
                new Score(2, Score.Result.CORRECT_SPOT),
                new Score(3, Score.Result.CORRECT_SPOT),
                new Score(4, Score.Result.CORRECT_SPOT),
                new Score(5, Score.Result.CORRECT_SPOT))));
    }
}
