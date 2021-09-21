package com.github.zuofengzhang.flake.client.focus.impl;

import com.github.zuofengzhang.flake.client.focus.*;

public class InterruptingState extends AbstractFocusState {
    public InterruptingState(StateEnum stateEnum) {
        super(stateEnum);
    }

    @Override
    public FocusState finish(FocusContext context) {
        FocusItem item = context.getItem();
        if (item.isSit()) {
            return FocusContext.NAPPING_STATE;
        } else {
            return FocusContext.WORKING_STATE;
        }
    }

    @Override
    public FocusState timeUp(FocusContext context) {
        return FocusContext.NAPPING_STATE;
    }
}
