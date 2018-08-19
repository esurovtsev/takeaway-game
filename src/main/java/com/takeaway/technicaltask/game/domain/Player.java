package com.takeaway.technicaltask.game.domain;

import com.google.common.annotations.VisibleForTesting;
import org.springframework.stereotype.Component;

@Component
public class Player {
    public Move makeMove(int result) {
        GameRules.resultShouldBeCorrect(result);
        int added = calculateAdded(result);
        return applyMove(result, added);
    }

    @VisibleForTesting
    int calculateAdded(int result) {
        return GameRules
                .possibleAddedValues()
                .filter(possibleAdd -> GameRules.isValidMove(result, possibleAdd))
                .findAny()
                .orElseThrow(() -> new ValidationException(
                        String.format("Not possible to make a move for game result " + result)));
    }

    @VisibleForTesting
    Move applyMove(int result, int added) {
        if (GameRules.isGameFinished(result)) {
            throw new ValidationException("Not possible to make a move. The game was finished already.");
        }
        GameRules.addedShouldBeInProperRange(added);
        GameRules.resultShouldBeDividedByThree(result, added);

        return new Move((result + added) / 3, added);
    }
}
