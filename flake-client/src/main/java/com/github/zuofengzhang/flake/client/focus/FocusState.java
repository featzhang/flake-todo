package com.github.zuofengzhang.flake.client.focus;

/**
 * https://www.jianshu.com/p/972ce0c98ce2
 */
public interface FocusState {
     StateEnum getStateEnum();
    FocusState timeUp(FocusContext context);

    FocusState interrupt(FocusContext context);

    FocusState finish(FocusContext context);
}
