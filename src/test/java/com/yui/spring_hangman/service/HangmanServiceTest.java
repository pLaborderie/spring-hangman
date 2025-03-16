package com.yui.spring_hangman.service;

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

    @Test
    public void wordGuessProgress_shouldReplaceUnknownWithUnderscore() {
        // arrange
        hangmanService.word = "test";
        hangmanService.guesses.add("t");

        // act
        String progress = hangmanService.wordGuessProgress();

        // assert
        assertThat(progress).isEqualTo("t _ _ t ");
    }

    @Test
    public void wrongGuesses_shouldOnlyReturnCharactersNotInWord() {
        hangmanService.word = "test";
        hangmanService.guesses.add("t");
        hangmanService.guesses.add("p");

        List<String> guesses = hangmanService.wrongGuesses();

        assertThat(guesses).hasSize(1);
        assertThat(guesses.getFirst()).isEqualTo("p");
    }
}
