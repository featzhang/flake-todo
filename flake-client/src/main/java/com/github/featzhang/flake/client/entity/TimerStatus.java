package com.github.featzhang.flake.client.entity;

import lombok.Data;

@Data
public class TimerStatus {
    private long remainingSeconds;
    private TimerActionType type;
    private long startTime;

    public TimerStatus(TimerActionType type, long startTime) {
        this.type = type;
        this.startTime = startTime;
        remainingSeconds = type.getTotalSeconds();
    }

    public void countDown() {
        this.remainingSeconds--;
    }
}






























