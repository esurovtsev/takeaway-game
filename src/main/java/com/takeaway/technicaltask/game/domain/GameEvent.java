package com.takeaway.technicaltask.game.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Value;
import lombok.experimental.Wither;

@Value
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonDeserialize(builder = GameEvent.GameEventBuilder.class)
public class GameEvent {
    private final Integer added;
    private final int result;
    @Wither
    private final Boolean success;
    @Wither
    private final int player;

    @JsonPOJOBuilder(withPrefix = "")
    public static class GameEventBuilder {
    }
}
