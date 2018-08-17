package com.takeaway.technicaltask.game.services;

import com.takeaway.technicaltask.game.domain.GameEvent;
import com.takeaway.technicaltask.game.web.Routes;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Component
@RequiredArgsConstructor
public class RemoteEventAwarePlayer implements EventAwarePlayer {
    private final static int PLAYER_ID = 2;

    @Value("application.opponentApiHost")
    private String opponentApiHost;
    @NonNull
    private final RestTemplate restTemplate;

    @Override
    public GameEvent makeMove(@NonNull GameEvent event) {
        try {
            GameEvent response = restTemplate.postForObject(opponentApiHost + Routes.MAKE_MOVE, event, GameEvent.class);
            return response.withPlayer(PLAYER_ID);

        } catch (RestClientException e) {
            return event.withPlayer(PLAYER_ID).withSuccess(false);
        }

    }
}
