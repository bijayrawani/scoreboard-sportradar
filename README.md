# This is my simple ScoreBoard application to implement the football world cup event between multiple teams

## Create the datamodel objects as follows
- GameStatus
- Game
- Team
## Create service which includes all the methods required for ScoreBoardOperations
- ScoreBoardService with the following methods
- startGame(int scoreBoardId, Team homeTeam, Team awayTeam);
- finishGame(int scoreBoardId, int gameId);
- updateScore(int scoreBoardId, int gameId, int homeScore, int awayScore);
- getCompletedSummary(int scoreBoardId);
- getLiveSummary(int scoreBoardId);
## Write implementation class ScoreBoardServiceImpl to implement all the service contracts for ScoreBoardService interface

## Write testcases to test all the contracts/operations for ScoreBoardService
- ScoreBoardTest

## Created this application as Maven Project, run the following commands from the project root directory to get it executed
- To compile and create jar file "mvn clean install"
- To run the testcases "mvn test"