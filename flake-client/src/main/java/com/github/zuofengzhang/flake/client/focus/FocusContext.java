package com.github.zuofengzhang.flake.client.focus;

import com.github.zuofengzhang.flake.client.focus.impl.InterruptingState;

public class FocusContext {
    public static final FocusState INTERRUPTING_STATE = new InterruptingState(StateEnum.INTERRUPTING);
    public static final FocusState WORKING_STATE = new InterruptingState(StateEnum.WORKING);
    public static final FocusState NAPPING_STATE = new InterruptingState(StateEnum.NAPPING);
    private FocusState state;
    private FocusItem item;

    public FocusContext() {
    }

    public FocusContext(FocusState state) {
        this.state = state;
    }

    public FocusItem getItem() {
        return item;
    }

    public void setItem(FocusItem item) {
        this.item = item;
    }

    public void timeUp(FocusItem item) {
        if (item != null) {
            this.setItem(item);
        }
        this.state = state.timeUp(this);
    }

    public void interrupt(FocusItem item) {
        if (item != null) {
            this.setItem(item);
        }
        this.state = state.interrupt(this);
    }

    public void finish(FocusItem item) {
        if (item != null) {
            this.setItem(item);
        }
        this.state = state.finish(this);
    }

    public void initState() {

    }

    public void setState(FocusState state) {
        this.state = state;
    }

    public FocusState getState() {
        return state;
    }
}
