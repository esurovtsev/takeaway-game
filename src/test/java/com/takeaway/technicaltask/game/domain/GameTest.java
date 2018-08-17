package com.takeaway.technicaltask.game.domain;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class GameTest {

    @Test
    public void start_incorrectInitialValue() {
        assertThatThrownBy(() -> new Game(-1, 0)).isInstanceOf(ValidationException.class);
        assertThatThrownBy(() -> new Game(0, 0)).isInstanceOf(ValidationException.class);
        assertThatThrownBy(() -> new Game(1, 0)).isInstanceOf(ValidationException.class);
    }

    @Test
    public void isFinished_correctAndFinished() {
        Game move1 = new Game(9, 0);
        Game move2 = move1.makeMove(0);
        assertThat(move2.isFinished()).isTrue();
    }

    @Test
    public void isFinished_correctAndNotFinished() {
        Game underTest = new Game(6, 0);
        assertThat(underTest.isFinished()).isFalse();
    }

    @Test
    public void isFinished_valueNotMultipliedByThree() {
        Game underTest = new Game(5, 0);
        assertThat(underTest.isFinished()).isFalse();
    }

    @Test
    public void makeMove_moveValueTooSmall() {
        Game underTest = new Game(57, 0);
        assertThatThrownBy(() -> underTest.makeMove(-3)).isInstanceOf(ValidationException.class);
    }

    @Test
    public void makeMove_moveValueTooHigh() {
        Game underTest = new Game(57, 0);
        assertThatThrownBy(() -> underTest.makeMove(3)).isInstanceOf(ValidationException.class);
    }

    @Test
    public void makeMove_valueDoesNotDivideByThree() {
        Game underTest = new Game(57, 0);
        assertThatThrownBy(() -> underTest.makeMove(1)).isInstanceOf(ValidationException.class);
    }

    @Test
    public void makeMove_returnNewValue() {
        Game move1 = new Game(56, 0);

        Game move2 = move1.makeMove(1);
        assertThat(move2.getValue()).isEqualTo(19);

        Game move3 = move2.makeMove(-1);
        assertThat(move3.getValue()).isEqualTo(6);
    }

    @Test
    public void makeMove_notPossiblePlayFinishedGame() {
        Game move1 = new Game(6, 0);
        Game move2 = move1.makeMove(0);
        assertThatThrownBy(() -> move2.makeMove(0)).isInstanceOf(ValidationException.class);
    }

    @Test
    public void makeMove_savesPreviousValue() {
        Game move1 = new Game(56);
        Game move2 = move1.makeMove(1);
        assertThat(move2.getValue()).isEqualTo(19);
        assertThat(move2.getAdded()).isEqualTo(1);
    }

    @Test
    public void toGameEvent() {
        Game move1 = new Game(56);
        Game move2 = move1.makeMove(1);
        GameEvent result = move2.toGameEvent(1);
        assertThat(result.getValue()).isEqualTo(19);
        assertThat(result.getAdded()).isEqualTo(1);
        assertThat(result.getGameFinished()).isFalse();
    }

    @Test
    public void fromEvent() {
        GameEvent event = GameEvent.builder().value(19).build();
        Game underTest = Game.fromEvent(event);

        assertThat(underTest.getValue()).isEqualTo(19);
    }
}