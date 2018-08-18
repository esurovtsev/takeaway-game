package com.takeaway.technicaltask.game.domain;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class MoveTest {

    @Test
    public void start_incorrectInitialValue() {
        assertThatThrownBy(() -> new Move(-1, 0)).isInstanceOf(ValidationException.class);
        assertThatThrownBy(() -> new Move(0, 0)).isInstanceOf(ValidationException.class);
        assertThatThrownBy(() -> new Move(1, 0)).isInstanceOf(ValidationException.class);
    }

    @Test
    public void isFinished_correctAndFinished() {
        Move move1 = new Move(9, 0);
        Move move2 = move1.applyStep(0);
        assertThat(move2.isFinished()).isTrue();
    }

    @Test
    public void isFinished_correctAndNotFinished() {
        Move underTest = new Move(6, 0);
        assertThat(underTest.isFinished()).isFalse();
    }

    @Test
    public void isFinished_valueNotMultipliedByThree() {
        Move underTest = new Move(5, 0);
        assertThat(underTest.isFinished()).isFalse();
    }

    @Test
    public void makeMove_moveValueTooSmall() {
        Move underTest = new Move(57, 0);
        assertThatThrownBy(() -> underTest.applyStep(-3)).isInstanceOf(ValidationException.class);
    }

    @Test
    public void makeMove_moveValueTooHigh() {
        Move underTest = new Move(57, 0);
        assertThatThrownBy(() -> underTest.applyStep(3)).isInstanceOf(ValidationException.class);
    }

    @Test
    public void makeMove_valueDoesNotDivideByThree() {
        Move underTest = new Move(57, 0);
        assertThatThrownBy(() -> underTest.applyStep(1)).isInstanceOf(ValidationException.class);
    }

    @Test
    public void makeMove_returnNewValue() {
        Move move1 = new Move(56, 0);

        Move move2 = move1.applyStep(1);
        assertThat(move2.getValue()).isEqualTo(19);

        Move move3 = move2.applyStep(-1);
        assertThat(move3.getValue()).isEqualTo(6);
    }

    @Test
    public void makeMove_notPossiblePlayFinishedGame() {
        Move move1 = new Move(6, 0);
        Move move2 = move1.applyStep(0);
        assertThatThrownBy(() -> move2.applyStep(0)).isInstanceOf(ValidationException.class);
    }

    @Test
    public void makeMove_savesPreviousValue() {
        Move move1 = new Move(56);
        Move move2 = move1.applyStep(1);
        assertThat(move2.getValue()).isEqualTo(19);
        assertThat(move2.getAdded()).isEqualTo(1);
    }

    @Test
    public void test_lastMove() {
        Move underTest = new Move(4);
        Move game = underTest.applyStep(-1);
    }

    @Test
    public void toGameEvent() {
        Move move1 = new Move(56);
        Move move2 = move1.applyStep(1);
        MoveEvent result = move2.toGameEvent(1);
        assertThat(result.getValue()).isEqualTo(19);
        assertThat(result.getAdded()).isEqualTo(1);
    }

    @Test
    public void fromEvent() {
        MoveEvent event = MoveEvent.builder().value(19).build();
        Move underTest = Move.fromEvent(event);

        assertThat(underTest.getValue()).isEqualTo(19);
    }
}