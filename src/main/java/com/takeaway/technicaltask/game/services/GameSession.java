package com.takeaway.technicaltask.game.services;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableList;
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

/***
 * A service responsible for supporting game process. Can start and finish a game by a request or ask a Player to
 * respond in a remote game (when current component is not responsible for starting a Game).
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class GameSession {
    @NonNull
    private final LocalPlayer localPlayer;
    @NonNull
    private final RemotePlayer remotePlayer;

    public GameEvent makeMoveBack(@NonNull GameEvent event) {
        return localPlayer.makeMove(event);
    }

    public GameResult playNewGame(int initialValue) {
        GameEvent firstMove = GameEvent.builder()
                .result(initialValue)
                .player(localPlayer.getPlayerNumber())
                .success(true)
                .build();

        return GameResult.fromMoves(makeAllMoves(firstMove));
    }

    @VisibleForTesting
    List<GameEvent> makeAllMoves(@NonNull GameEvent firstMove) {
        List<GameEvent> result = Lists.newArrayList(firstMove);
        // localPlayer already made it's move by starting new game with firstMove, so next should go removePlayer
        Iterator<ContextAwarePlayer> players = Iterators.cycle(remotePlayer, localPlayer);

        GameEvent move = firstMove;
        while (move != null) {
            ContextAwarePlayer player = players.next();
            move = player.makeMove(move);
            result.add(move);
            if (!move.getSuccess() || GameRules.isGameFinished(move.getResult())) {
                move = null;
            }
        }
        return ImmutableList.copyOf(result);
    }
}
