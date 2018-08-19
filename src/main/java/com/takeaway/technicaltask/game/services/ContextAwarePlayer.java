package com.takeaway.technicaltask.game.services;

import com.takeaway.technicaltask.game.domain.GameEvent;

/***
 * Represents a top level concept of a Game Player. Having the fact that all games happen in the context of
 * {@link GameSession} there are always 2 types of players from Game Session's point of view: a game owner who initiates
 * a game and "controls" the dialog between players and the opponent whos responsibility is only to remotely react on
 * request of game owner and make it's own move.
 * To hide these differences from the {@link GameSession} we are introducing this concept of a
 * Player who can behave differently based on it's own context.
 */
public interface ContextAwarePlayer {
    GameEvent makeMove(GameEvent move);

    int getPlayerNumber();
}
