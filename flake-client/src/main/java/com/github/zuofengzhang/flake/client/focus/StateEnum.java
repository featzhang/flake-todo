package com.github.zuofengzhang.flake.client.focus;

import java.util.Arrays;
import java.util.Optional;

public enum StateEnum {
    INTERRUPTING(0, "INTERRUPTING"),
    WORKING(1, "WORKING"),
    NAPPING(2, "NAPPING");

    StateEnum(int key, String cName) {
        this.key = key;
        this.cName = cName;
    }

    private final int key;
    private final String cName;

    public int getKey() {
        return key;
    }

    public String getCName() {
        return cName;
    }

    public static StateEnum findByKey(int key) {
        return Arrays.stream(StateEnum.values()).filter(e -> e.getKey() == key).findFirst().orElse(null);
    }
}
