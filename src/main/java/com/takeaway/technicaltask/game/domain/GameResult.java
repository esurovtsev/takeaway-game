package com.takeaway.technicaltask.game.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Value;

import java.util.List;

@Value
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GameResult {
    private String status;
    private Integer winningPLayer;
    private List<GameEvent> moves;
}
