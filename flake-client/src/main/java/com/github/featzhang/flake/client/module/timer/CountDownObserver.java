package com.github.featzhang.flake.client.module.timer;

public interface CountDownObserver {

    /**
     * callback ui component
     */
    void updateRemain(int remain);

    /**
     * callback when timeout
     */
    void onTimeout();
}
