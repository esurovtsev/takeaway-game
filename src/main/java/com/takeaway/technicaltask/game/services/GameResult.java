package com.takeaway.technicaltask.game.services;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.takeaway.technicaltask.game.domain.MoveEvent;
import lombok.Builder;
import lombok.Value;

import java.util.List;

@Value
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GameResult {
    private String status;
    private Integer winningPlayer;
    private List<MoveEvent> moves;
}
