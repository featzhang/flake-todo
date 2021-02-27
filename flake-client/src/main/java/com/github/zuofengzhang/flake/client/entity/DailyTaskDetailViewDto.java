package com.github.zuofengzhang.flake.client.entity;

/**
 * @author averyzhang
 * @date 2021/2/26
 */
public class DailyTaskDetailViewDto extends DailyTaskDetailViewDo {


    public static DailyTaskDetailViewDto parse(TaskDto taskDto, DailyTaskDto dailyTaskDto) {
        return (DailyTaskDetailViewDto) DailyTaskDetailViewDto.builder()
                .typeId(dailyTaskDto.getTypeId())
                .dailyTaskId(dailyTaskDto.getTaskId())
                .dayId(dailyTaskDto.getDayId())
                .attachment(taskDto.getAttachment())
                .content(taskDto.getContent())
                .createTime(dailyTaskDto.getCreateTime())
                .importanceUrgencyAxis(taskDto.getIua())
                .notes(dailyTaskDto.getNotes())
                .taskEndTime(taskDto.getEndTime())
                .priorityOrder(taskDto.getPriorityOrder())
                .taskCreateTime(taskDto.getCreatedTime())
                .taskFinished(taskDto.isFinished())
                .storeStatus(dailyTaskDto.getStoreStatus())
                .taskId(dailyTaskDto.getTaskId())
                .taskStartTime(taskDto.getStartTime())
                .taskStoreStatus(taskDto.getStoreStatus().getCode())
                .taskUpdateTime(taskDto.getUpdateTime())
                .title(taskDto.getTitle())
                .updateTime(dailyTaskDto.getUpdateTime())
                .build();
    }
}
