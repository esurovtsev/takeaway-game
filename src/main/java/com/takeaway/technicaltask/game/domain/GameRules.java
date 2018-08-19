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
    private int minAdded = -1;
    private int maxAdded = 1;

    void resultShouldBeDividedByThree(int result, int added) {
        if ((result + added) % 3 != 0) {
            throw new ValidationException("Result of the move should be divided by 3");
        }
    }

    boolean isValidMove(int result, int added) {
        // in general it might not be a good way to check validity by generating exceptions, but simplicity of test solutuin
        // we can use it here since we're still interesting in providing additional messages about what was wrong for
        // our clients.
        try {
            GameRules.addedShouldBeInProperRange(added);
            GameRules.resultShouldBeDividedByThree(result, added);
            return true;

        } catch (ValidationException e) {
            return false;
        }
    }

    IntStream possibleAddedValues() {
        return IntStream.range(minAdded, maxAdded + 1);
    }

    public static boolean isGameFinished(int value) {
        return value == 1;
    }

    public static void resultShouldBeCorrect(int result) {
        if (result < 1) {
            throw new ValidationException("Result should be more 2 or more");
        }
    }

    public static void addedShouldBeInProperRange(int added) {
        if (added < minAdded || added > maxAdded) {
            throw new ValidationException("An added should be one of [-1,0,1]");
        }
    }
}
