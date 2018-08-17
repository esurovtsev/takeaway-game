package com.takeaway.technicaltask.game.domain;

import org.junit.Test;

import static com.google.common.collect.ImmutableList.toImmutableList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class GameRulesTest {

    @Test
    public void moveShouldBeInProperRangeAndResultDividedByThree() {
        GameRules.moveShouldBeInProperRangeAndResultDividedByThree(56, 1);

        assertThatThrownBy(() -> GameRules.moveShouldBeInProperRangeAndResultDividedByThree(56, -2))
                .isInstanceOf(ValidationException.class);
        assertThatThrownBy(() -> GameRules.moveShouldBeInProperRangeAndResultDividedByThree(56, 2))
                .isInstanceOf(ValidationException.class);
        assertThatThrownBy(() -> GameRules.moveShouldBeInProperRangeAndResultDividedByThree(56, 0))
                .isInstanceOf(ValidationException.class);
    }

    @Test
    public void isValidMove() {
        assertThat(GameRules.isValidMove(56, 1)).isTrue();
        assertThat(GameRules.isValidMove(56, -2)).isFalse();
        assertThat(GameRules.isValidMove(56, 2)).isFalse();
        assertThat(GameRules.isValidMove(56, 0)).isFalse();
    }

    @Test
    public void possibleMoves() {
        int[] possibleMoves = GameRules.possibleMoves().toArray();
        assertThat(possibleMoves).containsExactlyInAnyOrder(-1, 0, 1);
    }
}