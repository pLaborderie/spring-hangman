package com.yui.spring_hangman;

import com.yui.spring_hangman.service.HangmanService;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class HangmanController {
    private final GameSessionManager gameSessionManager;
    public enum MessageType {USER_JOINED, USER_LEFT, CHAT_MESSAGE, GUESS_LETTER, GAME_STATE, START_GAME}
    public record ChatMessage(MessageType type, String room, String from, String message) {
    }

    public HangmanController(GameSessionManager gameSessionManager) {
        this.gameSessionManager = gameSessionManager;
    }

    @MessageMapping("/login")
    @SendTo("/topic/hangman")
    public ChatMessage login(ChatMessage message) {
        return message;
    }

    @MessageMapping("/logout")
    @SendTo("/topic/hangman")
    public ChatMessage logout(ChatMessage message) {
        return message;
    }

    @MessageMapping("/guess")
    @SendTo("/topic/hangman")
    public ChatMessage guess(ChatMessage message) {
        if (message.type == MessageType.GUESS_LETTER) {
            return addGuess(message.room, message.message);
        }
        if (message.type == MessageType.START_GAME) {
            return startGame(message.room);
        }
        return message;
    }

    private ChatMessage gameState(String gameId) {
        HangmanService game = gameSessionManager.getGame(gameId);
        String state = game.printGameState();
        return new ChatMessage(MessageType.GAME_STATE, gameId, "SYSTEM", state);
    }

    private ChatMessage addGuess(String gameId, String guess) {
        HangmanService game = gameSessionManager.getGame(gameId);
        if (!game.isGuessed(guess)) {
            game.addGuess(guess);
        }
        return gameState(gameId);
    }

    private ChatMessage startGame(String gameId) {
        HangmanService game = gameSessionManager.getGame(gameId);
        game.startGame();
        return gameState(gameId);
    }
}
