package com.github.zuofengzhang.flake.client.entity;

import java.io.Serializable;
import java.util.ResourceBundle;

public enum TaskType implements Serializable {
    YESTERDAY_REVIEW(1, ResourceBundle.getBundle("flake-client").getString("label_yesterday_review")),
    TODAY_PLAN(2, ResourceBundle.getBundle("flake-client").getString("label_today_plan")),
    TOMATO_POTATO(3, ResourceBundle.getBundle("flake-client").getString("label_tomato_potato")),
    TODAY_SUMMARY(4, ResourceBundle.getBundle("flake-client").getString("label_today_summary"));

    private final String cname;
    private final int id;

    TaskType(int id, String cname) {
        this.cname = cname;
        this.id = id;
    }

    public String getCname() {
        return cname;
    }

    public int getId() {
        return id;
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
            if (taskType.getId() == (id)) {
                return taskType;
            }
        }
        return null;
    }

}
