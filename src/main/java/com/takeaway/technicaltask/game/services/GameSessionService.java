package com.takeaway.technicaltask.game.services;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;
import com.google.common.collect.Iterators;
import com.google.common.collect.Lists;
import com.takeaway.technicaltask.game.domain.GameEvent;
import com.takeaway.technicaltask.game.domain.GameRules;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Iterator;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class GameSessionService {
    @NonNull
    private final LocalPlayer localPlayer;
    @NonNull
    private final RemotePlayer remotePlayer;

    public GameEvent makeLocalMove(@NonNull GameEvent event) {
        return localPlayer.makeMove(event);
    }

    public GameResult playNewGame(int initialValue) {
        List<GameEvent> moves = makeAllMoves(initialValue);

        GameResult.GameResultBuilder result = GameResult.builder();
        if (moves.isEmpty()) {
            result
                    .moves(ImmutableList.of())
                    .status("FAILED!");

        } else {
            GameEvent lastMove = Iterables.getLast(moves);
            result
                    .moves(moves)
                    .status(lastMove.getSuccess() && GameRules.isGameFinished(lastMove.getResult())
                            ? "WIN!!!" : "FAILED!");
            if (GameRules.isGameFinished(lastMove.getResult())) {
                result.winningPlayer(lastMove.getPlayer());
            }
        }

        return result.build();
    }

    private List<GameEvent> makeAllMoves(int initialValue) {
        List<GameEvent> result = Lists.newArrayList();

        GameEvent move = GameEvent.builder()
                .result(initialValue)
                .player(localPlayer.getPlayerNumber())
                .success(true)
                .build();
        log.debug("PLayer {} starts with new move {}", localPlayer.getClass().getSimpleName(), move);
        result.add(move);

        Iterator<ContextAwarePlayer> players = Iterators.cycle(remotePlayer, localPlayer);


        while (move != null) {
            ContextAwarePlayer player = players.next();
            log.debug("PLayer {} gets move {} to process", player.getClass().getSimpleName(), move);
            move = player.makeMove(move);
            result.add(move);
            if (!move.getSuccess() || GameRules.isGameFinished(move.getResult())) {
                move = null;
            }
        }
        return ImmutableList.copyOf(result);
    }
}
