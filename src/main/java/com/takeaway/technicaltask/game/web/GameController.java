package com.takeaway.technicaltask.game.web;

import com.takeaway.technicaltask.game.domain.GameEvent;
import com.takeaway.technicaltask.game.services.GameSessionService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ThreadLocalRandom;

@RestController
@RequiredArgsConstructor
public class GameController {
    @NonNull
    private final GameSessionService gameSessionService;

    /***
     * Start a new game request. This is blocking request which returns final results once game is finished.
     * @return JSON representation of the game result once game will be finished.
     *
     *
     * it's get request in order to make it easier to run via browser
     *
     */
    @GetMapping("/play")
    public GameResultDto play() {
        return play(ThreadLocalRandom.current().nextInt(2, 1000));
    }

    /***
     * Start a new game request. This is blocking request which returns final results once game is finished.
     * @param initialValue A Parameter which defines initial value for a game. Put here zero if you want Player
     *                     to decide on initial value.
     * @return JSON representation of the game result once game will be finished.
     */
    @GetMapping("/play/{initialValue}")
    public GameResultDto play(int initialValue) {
        return GameResultDto.fromGameResult(gameSessionService.playNewGame(initialValue));
    }

    /***
     * A request from a Game's owner to make a move in the game. Player should move and send new game value as a
     * response back.
     * @param event incomming event with current game's state.
     * @return updated GameEvent with the move made by the Player.
     */
    @PostMapping(value = Routes.MAKE_MOVE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public GameEvent makeMove(@RequestBody GameEvent event) {
        return gameSessionService.makeLocalMove(event);
    }
}
