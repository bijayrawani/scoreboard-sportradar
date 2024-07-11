package com.sportradar.football.scoreboard.impl;

import com.sportradar.football.scoreboard.datamodel.Game;
import com.sportradar.football.scoreboard.datamodel.GameStatus;

import java.util.*;

public class ScoreBoard {
    private final int scoreBoardId;
    private final Map<Integer, Game> liveGames = new LinkedHashMap<>();
    private final Map<Integer, Game> completedGames = new LinkedHashMap<>();
    private static int nextGameId = 1;

    public ScoreBoard(int scoreBoardId) {
        this.scoreBoardId = scoreBoardId;
    }

    public int addGame(Game game) {
        int gameId = nextGameId++;
        game.setStatus(GameStatus.STARTED);
        liveGames.put(gameId, game);
        return gameId;
    }

    public void removeGame(int gameId) {
        Game game = liveGames.remove(gameId);
        if (game != null) {
            game.setStatus(GameStatus.FINISHED);
            completedGames.put(gameId, game);
        }
    }

    public Game getGame(int gameId) {
        return liveGames.get(gameId);
    }

    public List<Game> getLiveGames() {
        return new ArrayList<>(liveGames.values());
    }

    public List<Game> getCompletedGames() {
        return new ArrayList<>(completedGames.values());
    }

    public int getScoreBoardId() {
        return scoreBoardId;
    }

    public static int getNextGameId() {
        return nextGameId;
    }
}