package com.takeaway.technicaltask.game.domain;

import com.google.common.annotations.VisibleForTesting;
import lombok.NonNull;
import org.springframework.stereotype.Component;

@Component
public class Player {
    public Move makeMove(@NonNull Move move) {
        int stepValue = calculateNextStep(move);
        return move.applyStep(stepValue);
    }

    @VisibleForTesting
    int calculateNextStep(@NonNull Move move) {
        return GameRules
                .possibleMoves()
                .filter(possibleMove -> GameRules.isValidMove(move.getValue(), possibleMove))
                .findAny()
                .orElseThrow(() -> new ValidationException(
                        String.format("Not possible to make a move for game value " + move.getValue())));
    }
}
