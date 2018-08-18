package com.takeaway.technicaltask.game.services;

import com.takeaway.technicaltask.game.domain.Move;
import com.takeaway.technicaltask.game.domain.MoveEvent;
import com.takeaway.technicaltask.game.domain.Player;
import com.takeaway.technicaltask.game.domain.ValidationException;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class LocalEventAwarePlayer implements EventAwarePlayer{
    @NonNull
    private final Player player;

    @Override
    public MoveEvent makeMove(@NonNull MoveEvent event) {
        try {
            MoveEvent gameEvent = player
                    .makeMove(Move.fromEvent(event))
                    .toGameEvent(getPlayerNumber());
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
