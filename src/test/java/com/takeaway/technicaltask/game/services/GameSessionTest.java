package com.takeaway.technicaltask.game.services;

import com.takeaway.technicaltask.game.domain.GameEvent;
import com.takeaway.technicaltask.game.domain.Player;
import org.junit.Test;
import org.mockito.InOrder;
import org.mockito.stubbing.Answer;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class GameSessionTest {
    @Test
    public void makeAllMoves_rotatesPlayers() {
        Player player = new Player();
        Answer<GameEvent> makeMove = (invocation) -> {
            GameEvent event = invocation.getArgument(0);
            return player.makeMove(event.getResult()).toSuccessEvent(1);
        };
        LocalPlayer localPlayer = mock(LocalPlayer.class);
        when(localPlayer.makeMove(any())).thenAnswer(makeMove);
        RemotePlayer remotePlayer = mock(RemotePlayer.class);
        when(remotePlayer.makeMove(any())).thenAnswer(makeMove);

        GameSession underTest = new GameSession(localPlayer, remotePlayer);


        List<GameEvent> events = underTest.makeAllMoves(GameEvent.builder().result(56).build());


        assertThat(events.size()).isEqualTo(5);

        InOrder inOrder = inOrder(localPlayer, remotePlayer);
        inOrder.verify(remotePlayer).makeMove(any());
        inOrder.verify(localPlayer).makeMove(any());
        inOrder.verify(remotePlayer).makeMove(any());
        inOrder.verify(localPlayer).makeMove(any());
    }
}