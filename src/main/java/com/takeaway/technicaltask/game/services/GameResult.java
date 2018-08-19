package com.takeaway.technicaltask.game.services;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.takeaway.technicaltask.game.domain.GameEvent;
import lombok.Builder;
import lombok.Value;

import java.util.List;

/***
 * A simple POJO to return results of the game back to caller (usually to render as a response in browser)
 */
@Value
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GameResult {
    private String status;
    private Integer winningPlayer;
    private List<GameEvent> moves;
}
