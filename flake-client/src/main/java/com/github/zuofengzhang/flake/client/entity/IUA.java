package com.github.zuofengzhang.flake.client.entity;

import com.github.zuofengzhang.flake.client.constraints.FlakeLabel;

import java.io.Serializable;

/**
 * @author averyzhang
 * @date 2021/2/26
 */
public enum IUA implements Serializable {
    /**
     *
     */
    IMPORTANCE_URGENCY(1, FlakeLabel.label("label_importance_urgency")),
    IMPORTANCE_BUT_NOT_URGENCY(3, FlakeLabel.label("label_importance_but_not_urgency")),
    NOT_IMPORTANCE_BUT_URGENCY(2, FlakeLabel.label("label_not_importance_but_urgency")),
    NOT_IMPORTANCE_NOT_URGENCY(4, FlakeLabel.label("label_not_importance_not_urgency"));
    private final int    code;
    private final String cname;

    private IUA(int code, String cname) {
        this.code  = code;
        this.cname = cname;
    }

    public int getCode() {
        return code;
    }

    public String getCname() {
        return cname;
    }

    public IUA findByCode(int code) {
        for (IUA iua : IUA.values()) {
            if (code == iua.code) {
                return iua;
            }
        }
        return null;
    }
}
