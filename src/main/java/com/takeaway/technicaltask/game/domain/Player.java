package com.takeaway.technicaltask.game.domain;

import com.google.common.annotations.VisibleForTesting;
import lombok.NonNull;
import org.springframework.stereotype.Component;

@Component
public class Player {
    public Move makeMove(@NonNull Move game) {
        int move = calculateNextMove(game);
        return game.makeMove(move);
    }

    @VisibleForTesting
    int calculateNextMove(@NonNull Move game) {
        return GameRules
                .possibleMoves()
                .filter(possibleMove -> GameRules.isValidMove(game.getValue(), possibleMove))
                .findAny()
                .orElseThrow(() -> new ValidationException(
                        String.format("Not possible to make a move for game value " + game.getValue())));
    }
}
