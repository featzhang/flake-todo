package com.github.zuofengzhang.flake.client.entity;

import java.io.Serializable;
import java.util.ResourceBundle;

public enum TaskType implements Serializable {
    /**
     *
     */
    YESTERDAY_REVIEW(1, ResourceBundle.getBundle("flake-client").getString("yesterday_review")),
    TODAY_PLAN(2, ResourceBundle.getBundle("flake-client").getString("today_plan")),
    TOMATO_POTATO(3, ResourceBundle.getBundle("flake-client").getString("tomato_potato")),
    TODAY_SUMMARY(4, ResourceBundle.getBundle("flake-client").getString("today_summary"));

    private final String cname;
    private final int cid;

    TaskType(int id, String cname) {
        this.cname = cname;
        this.cid = id;
    }

    public String getCname() {
        return cname;
    }

    public int getCId() {
        return cid;
    }

    public static TaskType findByCName(String cname) {
        for (TaskType taskType : TaskType.values()) {
            if (taskType.getCname().equals(cname)) {
                return taskType;
            }
        }
        return null;
    }

    public static TaskType findById(int id) {
        for (TaskType taskType : TaskType.values()) {
            if (taskType.getCId() == (id)) {
                return taskType;
            }
        }
        return null;
    }

}
