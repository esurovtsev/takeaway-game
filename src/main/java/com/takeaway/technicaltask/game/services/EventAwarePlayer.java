package com.takeaway.technicaltask.game.services;

import com.takeaway.technicaltask.game.domain.Move;

public interface EventAwarePlayer {
    Move.Event makeMove(Move.Event move);

    int getPlayerNumber();
}
