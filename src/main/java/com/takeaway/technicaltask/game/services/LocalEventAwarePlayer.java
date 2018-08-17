package com.takeaway.technicaltask.game.services;

import com.takeaway.technicaltask.game.domain.Game;
import com.takeaway.technicaltask.game.domain.GameEvent;
import com.takeaway.technicaltask.game.domain.Player;
import com.takeaway.technicaltask.game.domain.ValidationException;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LocalEventAwarePlayer implements EventAwarePlayer{
    private final static int PLAYER_ID = 1;
    @NonNull
    private final Player player;

    @Override
    public GameEvent makeMove(@NonNull GameEvent event) {
        try {
            return player
                    .makeMove(Game.fromEvent(event))
                    .toGameEvent(PLAYER_ID);

        } catch (ValidationException e) {
            return event.withPlayer(PLAYER_ID).withSuccess(false);
        }

    }
}
