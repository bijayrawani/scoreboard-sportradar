package com.sportradar.football.scoreboard.impl;

import com.sportradar.football.scoreboard.ScoreBoardService;
import com.sportradar.football.scoreboard.datamodel.Team;
import com.sportradar.football.scoreboard.datamodel.Game;
import com.sportradar.football.scoreboard.datamodel.GameStatus;

import java.util.*;
import java.util.stream.Collectors;

public class ScoreBoardServiceImpl implements ScoreBoardService {
    private static int nextScoreBoardId = 1;
    private final Map<Integer, ScoreBoard> scoreBoards = new HashMap<>();

    @Override
    public int createScoreBoard() {
        int scoreBoardId = nextScoreBoardId++;
        ScoreBoard scoreBoard = new ScoreBoard(scoreBoardId);
        scoreBoards.put(scoreBoardId, scoreBoard);
        return scoreBoardId;
    }

    @Override
    public int startGame(int scoreBoardId, Team homeTeam, Team awayTeam) {
        ScoreBoard scoreBoard = scoreBoards.get(scoreBoardId);
        if (scoreBoard != null) {
            Game game = new Game(ScoreBoard.getNextGameId(), homeTeam, awayTeam);
            return scoreBoard.addGame(game);
        }
        throw new IllegalArgumentException("ScoreBoard with ID " + scoreBoardId + " not found");
    }

    @Override
    public void finishGame(int scoreBoardId, int gameId) {
        ScoreBoard scoreBoard = scoreBoards.get(scoreBoardId);
        if (scoreBoard != null) {
            scoreBoard.removeGame(gameId);
        } else {
            throw new IllegalArgumentException("ScoreBoard with ID " + scoreBoardId + " not found");
        }
    }

    @Override
    public void updateScore(int scoreBoardId, int gameId, int homeScore, int awayScore) {
        ScoreBoard scoreBoard = scoreBoards.get(scoreBoardId);
        if (scoreBoard != null) {
            Game game = scoreBoard.getGame(gameId);
            if (game != null && game.getStatus() == GameStatus.STARTED) {
                game.getHomeTeam().setScore(homeScore);
                game.getAwayTeam().setScore(awayScore);
            }
        } else {
            throw new IllegalArgumentException("ScoreBoard with ID " + scoreBoardId + " not found");
        }
    }

    @Override
    public List<String> getCompletedSummary(int scoreBoardId) {
        ScoreBoard scoreBoard = scoreBoards.get(scoreBoardId);
        if (scoreBoard != null) {
            List<Game> games = scoreBoard.getCompletedGames();
            return games.stream()
                    .sorted((g1, g2) -> {
                        int scoreComparison = Integer.compare(g2.getTotalScore(), g1.getTotalScore());
                        if (scoreComparison == 0) {
                            return g2.getStartTime().compareTo(g1.getStartTime());
                        }
                        return scoreComparison;
                    })
                    .map(Game::toString)
                    .collect(Collectors.toList());
        }
        throw new IllegalArgumentException("ScoreBoard with ID " + scoreBoardId + " not found");
    }

    @Override
    public List<String> getLiveSummary(int scoreBoardId) {
        ScoreBoard scoreBoard = scoreBoards.get(scoreBoardId);
        if (scoreBoard != null) {
            List<Game> games = scoreBoard.getLiveGames();
            return games.stream()
                    .sorted((g1, g2) -> {
                        int scoreComparison = Integer.compare(g2.getTotalScore(), g1.getTotalScore());
                        if (scoreComparison == 0) {
                            return g2.getStartTime().compareTo(g1.getStartTime());
                        }
                        return scoreComparison;
                    })
                    .map(Game::toString)
                    .collect(Collectors.toList());
        }
        throw new IllegalArgumentException("ScoreBoard with ID " + scoreBoardId + " not found");
    }
}
