package com.takeaway.technicaltask.game.services;

import com.takeaway.technicaltask.game.domain.GameEvent;
import com.takeaway.technicaltask.game.web.Routes;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

/***
 * RemotePlayer is a representation of a remote PLayer sitting on another service. The class knows how to
 * communicate with remote server and obtain another's Player move from it.
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class RemotePlayer implements ContextAwarePlayer {
    @Value("${application.opponentApiHost}")
    private String opponentApiHost;
    @NonNull
    private final RestTemplate restTemplate;

    @Override
    public GameEvent makeMove(@NonNull GameEvent event) {
        try {
            GameEvent response = restTemplate.postForObject(opponentApiHost + Routes.MAKE_MOVE, event, GameEvent.class);
            return response.withPlayer(getPlayerNumber());

        } catch (RestClientException e) {
            log.error("Error while getting remote response", e);
            return event.withPlayer(getPlayerNumber()).withSuccess(false);
        }

    }

    @Override
    public int getPlayerNumber() {
        return 2;
    }
}
