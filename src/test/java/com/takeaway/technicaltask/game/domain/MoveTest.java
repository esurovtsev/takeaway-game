package com.takeaway.technicaltask.game.domain;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class MoveTest {
    @Test
    public void construct_correct() {
        new Move(56, 1);
    }

    @Test
    public void construct_addedNotInTheRange() {
        assertThatThrownBy(() -> new Move(56, 2)).isInstanceOf(ValidationException.class);
        assertThatThrownBy(() -> new Move(56, -2)).isInstanceOf(ValidationException.class);
    }

    @Test
    public void construct_resultIncorrect() {
        assertThatThrownBy(() -> new Move(0, 0)).isInstanceOf(ValidationException.class);
        assertThatThrownBy(() -> new Move(-1, 0)).isInstanceOf(ValidationException.class);
    }
}