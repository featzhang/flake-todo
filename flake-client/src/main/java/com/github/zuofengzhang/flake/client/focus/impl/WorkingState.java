package com.github.zuofengzhang.flake.client.focus.impl;

import com.github.zuofengzhang.flake.client.focus.AbstractFocusState;
import com.github.zuofengzhang.flake.client.focus.FocusContext;
import com.github.zuofengzhang.flake.client.focus.FocusState;
import com.github.zuofengzhang.flake.client.focus.StateEnum;

import static com.github.zuofengzhang.flake.client.focus.StateEnum.INTERRUPTING;

public class WorkingState extends AbstractFocusState {
    public WorkingState(StateEnum stateEnum) {
        super(stateEnum);
    }

    @Override
    public FocusState interrupt(FocusContext context) {
        return FocusContext.INTERRUPTING_STATE;
    }

    @Override
    public FocusState timeUp(FocusContext context) {
        return FocusContext.NAPPING_STATE;
    }

    @Override
    public FocusState finish(FocusContext context) {
        return FocusContext.NAPPING_STATE;
    }
}
