# Football world cup score board application

A live scoreboard application for tracking football World Cup games and scores.

## Features
- Start a game with initial score 0-0, capturing home team and away team.
- Finish a game to remove it from the scoreboard.
- Update score of a game with home team score and away team score.
- Get a summary of games ordered by the most recently added, for those with the same total score.

## Design of datamodel domains and service layer and testcases of the application
#### 1. Datamodel domains
- GameStatus
- Game
- Team
#### 2. Create service and their implementations classes
- **ScoreBoardService** interface with the methods (startGame, finishGame, updateScore and getSummary)
- **ScoreBoard** class - manages the game lifecycle and game data.
- **ScoreBoardServiceImpl** class - implementation of ScoreBoardService.
#### 3. Testcases for **ScoreBoardService**
- **ScoreBoardTest** class
## Build application as Maven Project, run the following commands from the project root directory
- Set up POM.xml with all required dependencies  
- To compile and create jar file **"mvn clean install"**
- To run the testcases **"mvn test"**