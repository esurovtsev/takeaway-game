package com.takeaway.technicaltask.game.domain;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class PlayerTest {

    @Test
    public void calculateNextMove_plusOne() {
        Move game = new Move(56);
        Player underTest = new Player();
        assertThat(underTest.calculateNextMove(game)).isEqualTo(1);

    }

    @Test
    public void calculateNextMove_zero() {
        Move game = new Move(57);
        Player underTest = new Player();
        assertThat(underTest.calculateNextMove(game)).isEqualTo(0);

    }

    @Test
    public void calculateNextMove_minusOne() {
        Move game = new Move(58);
        Player underTest = new Player();
        assertThat(underTest.calculateNextMove(game)).isEqualTo(-1);
    }

    @Test
    public void makeMove() {
        Move game = new Move(58, 0);
        Player underTest = new Player();
        Move nextMove = underTest.makeMove(game);
        assertThat(nextMove.getValue()).isEqualTo(19);
        assertThat(nextMove.getAdded()).isEqualTo(-1);
        assertThat(nextMove.isFinished()).isFalse();
    }
}