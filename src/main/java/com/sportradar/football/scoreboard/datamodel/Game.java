package com.sportradar.football.scoreboard.datamodel;

import java.time.LocalDateTime;

public class Game {
    private final int gameId;
    private final Team homeTeam;
    private final Team awayTeam;
    private final LocalDateTime startTime;
    private GameStatus status;

    public Game(int gameId, Team homeTeam, Team awayTeam) {
        this.gameId = gameId;
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
        this.startTime = LocalDateTime.now();
        this.status = GameStatus.NOT_STARTED;
    }

    public int getGameId() {
        return gameId;
    }

    public Team getHomeTeam() {
        return homeTeam;
    }

    public Team getAwayTeam() {
        return awayTeam;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public GameStatus getStatus() {
        return status;
    }

    public void setStatus(GameStatus status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return homeTeam + " " + homeTeam.getScore() + " - " + awayTeam + " " + awayTeam.getScore();
    }

    public int getTotalScore() {
        return homeTeam.getScore() + awayTeam.getScore();
    }
}