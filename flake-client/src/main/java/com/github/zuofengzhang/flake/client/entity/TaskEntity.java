package com.github.zuofengzhang.flake.client.entity;

import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;

@Data
@Builder
@Slf4j
public class TaskEntity implements Serializable {
    private long createdTime;
    private long updateTime;
    private String title;
    private String content;
    private TaskType taskType;
    private long startTime;
    private long endTime;
    private boolean fullTomato;
}
