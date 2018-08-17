package com.takeaway.technicaltask.game.web;

import com.takeaway.technicaltask.game.domain.GameResult;

public class GameResultDto {
    private String status;
    private String winningPLayer;
    private String time;

    public static GameResultDto fromGameResult(GameResult gameResult) {
        return null;
    }
}
