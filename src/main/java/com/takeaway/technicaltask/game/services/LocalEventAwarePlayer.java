package com.takeaway.technicaltask.game.services;

import com.takeaway.technicaltask.game.domain.Move;
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
    public Move.Event makeMove(@NonNull Move.Event event) {
        try {
            Move.Event gameEvent = player
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
