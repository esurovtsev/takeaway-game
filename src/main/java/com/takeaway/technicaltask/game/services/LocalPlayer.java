package com.takeaway.technicaltask.game.services;

import com.takeaway.technicaltask.game.domain.GameEvent;
import com.takeaway.technicaltask.game.domain.Player;
import com.takeaway.technicaltask.game.domain.ValidationException;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/***
 * LocalPlayer is represented by the system for 2 cases: 1) as an Entity responsible for starting the game and making
 * moves on game's owner side and 2) as an Entiry responsible for processing remotely coming requests from another game's
 * owning server.
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class LocalPlayer implements ContextAwarePlayer {
    @NonNull
    private final Player player;

    @Override
    public GameEvent makeMove(@NonNull GameEvent event) {
        try {
            GameEvent gameEvent = player
                    .makeMove(event.getResult())
                    .toSuccessEvent(getPlayerNumber());
            return gameEvent;

        } catch (ValidationException e) {
            log.error("Error while getting local response", e);
            return event.withPlayer(getPlayerNumber()).withSuccess(false);
        }

    }

    @Override
    public int getPlayerNumber() {
        return 1;
    }
}
