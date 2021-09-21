package com.github.zuofengzhang.flake.client.focus.impl;

import com.github.zuofengzhang.flake.client.focus.*;

public class NappingState extends AbstractFocusState {
    public NappingState(StateEnum stateEnum) {
        super(stateEnum);
    }

    @Override
    public FocusState finish(FocusContext context) {
        FocusItem item = context.getItem();
        if (item.isInterruptingItem()) {
            if (item.isFinished()) {
                return FocusContext.WORKING_STATE;
            } else {
                return FocusContext.INTERRUPTING_STATE;
            }
        } else {
            return FocusContext.WORKING_STATE;
        }
    }

    @Override
    public FocusState interrupt(FocusContext context) {
        return FocusContext.INTERRUPTING_STATE;
    }
}
