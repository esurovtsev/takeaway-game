# takeaway-game

## Goal
The Goal is to implement a game with two independent units - the players - communicating with each other using an "API".
Each player should run on its own as an independent component. The application should be built with keeping concepts of
Domain Driven Design in mind, avoiding creating anemic domain model. Component should communicate via sending each other
events. 

## Dictionary
Here are some entities, terms, and their meaning highly used across the application.

- **Game** - a process of passing and transforming *Result* between two players until *Result* becomes 1. In this case 
*Game* finishes and the *Player* who reached 1 considered as a winning player.
- **Player** - a domain entity representing player's knowledge about how to play and some logic for making game's moves 
(see ```com.takeaway.technicaltask.game.domain.Player```)
- **Move** - a simple Value Object which keeps information about current move 
(see ```com.takeaway.technicaltask.game.domain.Move```) in the *Game*.
- **Result** - Current result of the *Game* at a specific time. Result is a whole number and is part of *Move* and 
*GameEvent*.
- **Added** - A value (-1, 0 or +1) applied by last *Player* to the *Result* on every *Move* based on rules of the *Game*. 
Probably better term could be move or step but for keeping consistency with the test task description. *Added* is part 
of *Move* and *GameEvent*.
- **Game Session** - A service responsible for supporting game process. Can start and finish a *Game* by a request or 
ask a *Player* to respond in a remote *Game* (when current component is not responsible for starting a *Game*) (see
```com.takeaway.technicaltask.game.services.GameSession```).
- **ContextAwarePlayer** - Represents a top level concept of a Game Player. Having the fact that all games happen in the
context of *Game Session* there are always 2 types of players from *Game Session*'s point of view: a game owner who 
initiates a game and "controls" the dialog between players and the opponent whos responsibility is only to remotely 
react on request of game owner and make it's own move.


## Assumptions
For simplicity of the task which is still a test task some assumptions were take into account and limitations were made:

- do not implement different states of the game, which might be a case in real world example;
- do not create specific exceptions for different validation use cases, use just one everywhere.
- do not use reactive programming such as Flux or Reactive, all calls made to the service are working in blockable manner.
- unit tests were implemented only for places where it was really needed to verify how components work together.
- a new game start with a GET request which was make on purpose. So it's possible to test it with just firing a new url
in web browser.  

## How to run

### How to run server

The game is a java web application based on spring boot framework. So the App can be built using maven command 
```mvn clean package``` and then started with command
 
```mvn spring-boot:run```. By default next parameters pre-configured in the app:

- **port=8080** - the port, the application should run on
- **opponent=http://localhost:8080** - the host of another instance of this service which will be playing in the game a 
role of Opponent.

These parameters can be reconfigured by defining them as params of command line run commands. For example

```mvn spring-boot:run -Dport=8090 -Dopponent=http://localhost:8085```

tells maven to run a server on port 8090 (the full adreess of the server will be ```http://localhost:8090```) when the 
opponent is sitting at ```http://localhost:8085```) 
   

For a game 2 instances sitting on different ports should be running at the same time, pointing at each other (so for 
every service is possible to start a new game):

```
mvn spring-boot:run -Dport=8090 -Dopponent=http://localhost:8095
mvn spring-boot:run -Dport=8095 -Dopponent=http://localhost:8090
```

or even a single server can play against itself:

```
mvn spring-boot:run -Dport=8090 -Dopponent=http://localhost:8090
``` 

or just ```mvn spring-boot:run``` if port 8080 on your machine is free.


Once server runs, open in web browser a url: 
- ```http://localhost:8090/play/56``` - for starting a new game with 56 as a initial value
- ```http://localhost:8090/play``` - for starting a new game and let server to decide what should be initial value 

the result of the game will be sent back to browser in a JSON format once game finishes, so it makes sense to install
web browser's extension which can show JSON in structured view for better readability.

```
{
    "status": "WIN!!!",
    "winningPlayer": 1,
    "moves": [
        {
            "result": 56,
            "success": true,
            "player": 1
        },
        {
            "added": 1,
            "result": 19,
            "success": true,
            "player": 2
        },
        {
            "added": -1,
            "result": 6,
            "success": true,
            "player": 1
        },
        {
            "added": 0,
            "result": 2,
            "success": true,
            "player": 2
        },
        {
            "added": 1,
            "result": 1,
            "success": true,
            "player": 1
        }
    ]
}
```