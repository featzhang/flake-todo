package com.github.featzhang.flake.client.constraints;

import java.io.Serializable;

import static com.github.featzhang.flake.client.constraints.FlakeLabel.*;

/**
 */
public enum TASK_PRIORITY implements Serializable {
    /**
     *
     */
    importance_urgency(1, IMPORTANCE_URGENCY),
    importance_but_not_urgency(2, IMPORTANCE_BUT_NOT_URGENCY),
    not_importance_but_urgency(3, NOT_IMPORTANCE_BUT_URGENCY),
    not_importance_not_urgency(4, NOT_IMPORTANCE_NOT_URGENCY),
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

    
}
