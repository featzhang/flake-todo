package com.github.zuofengzhang.flake.client.entity;

import java.io.Serializable;

import static com.github.zuofengzhang.flake.client.constraints.FlakeLabel.*;

public enum REPETITION_TYPE implements Serializable {
    NONE(REPETITION_NONE), EVERY_DAY(REPETITION_EVERY_DAY), EVERY_WEEK(REPETITION_EVERY_WEEK), EVERY_MONTH(REPETITION_EVERY_MONTH), EVERY_YEAR(REPETITION_EVERY_YEAR), EVERY_LUNAR_YEAR(REPETITION_EVERY_LUNAR_YEAR),
    LEGAL_WORKDAY(REPETITION_LEGAL_WORKDAY), EBBINGHAUS_MNEMONICS(REPETITION_EBBINGHAUS_MNEMONICS),
    ;

    private final String label;

    private REPETITION_TYPE(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

}
