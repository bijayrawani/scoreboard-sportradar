package com.sportradar.football.scoreboard;

import com.sportradar.football.scoreboard.datamodel.Team;
import com.sportradar.football.scoreboard.impl.ScoreBoardServiceImpl;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;

public class ScoreBoardTest {

    private ScoreBoardService scoreBoardService;
    private Team mexico;
    private Team canada;
    private Team spain;
    private Team brazil;
    private Team germany;
    private Team france;
    private Team uruguay;
    private Team italy;
    private Team argentina;
    private Team australia;

    @Before
    public void setUp() {
        scoreBoardService = new ScoreBoardServiceImpl();
        mexico = new Team("1", "Mexico");
        canada = new Team("2", "Canada");
        spain = new Team("3", "Spain");
        brazil = new Team("4", "Brazil");
        germany = new Team("5", "Germany");
        france = new Team("6", "France");
        uruguay = new Team("7", "Uruguay");
        italy = new Team("8", "Italy");
        argentina = new Team("9", "Argentina");
        australia = new Team("10", "Australia");
    }

    @Test
    public void testCreateScoreBoard() {
        int scoreBoardId = scoreBoardService.createScoreBoard();
        assertTrue(scoreBoardId > 0);
    }

    @Test
    public void testStartGame() {
        int scoreBoardId = scoreBoardService.createScoreBoard();
        scoreBoardService.startGame(scoreBoardId, mexico, canada);
        List<String> summary = scoreBoardService.getLiveSummary(scoreBoardId);
        assertEquals(1, summary.size()); // One live game
        assertEquals("Mexico 0 - Canada 0", summary.get(0));
    }

    @Test
    public void testFinishGame() {
        int scoreBoardId = scoreBoardService.createScoreBoard();
        int gameId = scoreBoardService.startGame(scoreBoardId, mexico, canada);
        scoreBoardService.finishGame(scoreBoardId, gameId);
        List<String> liveSummary = scoreBoardService.getLiveSummary(scoreBoardId);
        List<String> completedSummary = scoreBoardService.getCompletedSummary(scoreBoardId);
        assertTrue(liveSummary.isEmpty()); // No live games
        assertEquals(1, completedSummary.size());
        assertEquals("Mexico 0 - Canada 0", completedSummary.get(0));
    }

    @Test
    public void testUpdateScore() {
        int scoreBoardId = scoreBoardService.createScoreBoard();
        int gameId = scoreBoardService.startGame(scoreBoardId, mexico, canada);
        scoreBoardService.updateScore(scoreBoardId, gameId, 1, 2);
        List<String> liveSummary = scoreBoardService.getLiveSummary(scoreBoardId);
        assertEquals("Mexico 1 - Canada 2", liveSummary.get(0));
        scoreBoardService.finishGame(scoreBoardId, gameId);
        List<String> completedSummary = scoreBoardService.getCompletedSummary(scoreBoardId);
        assertEquals("Mexico 1 - Canada 2", completedSummary.get(0));
    }

    /*
    * Games are started and scores are updated, here we could see the one time update for
    * the scores for every game, however when the real game is ongoing, there will be multiple
    * times update of score are required.
    * */
    @Test
    public void testGetSummary() throws InterruptedException {
        int scoreBoardId = scoreBoardService.createScoreBoard();
        int game1 = scoreBoardService.startGame(scoreBoardId, mexico, canada);
        Thread.sleep(500);
        int game2 = scoreBoardService.startGame(scoreBoardId, spain, brazil);
        Thread.sleep(500);
        int game3 = scoreBoardService.startGame(scoreBoardId, germany, france);
        Thread.sleep(500);
        int game4 = scoreBoardService.startGame(scoreBoardId, uruguay, italy);
        Thread.sleep(500);
        int game5 = scoreBoardService.startGame(scoreBoardId, argentina, australia);
        Thread.sleep(500);

        scoreBoardService.updateScore(scoreBoardId, game1, 0, 5);
        scoreBoardService.updateScore(scoreBoardId, game2, 10, 2);
        scoreBoardService.updateScore(scoreBoardId, game3, 2, 2);
        scoreBoardService.updateScore(scoreBoardId, game4, 6, 6);
        scoreBoardService.updateScore(scoreBoardId, game5, 3, 1);

        scoreBoardService.finishGame(scoreBoardId, game1);
        scoreBoardService.finishGame(scoreBoardId, game2);
        scoreBoardService.finishGame(scoreBoardId, game3);
        scoreBoardService.finishGame(scoreBoardId, game4);
        scoreBoardService.finishGame(scoreBoardId, game5);

        List<String> completedSummary = scoreBoardService.getCompletedSummary(scoreBoardId);
        List<String> liveSummary = scoreBoardService.getLiveSummary(scoreBoardId);

        assertTrue(liveSummary.isEmpty());
        assertEquals("Uruguay 6 - Italy 6", completedSummary.get(0));
        assertEquals("Spain 10 - Brazil 2", completedSummary.get(1));
        assertEquals("Mexico 0 - Canada 5", completedSummary.get(2));
        assertEquals("Argentina 3 - Australia 1", completedSummary.get(3));
        assertEquals("Germany 2 - France 2", completedSummary.get(4));
    }
}