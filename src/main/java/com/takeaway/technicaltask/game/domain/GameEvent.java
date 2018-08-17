package com.takeaway.technicaltask.game.domain;

import lombok.Builder;
import lombok.Value;
import lombok.experimental.Wither;

@Value
@Builder
public class GameEvent {
    private final int value;
    private final Integer added;

    @Wither
    private final Boolean success;
    private final Boolean gameFinished;

    @Wither
    private final Integer player;
}
