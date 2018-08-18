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
@JsonDeserialize(builder = MoveEvent.MoveEventBuilder.class)
public class MoveEvent {
    private final Integer added;
    private final int value;
    @Wither
    private final Boolean success;
    @Wither
    private final int player;

    @JsonPOJOBuilder(withPrefix = "")
    public static class MoveEventBuilder {
    }
}
