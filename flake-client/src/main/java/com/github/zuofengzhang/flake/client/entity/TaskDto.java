package com.github.zuofengzhang.flake.client.entity;

import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;

@Data
@Builder
@Slf4j
public class TaskDto implements Serializable {
    private int taskId;
    private long createdTime;
    private long updateTime;
    private String title;
    private String content;
    private TaskType taskType;
    private long startTime;
    private long endTime;
    private boolean fullTomato;
    private int dayId;

    public static TaskDto parse(TaskDo taskDo) {
        return TaskDto.builder()
                .taskId(taskDo.getTaskId())
                .taskType(TaskType.findById(taskDo.getTypeId()))
                .content(taskDo.getContent())
                .title(taskDo.getTitle())
                .dayId(taskDo.getDayId())
                .createdTime(taskDo.getCreateTime())
                .updateTime(taskDo.getUpdateTime())
                .startTime(taskDo.getStartTime())
                .endTime(taskDo.getEndTime())
                .build();
    }

    public TaskDo parse() {
        return TaskDo.builder()
                .taskId(taskId)
                .typeId(taskType.getCId())
                .content(content)
                .title(title)
                .dayId(dayId)
                .createTime(createdTime)
                .updateTime(updateTime)
                .startTime(startTime)
                .endTime(endTime)
                .build();
    }
}
