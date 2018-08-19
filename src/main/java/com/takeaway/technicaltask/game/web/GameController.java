package com.takeaway.technicaltask.game.web;

import com.takeaway.technicaltask.game.domain.GameEvent;
import com.takeaway.technicaltask.game.services.GameResult;
import com.takeaway.technicaltask.game.services.GameSession;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ThreadLocalRandom;

@RestController
@RequiredArgsConstructor
public class GameController {
    @NonNull
    private final GameSession gameSession;

    /***
     * "Start a new game" request. This is blocking request which returns final results once the game is finished.
     * The initial number which is used to start a game will be selected randomly from 2 to 1000.
     *
     * Impl. Details: The method accepts a GET request in order to make it easy demonstrable from web browser.
     */
    @GetMapping("/play")
    public GameResult play() {
        return play(ThreadLocalRandom.current().nextInt(2, 1000));
    }

    /***
     * "Start a new game" request. This is blocking request which returns final results once the game is finished.
     * The initial number which is used to start a game should be provided as a parameter.
     *
     * Impl. Details: The method accepts a GET request in order to make it easy demonstrable from web browser.
     */
    @GetMapping("/play/{initialValue}")
    public GameResult play(@PathVariable("initialValue") int initialValue) {
        return gameSession.playNewGame(initialValue);
    }

    /***
     * A request from a Game's owner to make a move in the game. Player should move and send new game value as a
     * response back.
     * @param event incomming event with current game's state.
     * @return updated GameEvent with the move made by the Player.
     */
    @PostMapping(value = Routes.MAKE_MOVE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public GameEvent makeMove(@RequestBody GameEvent event) {
        return gameSession.makeMoveBack(event);
    }
}
