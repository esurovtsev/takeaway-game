package com.takeaway.technicaltask.game.domain;

import com.google.common.annotations.VisibleForTesting;
import lombok.NonNull;

/***
 * Entity represents a Game and is responsible for keeping current game state. Such as players, who is active player
 * and can respond if game is not finished yet.
 * For simplicity of the test task:
 *     - we do not implement different states of the game, which might be a case in real world example;
 *     - we allow Game entity to be a mutable object;
 *     - we do all business rules checks inside the Game class. in real world creating checking specifications might
 *     make sense to consider.
 *     - we do not create specific exceptions for different validation use cases, use just one everywhere.
 *     - for real world example it might make sense to extract value as a separated Value Object incapsulating all rules
 *     and logic related to applying new move to the current value in the object itself.
 *
 *
 *
 *     do not use reactive programmimg such as Flux or Reactive
 */
public class Move {
    private int value;
    private Integer added;

    /***
     * @param value current value of the game. Based on rules should be 2 or more
     */
    Move(int value) {
        this(value, null);
    }

    Move(int value, Integer added) {
        if (value < 2) {
            throw new ValidationException("Value should be more 2 or more");
        }

        this.value = value;
        this.added = added;
    }

    public boolean isFinished() {
        return GameRules.isGameFinished(getValue());
    }

    public int getValue() {
        return value;
    }

    @VisibleForTesting
    Integer getAdded() {
        return added;
    }

    public static Move fromEvent(@NonNull MoveEvent event) {
        return new Move(event.getValue(), event.getAdded());
    }

    public Move applyStep(int input) {
        if (isFinished()) {
            throw new ValidationException("Not possible to make a move. The game was finished already.");
        }
        GameRules.moveShouldBeInProperRangeAndResultDividedByThree(value, input);

        return new Move((value + input) / 3, input);
    }

    public MoveEvent toGameEvent(int player) {
        return MoveEvent.builder()
                .player(player)
                .value(value)
                .added(added)
                .success(true)
                .build();
    }
}
