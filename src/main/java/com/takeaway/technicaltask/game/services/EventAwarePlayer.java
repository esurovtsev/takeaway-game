package com.takeaway.technicaltask.game.services;

import com.takeaway.technicaltask.game.domain.MoveEvent;

public interface EventAwarePlayer {
    MoveEvent makeMove(MoveEvent move);

    int getPlayerNumber();
}
