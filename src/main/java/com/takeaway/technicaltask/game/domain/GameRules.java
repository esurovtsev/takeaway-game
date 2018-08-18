package com.takeaway.technicaltask.game.domain;

import lombok.experimental.UtilityClass;

import java.util.stream.IntStream;

/**
 * The class is responsible for performing some Game's related validation. We extracted this logic from the Game Object
 * because these rules are being reused between several entities. All other validation rules for simplicity reasons are
 * kept inside Game.
 */
@UtilityClass
public class GameRules {
    private int minMove = -1;
    private int maxMove = 1;

    void moveShouldBeInProperRangeAndResultDividedByThree(int current, int move) {
        if (move < minMove || move > maxMove) {
            throw new ValidationException("A move should be one of [-1,0,1]");
        }
        if ((current + move) % 3 != 0) {
            throw new ValidationException("Result of the move should be divided by 3");
        }
    }

    boolean isValidMove(int current, int move) {
        // in general it might not be a good way to check validity by generating exceptions, but simplicity of test solutuin
        // we can use it here since we're still interesting in providing additional messages about what was wrong for
        // our clients.
        try {
            moveShouldBeInProperRangeAndResultDividedByThree(current, move);
            return true;

        } catch (ValidationException e) {
            return false;
        }
    }

    IntStream possibleMoves() {
        return IntStream.range(minMove, maxMove + 1);
    }

    public static boolean isGameFinished(int value) {
        return value == 1;
    }
}
