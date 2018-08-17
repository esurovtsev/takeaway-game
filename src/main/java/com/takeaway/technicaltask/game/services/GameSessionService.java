package com.takeaway.technicaltask.game.services;

import com.google.common.base.Stopwatch;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;
import com.google.common.collect.Iterators;
import com.google.common.collect.Lists;
import com.takeaway.technicaltask.game.domain.GameEvent;
import com.takeaway.technicaltask.game.domain.GameResult;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Iterator;
import java.util.List;

@Component
@RequiredArgsConstructor
public class GameSessionService {
    @NonNull
    private final LocalEventAwarePlayer localPlayer;
    @NonNull
    private final RemoteEventAwarePlayer remotePlayer;

    public GameEvent makeLocalMove(@NonNull GameEvent event) {
        return localPlayer.makeMove(event);
    }

    public GameResult playNewGame(int initialValue) {
        Stopwatch timer = Stopwatch.createStarted();
        List<GameEvent> moves = makeAllMoves(initialValue);
        timer.stop();

        GameResult.GameResultBuilder result = GameResult.builder().duration(timer.elapsed());
        if (moves.isEmpty()) {
            result
                    .moves(ImmutableList.of())
                    .status("Game was not started");

        } else {
            GameEvent lastMove = Iterables.getLast(moves);
            result
                    .moves(moves)
                    .status(lastMove.getSuccess() && lastMove.getGameFinished()
                            ? "Game was finished" : "Game was interrupted");
            if (lastMove.getGameFinished() && lastMove.getPlayer() != null) {
                result.winningPLayer(lastMove.getPlayer());
            }
        }

        return result.build();
    }

    private List<GameEvent> makeAllMoves(int initialValue) {
        List<GameEvent> result = Lists.newArrayList();
        Iterator<EventAwarePlayer> players = Iterators.cycle(localPlayer, remotePlayer);
        GameEvent move = GameEvent.builder().value(initialValue).build();

        while (move != null) {
            move = players.next().makeMove(move);
            result.add(move);
            if (!move.getSuccess() || move.getGameFinished()) {
                move = null;
            }
        }
        return ImmutableList.copyOf(result);
    }
}
