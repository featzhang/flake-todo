package com.github.zuofengzhang.flake.client.entity;

import lombok.Data;

import java.io.Serializable;

public class TimerStatus implements Serializable {
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

    public long getRemainingSeconds() {
        return remainingSeconds;
    }

    public void setRemainingSeconds(long remainingSeconds) {
        this.remainingSeconds = remainingSeconds;
    }

    public TimerActionType getType() {
        return type;
    }

    public void setType(TimerActionType type) {
        this.type = type;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }
}






























