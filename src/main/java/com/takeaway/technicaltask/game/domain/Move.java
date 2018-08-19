package com.takeaway.technicaltask.game.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.experimental.Wither;

import java.util.Optional;

/***
 * Entity represents a Game and is responsible for keeping current game state. Such as players, who is active player
 * and can respond if game is not finished yet.
 * For simplicity of the test task:
 *     - we do not implement different states of the game, which might be a case in real world example;
 *     - we allow Game entity to be a mutable object;
 *     - we do all business rules checks inside the Game class. in real world creating checking specifications might
 *     make sense to consider.
 *     - we do not create specific exceptions for different validation use cases, use just one everywhere.
 *     - for real world example it might make sense to extract value as a separated Value Object incapsulating all rules
 *     and logic related to applying new move to the current value in the object itself.
 *
 *
 *
 *     do not use reactive programmimg such as Flux or Reactive
 */
public class Move {
    private int result;
    private Optional<Integer> added;

    public Move(int result) {
        this(result, null);
    }

    public Move(int result, Integer added) {
        GameRules.resultShouldBeCorrect(result);
        if (added != null) {
            GameRules.addedShouldBeInProperRange(added);
        }

        this.result = result;
        this.added = Optional.ofNullable(added);
    }

    public static Move fromEvent(@NonNull Event event) {
        return new Move(event.getResult(), event.getAdded());
    }


    public Event toSuccessEvent(int player) {
        return Event.builder()
                .player(player)
                .result(result)
                .added(added.orElse(null))
                .success(true)
                .build();
    }

    @Value
    @Builder
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonDeserialize(builder = Event.EventBuilder.class)
    public static class Event {
        private final Integer added;
        private final int result;
        @Wither
        private final Boolean success;
        @Wither
        private final int player;

        @JsonPOJOBuilder(withPrefix = "")
        public static class EventBuilder {
        }
    }
}
