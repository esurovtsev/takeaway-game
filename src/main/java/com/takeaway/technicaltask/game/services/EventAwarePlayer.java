package com.takeaway.technicaltask.game.services;

import com.takeaway.technicaltask.game.domain.GameEvent;

public interface EventAwarePlayer {
    GameEvent makeMove(GameEvent move);
}
