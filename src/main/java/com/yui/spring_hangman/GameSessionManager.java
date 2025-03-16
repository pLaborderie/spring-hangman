package com.yui.spring_hangman;

import com.yui.spring_hangman.service.HangmanService;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.ApplicationScope;

import java.util.HashMap;
import java.util.Map;

@ApplicationScope
@Component
public class GameSessionManager {
    private final Map<String, HangmanService> activeGames = new HashMap<>();

    public HangmanService getGame(String gameId) {
        return activeGames.computeIfAbsent(gameId, id -> new HangmanService());
    }
}
