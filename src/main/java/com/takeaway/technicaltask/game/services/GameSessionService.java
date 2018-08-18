package com.takeaway.technicaltask.game.services;

import com.google.common.base.Stopwatch;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;
import com.google.common.collect.Iterators;
import com.google.common.collect.Lists;
import com.takeaway.technicaltask.game.domain.GameRules;
import com.takeaway.technicaltask.game.domain.MoveEvent;
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
    private final LocalEventAwarePlayer localPlayer;
    @NonNull
    private final RemoteEventAwarePlayer remotePlayer;

    public MoveEvent makeLocalMove(@NonNull MoveEvent event) {
        return localPlayer.makeMove(event);
    }

    public GameResult playNewGame(int initialValue) {
        Stopwatch timer = Stopwatch.createStarted();
        List<MoveEvent> moves = makeAllMoves(initialValue);
        timer.stop();

        GameResult.GameResultBuilder result = GameResult.builder();
        if (moves.isEmpty()) {
            result
                    .moves(ImmutableList.of())
                    .status("Game was not started");

        } else {
            MoveEvent lastMove = Iterables.getLast(moves);
            result
                    .moves(moves)
                    .status(lastMove.getSuccess() && GameRules.isGameFinished(lastMove.getValue())
                            ? "Game was finished" : "Game was interrupted");
            if (GameRules.isGameFinished(lastMove.getValue())) {
                result.winningPlayer(lastMove.getPlayer());
            }
        }

        return result.build();
    }

    private List<MoveEvent> makeAllMoves(int initialValue) {
        List<MoveEvent> result = Lists.newArrayList();

        MoveEvent move = MoveEvent.builder()
                .value(initialValue)
                .player(localPlayer.getPlayerNumber())
                .build();
        log.debug("PLayer {} starts with new move {}", localPlayer.getClass().getSimpleName(), move);
        result.add(move);

        Iterator<EventAwarePlayer> players = Iterators.cycle(remotePlayer, localPlayer);


        while (move != null) {
            EventAwarePlayer player = players.next();
            log.debug("PLayer {} gets move {} to process", player.getClass().getSimpleName(), move);
            move = player.makeMove(move);
            result.add(move);
            if (!move.getSuccess() || GameRules.isGameFinished(move.getValue())) {
                move = null;
            }
        }
        return ImmutableList.copyOf(result);
    }
}
