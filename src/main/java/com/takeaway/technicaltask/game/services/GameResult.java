package com.takeaway.technicaltask.game.services;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;
import com.takeaway.technicaltask.game.domain.GameEvent;
import com.takeaway.technicaltask.game.domain.GameRules;
import lombok.Builder;
import lombok.NonNull;
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

    public static GameResult fromMoves(@NonNull List<GameEvent> moves) {
        return !moves.isEmpty() ? getGameWasFinishedResult(moves) : getGameWasNotStartedResult();
    }

    private static GameResult getGameWasFinishedResult(@NonNull List<GameEvent> moves) {
        GameEvent lastMove = Iterables.getLast(moves);
        boolean gameWasFinishedSuccessfully = lastMove.getSuccess() && GameRules.isGameFinished(lastMove.getResult());
        return GameResult.builder()
                .moves(moves)
                .status(gameWasFinishedSuccessfully ? "WIN!!!" : "FAILED!")
                .winningPlayer(gameWasFinishedSuccessfully ? lastMove.getPlayer() : null)
                .build();
    }

    private static GameResult getGameWasNotStartedResult() {
        return GameResult.builder()
                .moves(ImmutableList.of())
                .status("FAILED!")
                .build();
    }
}
