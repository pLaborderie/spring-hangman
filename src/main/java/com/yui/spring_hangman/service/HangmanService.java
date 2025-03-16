package com.yui.spring_hangman.service;

import com.yui.spring_hangman.util.HangmanASCIIDrawer;
import com.yui.spring_hangman.util.WordSelector;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class HangmanService {
    String word = "";
    List<String> guesses = new ArrayList<>();

    /**
     * Sets a random word for the current game
     */
    void pickWord() {
        word = WordSelector.pickRandomWord();
    }

    String wordGuessProgress() {
        StringBuilder guessProgress = new StringBuilder();
        for (Character c : word.toCharArray()) {
            if (guesses.contains(c.toString())) {
                guessProgress.append(c);
            } else {
                guessProgress.append("_");
            }
            guessProgress.append(" ");
        }
        return guessProgress.toString();
    }

    List<String> wrongGuesses() {
        return guesses.stream().filter(c -> !word.contains(c)).collect(Collectors.toList());
    }

    int livesLeft() {
        return 6 - wrongGuesses().size();
    }

    String hangmanArt() {
        return HangmanASCIIDrawer.drawHangman(livesLeft());
    }

    boolean isGameWon() {
        for (Character c : word.toCharArray()) {
            if (!guesses.contains(c.toString())) {
                return false;
            }
        }
        return true;
    }

    boolean isGameLost() {
        return livesLeft() <= 0;
    }

    public boolean isGuessed(String guess) {
        return guesses.contains(guess);
    }

    public void addGuess(String guess) {
        guesses.add(guess);
    }

    public void startGame() {
        System.out.println("Starting game...");
        pickWord();
        guesses = new ArrayList<>();
        System.out.println("The word is " + word);
    }

    public String printGameState() {
        if (isGameWon()) {
            return "Game won! The word was: " + word;
        }
        if (isGameLost()) {
            return "Game lost! The word was: " + word;
        }

        return hangmanArt() +
                "\n" +
                "You have " + livesLeft() + " lives left." +
                "\n" +
                wordGuessProgress() +
                "\n" +
                wrongGuesses();
    }
}

