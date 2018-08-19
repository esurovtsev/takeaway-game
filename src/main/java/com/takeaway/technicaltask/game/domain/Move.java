package com.takeaway.technicaltask.game.domain;

/***
 * Simple Value Object which keeps information about current move.
 */
public class Move {
    private int result;
    private int added;

    public Move(int result, int added) {
        GameRules.resultShouldBeCorrect(result);
        GameRules.addedShouldBeInProperRange(added);
        this.result = result;
        this.added = added;
    }

    public GameEvent toSuccessEvent(int player) {
        return GameEvent.builder()
                .player(player)
                .result(result)
                .added(added)
                .success(true)
                .build();
    }
}
