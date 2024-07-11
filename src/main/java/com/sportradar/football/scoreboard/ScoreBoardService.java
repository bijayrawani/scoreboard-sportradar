package com.sportradar.football.scoreboard;

import com.sportradar.football.scoreboard.datamodel.Team;

import java.util.List;

public interface ScoreBoardService {
    int createScoreBoard();
    int startGame(int scoreBoardId, Team homeTeam, Team awayTeam);
    void finishGame(int scoreBoardId, int gameId);
    void updateScore(int scoreBoardId, int gameId, int homeScore, int awayScore);
    List<String> getCompletedSummary(int scoreBoardId);
    List<String> getLiveSummary(int scoreBoardId);
}

