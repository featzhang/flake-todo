package com.github.featzhang.flake.client.entity;

import com.github.featzhang.flake.client.consts.FlakeLabel;

public enum TimerActionType {
    FOCUS(30 * 60, FlakeLabel.FOCUS),
    BREAK(5 * 60, FlakeLabel.BREAKING);

    private final int totalSeconds;
    private final String displayName;

    TimerActionType(int totalSeconds, String displayName) {
        this.totalSeconds = totalSeconds;
        this.displayName = displayName;
    }

    public int getTotalSeconds() {
        return totalSeconds;
    }

    public String getDisplayName() {
        return displayName;
    }
}
