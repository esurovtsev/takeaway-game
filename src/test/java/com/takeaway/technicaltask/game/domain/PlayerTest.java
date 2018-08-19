package com.takeaway.technicaltask.game.domain;

import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class PlayerTest {
    private Player underTest;

    @Before
    public void before() {
        underTest = new Player();
    }

    @Test
    public void applyMove_correct() {
        Move expected = new Move(19, 1);
        assertThat(underTest.applyMove(56, 1)).isEqualTo(expected);
    }

    @Test
    public void applyMove_gameWasFinished() {
        assertThatThrownBy(() -> underTest.applyMove(1, 0)).isInstanceOf(ValidationException.class);
    }

    @Test
    public void applyMove_resultIncorrect() {
        assertThatThrownBy(() -> underTest.applyMove(0, 1)).isInstanceOf(ValidationException.class);
    }

    @Test
    public void applyMove_addedInWrongRange() {
        assertThatThrownBy(() -> underTest.applyMove(56, 2)).isInstanceOf(ValidationException.class);
        assertThatThrownBy(() -> underTest.applyMove(56, -2)).isInstanceOf(ValidationException.class);
    }

    @Test
    public void calculateAdded_plusOne() {
        assertThat(underTest.calculateAdded(56)).isEqualTo(1);
    }

    @Test
    public void calculateAdded_zero() {
        assertThat(underTest.calculateAdded(57)).isEqualTo(0);
    }

    @Test
    public void calculateAdded_minusOne() {
        assertThat(underTest.calculateAdded(58)).isEqualTo(-1);
    }

    @Test
    public void makeMove_plusOne() {
        Move expected = new Move(19, 1);
        assertThat(underTest.makeMove(56)).isEqualTo(expected);
    }

    @Test
    public void makeMove_zero() {
        Move expected = new Move(19, 0);
        assertThat(underTest.makeMove(57)).isEqualTo(expected);
    }

    @Test
    public void makeMove_minusOne() {
        Move expected = new Move(19, -1);
        assertThat(underTest.makeMove(58)).isEqualTo(expected);
    }
}