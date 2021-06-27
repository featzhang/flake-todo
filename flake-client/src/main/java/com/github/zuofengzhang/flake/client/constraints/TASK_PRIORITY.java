package com.github.zuofengzhang.flake.client.constraints;

import java.io.Serializable;

import static com.github.zuofengzhang.flake.client.constraints.FlakeLabel.label;

/**
 * @author averyzhang
 * @date 2021/6/26
 */
public enum TASK_PRIORITY implements Serializable {
    /**
     *
     */
    importance_urgency(1, label("label_importance_urgency")),
    importance_but_not_urgency(2, label("label_importance_but_not_urgency")),
    not_importance_but_urgency(3, label("label_not_importance_but_urgency")),
    not_importance_not_urgency(4, label("label_not_importance_not_urgency")),
    ;
    private final int    id;
    private final String label;

    TASK_PRIORITY(int id, String label) {
        this.id    = id;
        this.label = label;
    }

    public int getId() {
        return id;
    }

    public String getLabel() {
        return label;
    }

    public static TASK_PRIORITY findById(int id) {
        for (TASK_PRIORITY priority : TASK_PRIORITY.values()) {
            if (priority.id == id) {
                return priority;
            }
        }
        return null;
    }

    public static String lbl(int id) {
        return findById(id).getLabel();
    }
}
