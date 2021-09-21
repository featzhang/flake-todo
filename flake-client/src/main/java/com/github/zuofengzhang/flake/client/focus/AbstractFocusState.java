package com.github.zuofengzhang.flake.client.focus;

public abstract class AbstractFocusState implements FocusState {
    private StateEnum stateEnum;


    public AbstractFocusState(StateEnum stateEnum) {
        this.stateEnum = stateEnum;
    }

    public StateEnum getStateEnum() {
        return stateEnum;
    }

    public void setStateEnum(StateEnum stateEnum) {
        this.stateEnum = stateEnum;
    }

    @Override
    public String toString() {
        return "AbstractFocusState{" +
                "stateEnum=" + stateEnum +
                '}';
    }

    @Override
    public FocusState timeUp(FocusContext context) {
        throw new UnsupportedOperationException();
    }

    @Override
    public FocusState interrupt(FocusContext context) {
        throw new UnsupportedOperationException();
    }

    @Override
    public FocusState finish(FocusContext context) {
        throw new UnsupportedOperationException();
    }
}
