package com.yui.spring_hangman.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Unit tests for #{HangmanService}
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest
public class HangmanServiceTest {
    @Autowired
    private HangmanService hangmanService;

    @BeforeEach
    void setUp() {
        hangmanService.word = "test";
        hangmanService.guesses.clear();
    }

    @Test
    public void wordGuessProgress_shouldReplaceUnknownWithUnderscore() {
        // arrange
        hangmanService.guesses.add("t");

        // act
        String progress = hangmanService.wordGuessProgress();

        // assert
        assertThat(progress).isEqualTo("t _ _ t ");
    }

    @Test
    public void wrongGuesses_shouldOnlyReturnCharactersNotInWord() {
        hangmanService.guesses.add("t");
        hangmanService.guesses.add("p");

        List<String> guesses = hangmanService.wrongGuesses();

        assertThat(guesses).hasSize(1);
        assertThat(guesses.getFirst()).isEqualTo("p");
    }

    @Test
    public void livesLeft_shouldReturnCorrectNumberOfLives() {
        hangmanService.guesses.add("t");
        hangmanService.guesses.add("p");

        int livesLeft = hangmanService.livesLeft();

        assertThat(livesLeft).isEqualTo(5);
    }

    @Test
    public void isGameWon_shouldReturnTrueWhenAllLettersGuessed() {
        hangmanService.guesses.add("t");
        hangmanService.guesses.add("e");
        hangmanService.guesses.add("s");

        boolean gameWon = hangmanService.isGameWon();

        assertThat(gameWon).isTrue();
    }

    @Test
    public void isGameLost_shouldReturnTrueWhenNoLivesLeft() {
        hangmanService.guesses.add("p");
        hangmanService.guesses.add("q");
        hangmanService.guesses.add("r");
        hangmanService.guesses.add("u");
        hangmanService.guesses.add("v");
        hangmanService.guesses.add("w");

        boolean gameLost = hangmanService.isGameLost();

        assertThat(gameLost).isTrue();
    }
}
