package com.takeaway.technicaltask.game.domain;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class GameRulesTest {
    @Test
    public void resultShouldBeDividedByThree_correct() {
        GameRules.resultShouldBeDividedByThree(56, 1);
    }

    @Test
    public void resultShouldBeDividedByThree_wrongAddedRange() {
        assertThatThrownBy(() -> GameRules.resultShouldBeDividedByThree(56, -2))
                .isInstanceOf(ValidationException.class);
        assertThatThrownBy(() -> GameRules.resultShouldBeDividedByThree(56, 2))
                .isInstanceOf(ValidationException.class);
    }

    @Test
    public void resultShouldBeDividedByThree_notDividedByThree() {
        assertThatThrownBy(() -> GameRules.resultShouldBeDividedByThree(56, 0))
                .isInstanceOf(ValidationException.class);
    }

    @Test
    public void isValidMove_correct() {
        assertThat(GameRules.isValidMove(56, 1)).isTrue();
    }

    @Test
    public void isValidMove_wrongAddedRange() {
        assertThat(GameRules.isValidMove(56, -2)).isFalse();
        assertThat(GameRules.isValidMove(56, 2)).isFalse();
    }

    @Test
    public void isValidMove_notDividedByThree() {
        assertThat(GameRules.isValidMove(56, 0)).isFalse();
    }

    @Test
    public void possibleAddedValues_rangeIncludesMinAndMax() {
        assertThat(GameRules.possibleAddedValues().toArray()).containsExactlyInAnyOrder(-1, 0, 1);
    }
}