package com.takeaway.technicaltask.game.domain;

import lombok.Builder;
import lombok.Value;

import java.time.Duration;
import java.util.List;

@Value
@Builder
public class GameResult {
    private String status;
    private Integer winningPLayer;
    private Duration duration;
    private List<GameEvent> moves;
}
