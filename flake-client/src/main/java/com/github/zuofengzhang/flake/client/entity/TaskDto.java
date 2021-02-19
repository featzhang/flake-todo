package com.github.zuofengzhang.flake.client.entity;

import javafx.beans.property.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @author zhangzuofeng1
 */
public class TaskDto {
    private static final Logger log = LoggerFactory.getLogger(TaskDto.class);
    private SimpleIntegerProperty taskIdProperty = new SimpleIntegerProperty();
    private SimpleIntegerProperty dayIdProperty = new SimpleIntegerProperty();
    private SimpleLongProperty createdTimeProperty = new SimpleLongProperty();
    private SimpleLongProperty updateTimeProperty = new SimpleLongProperty();
    private SimpleLongProperty startTimeProperty = new SimpleLongProperty();
    private SimpleLongProperty endTimeProperty = new SimpleLongProperty();
    private SimpleStringProperty titleProperty = new SimpleStringProperty();
    private SimpleStringProperty contentProperty = new SimpleStringProperty();
    private SimpleBooleanProperty finishedProperty = new SimpleBooleanProperty(false);
    private SimpleBooleanProperty fullTomatoProperty = new SimpleBooleanProperty(false);
    private SimpleObjectProperty<TaskType> taskTypeProperty = new SimpleObjectProperty<>();

    public static Builder builder() {
        return new Builder();
    }

    private TaskDto(Builder builder) {
        this.taskIdProperty.set(builder.taskId);
        this.dayIdProperty.set(builder.dayId);
        this.createdTimeProperty.set(builder.createdTime);
        this.updateTimeProperty.set(builder.updateTime);
        this.startTimeProperty.set(builder.startTime);
        this.endTimeProperty.set(builder.endTime);
        this.titleProperty.set(builder.title);
        this.contentProperty.set(builder.content);
        this.finishedProperty.set(builder.finished);
        this.fullTomatoProperty.set(builder.fullTomato);
        this.taskTypeProperty.set(builder.taskType);
    }

    public void setTaskType(TaskType taskType) {
        this.taskTypeProperty.set(taskType);
    }

    public int getTaskId() {
        return taskIdProperty.get();
    }

    public TaskType getTaskType() {
        return taskTypeProperty.get();
    }

    public String getTitle() {
        return titleProperty.get();
    }

    public String getContent() {
        return contentProperty.get();
    }

    public int getDayId() {
        return dayIdProperty.get();
    }

    public boolean isFullTomato() {
        return finishedProperty.get();
    }

    public static class Builder {
        private int taskId;
        private int dayId;
        private long createdTime;
        private long updateTime;
        private long startTime;
        private long endTime;
        private String title;
        private String content;
        private boolean finished;
        private boolean fullTomato;
        private TaskType taskType;

        public Builder taskId(int taskId) {
            this.taskId = taskId;
            return this;
        }

        public Builder taskType(TaskType taskType) {
            this.taskType = taskType;
            return this;
        }

        public Builder content(String content) {
            this.content = content;
            return this;
        }

        public Builder title(String title) {
            this.title = title;
            return this;
        }

        public Builder dayId(int dayId) {
            this.dayId = dayId;
            return this;
        }

        public Builder createdTime(long createdTime) {
            this.createdTime = createdTime;
            return this;
        }

        public Builder updateTime(long updateTime) {
            this.updateTime = updateTime;
            return this;
        }

        public Builder startTime(long startTime) {
            this.startTime = startTime;
            return this;
        }

        public Builder endTime(long endTime) {
            this.endTime = endTime;
            return this;
        }

        public Builder finished(Boolean finished) {
            if (finished != null) {
                this.finished = finished;
            }
            return this;
        }

        public TaskDto build() {
            return new TaskDto(this);
        }

        public Builder fullTomato(boolean b) {
            // FIXME
            this.finished = b;
            return this;
        }
    }

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
                .finished(taskDo.getFinished())
                .build();
    }

    public TaskDo parse() {
        return TaskDo.builder().taskId(taskIdProperty.get()).typeId(taskTypeProperty.get().getCId()).content(contentProperty.get()).title(titleProperty.get()).dayId(dayIdProperty.get())
                .createTime(createdTimeProperty.get()).updateTime(updateTimeProperty.get()).startTime(startTimeProperty.get()).endTime(endTimeProperty.get()).build();
    }
}
