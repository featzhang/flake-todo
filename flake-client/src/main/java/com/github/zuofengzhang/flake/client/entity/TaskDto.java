package com.github.zuofengzhang.flake.client.entity;

import javafx.beans.property.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @author zhangzuofeng1
 */
public class TaskDto {
    private static final Logger log = LoggerFactory.getLogger(TaskDto.class);
    private final SimpleIntegerProperty taskIdProperty = new SimpleIntegerProperty();
    private final SimpleIntegerProperty dayIdProperty = new SimpleIntegerProperty();
    private final SimpleLongProperty createdTimeProperty = new SimpleLongProperty();
    private final SimpleLongProperty updateTimeProperty = new SimpleLongProperty();
    private final SimpleLongProperty startTimeProperty = new SimpleLongProperty();
    private final SimpleLongProperty endTimeProperty = new SimpleLongProperty();
    private final SimpleStringProperty titleProperty = new SimpleStringProperty();
    private final SimpleStringProperty contentProperty = new SimpleStringProperty();
    private final SimpleBooleanProperty finishedProperty = new SimpleBooleanProperty(false);
    private final SimpleObjectProperty<TaskType> taskTypeProperty = new SimpleObjectProperty<>();
    private final SimpleIntegerProperty importanceUrgencyAxisProperty = new SimpleIntegerProperty(0);
    private final SimpleLongProperty priorityOrderProperty = new SimpleLongProperty(0);
    private final SimpleStringProperty attachmentProperty = new SimpleStringProperty();
    private final SimpleObjectProperty<StoreStatus> storeStatusProperty = new SimpleObjectProperty<>(StoreStatus.YES);


    public SimpleIntegerProperty taskIdProperty() {
        return taskIdProperty;
    }


    public SimpleIntegerProperty dayIdProperty() {
        return dayIdProperty;
    }


    public SimpleLongProperty createdTimeProperty() {
        return createdTimeProperty;
    }


    public SimpleLongProperty updateTimeProperty() {
        return updateTimeProperty;
    }


    public SimpleLongProperty startTimeProperty() {
        return startTimeProperty;
    }


    public SimpleLongProperty endTimeProperty() {
        return endTimeProperty;
    }


    public SimpleStringProperty titleProperty() {
        return titleProperty;
    }

    public String getContent() {
        return contentProperty.get();
    }

    public SimpleStringProperty contentProperty() {
        return contentProperty;
    }


    public SimpleBooleanProperty finishedProperty() {
        return finishedProperty;
    }


    public SimpleObjectProperty<TaskType> taskTypeProperty() {
        return taskTypeProperty;
    }

    public SimpleObjectProperty<StoreStatus> storeStatusProperty() {
        return storeStatusProperty;
    }

    public StoreStatus getStoreStatus() {
        return storeStatusProperty.get();
    }

    public void setStoreStatus(StoreStatus storeStatus) {
        this.storeStatusProperty.set(storeStatus);
    }

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
        this.taskTypeProperty.set(builder.taskType);
        this.importanceUrgencyAxisProperty.set(builder.importanceUrgencyAxis);
        this.attachmentProperty.set(builder.attachment);
        this.storeStatusProperty.set(builder.storeStatus);
        this.priorityOrderProperty.set(builder.priorityOrder);
    }

    public int getIua() {
        return importanceUrgencyAxisProperty.get();
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


    public int getDayId() {
        return dayIdProperty.get();
    }

    public boolean isFinished() {
        return finishedProperty.get();
    }

    @Override
    public String toString() {
        return "TaskDto{" +
                "taskId=" + taskIdProperty.get() +
                ", dayId=" + dayIdProperty.get() +
                ", createdTime=" + createdTimeProperty.get() +
                ", updateTime=" + updateTimeProperty.get() +
                ", startTime=" + startTimeProperty.get() +
                ", endTime=" + endTimeProperty.get() +
                ", title=" + titleProperty.get() +
                ", content=" + contentProperty.get() +
                ", finished=" + finishedProperty.get() +
                ", taskType=" + taskTypeProperty.get() +
                ", iua=" + importanceUrgencyAxisProperty.get() +
                ", attachment=" + attachmentProperty.get() +
                ", priorityOrder=" + priorityOrderProperty.get() +
                '}';
    }

    public void setTaskId(int insert) {
        this.taskIdProperty.set(insert);
    }

    public void setIua(int targetIuaId) {
        this.importanceUrgencyAxisProperty.set(targetIuaId);
    }

    public SimpleIntegerProperty iuaProperty() {
        return importanceUrgencyAxisProperty;
    }

    public Long getCreatedTime() {
        return createdTimeProperty.get();
    }

    public long getUpdateTime() {
        return updateTimeProperty.get();
    }

    public long getEndTime() {
        return endTimeProperty.get();
    }

    public SimpleStringProperty attachmentProperty() {
        return attachmentProperty;
    }

    public String getAttachment() {
        return attachmentProperty.get();
    }

    public void setContent(String text) {
        this.contentProperty.set(text);
    }

    public void setPriorityOrder(long l) {
        this.priorityOrderProperty.set(l);
    }

    public long getPriorityOrder() {
        return priorityOrderProperty.get();
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
        private TaskType taskType;
        private int importanceUrgencyAxis;
        private String attachment;
        private StoreStatus storeStatus;
        private long priorityOrder;

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

        public Builder importanceUrgencyAxis(int finished) {
            this.importanceUrgencyAxis = finished;
            return this;
        }

        public Builder attachment(String finished) {
            this.attachment = finished;
            return this;
        }

        public Builder storeStatus(StoreStatus storeStatus) {
            this.storeStatus = storeStatus;
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

        public Builder priorityOrder(Long priorityOrder) {
            this.priorityOrder = priorityOrder;
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
                .importanceUrgencyAxis(taskDo.getImportanceUrgencyAxis())
                .attachment(taskDo.getAttachment())
                .storeStatus(StoreStatus.findByCode(taskDo.getStoreStatus()))
                .priorityOrder(taskDo.getPriorityOrder())
                .build();
    }

    public TaskDo parse() {
        return TaskDo.builder()
                .taskId(taskIdProperty.get())
                .typeId(taskTypeProperty.get().getCId())
                .content(contentProperty.get())
                .title(titleProperty.get())
                .dayId(dayIdProperty.get())
                .createTime(createdTimeProperty.get())
                .updateTime(updateTimeProperty.get())
                .startTime(startTimeProperty.get())
                .endTime(endTimeProperty.get())
                .finished(finishedProperty.get())
                .importanceUrgencyAxis(importanceUrgencyAxisProperty.get())
                .attachment(attachmentProperty.get())
                .storeStatus(storeStatusProperty.get().getCode())
                .priorityOrder(priorityOrderProperty.get())
                .build();
    }
}
