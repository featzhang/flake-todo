package com.github.featzhang.flake.client.consts;

import com.github.featzhang.flake.client.utils.FlakeResource;

public enum FlakeLabel {
    application_name,
    CURRENT_DAY,
    WORKING,
    FOCUS,
    BREAKING,
    TIME_TO_WEAK,
    TASK_EDIT,
    CREATE,
    LAST_UPDATE,
    FINISH,
    IMPORTANCE_URGENCY,
    IMPORTANCE_BUT_NOT_URGENCY,
    NOT_IMPORTANCE_BUT_URGENCY,
    NOT_IMPORTANCE_NOT_URGENCY,
    SETTINGS;

    private final String value;

    private FlakeLabel() {
        this.value = FlakeResource.label("label_" + this.name().toLowerCase());
    }

    public String value() {
        return value;
    }

    public static String label(String s) {
        return FlakeResource.label(s);
    }
}
