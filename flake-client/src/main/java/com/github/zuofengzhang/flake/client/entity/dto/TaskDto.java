package com.github.zuofengzhang.flake.client.entity.dto;

import com.github.zuofengzhang.flake.client.entity.StoreStatus;
import com.github.zuofengzhang.flake.client.entity.dos.TaskDo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * @author zhangzuofeng1
 */
public class TaskDto {
    private static final Logger log = LoggerFactory.getLogger(TaskDto.class);
    private final SimpleIntegerProperty taskIdProperty = new SimpleIntegerProperty();
    private final SimpleLongProperty createdTimeProperty = new SimpleLongProperty();
    private final SimpleLongProperty updateTimeProperty = new SimpleLongProperty();
    private final SimpleLongProperty startTimeProperty = new SimpleLongProperty();
    private final SimpleLongProperty endTimeProperty = new SimpleLongProperty();
    private final SimpleStringProperty titleProperty = new SimpleStringProperty();
    private final SimpleStringProperty contentProperty = new SimpleStringProperty();
    private final SimpleBooleanProperty finishedProperty = new SimpleBooleanProperty(false);
    private final SimpleIntegerProperty iuaProperty = new SimpleIntegerProperty(0);
    private final SimpleIntegerProperty taskOrderProperty  = new SimpleIntegerProperty(0);
    private final SimpleStringProperty  attachmentProperty = new SimpleStringProperty();
    private final SimpleObjectProperty<StoreStatus> storeStatusProperty = new SimpleObjectProperty<>(StoreStatus.YES);
    private Long startTime;

    public SimpleIntegerProperty taskIdProperty() {
        return taskIdProperty;
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

    public SimpleIntegerProperty taskOrderProperty() {
        return taskOrderProperty;
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

    public SimpleObjectProperty<StoreStatus> storeStatusProperty() {
        return storeStatusProperty;
    }

    public StoreStatus getStoreStatus() {
        return storeStatusProperty.get();
    }

    public void setStoreStatus(StoreStatus storeStatus) {
        this.storeStatusProperty.set(storeStatus);
    }

    public int getTaskOrder() {
        return this.taskOrderProperty.get();
    }

    public void setTaskOrder(int taskOrder) {
        this.taskOrderProperty.set(taskOrder);
    }

    public static Builder builder() {
        return new Builder();
    }

    private TaskDto(Builder builder) {
        this.taskIdProperty.set(builder.taskId);
        this.createdTimeProperty.set(builder.createdTime);
        this.updateTimeProperty.set(builder.updateTime);
        this.startTimeProperty.set(builder.startTime);
        this.endTimeProperty.set(builder.endTime);
        this.titleProperty.set(builder.title);
        this.contentProperty.set(builder.content);
        this.finishedProperty.set(builder.finished);
        this.attachmentProperty.set(builder.attachment);
        this.storeStatusProperty.set(builder.storeStatus);
        this.taskOrderProperty.set(builder.taskOrder);
    }

    public int getIua() {
        return iuaProperty.get();
    }

    public int getTaskId() {
        return taskIdProperty.get();
    }

    public String getTitle() {
        return titleProperty.get();
    }

    public boolean isFinished() {
        return finishedProperty.get();
    }

    @Override
    public String toString() {
        return "TaskDto{" + "taskId=" + taskIdProperty.get() + ", createdTime=" + createdTimeProperty.get()
                + ", updateTime=" + updateTimeProperty.get() + ", startTime=" + startTimeProperty.get() + ", endTime="
                + endTimeProperty.get() + ", title=" + titleProperty.get() + ", content=" + contentProperty.get()
                + ", finished=" + finishedProperty.get() + ", iua=" + iuaProperty.get() + ", attachment="
                + attachmentProperty.get() + ", priorityOrder=" + taskOrderProperty.get() + '}';
    }

    public void setTaskId(int insert) {
        this.taskIdProperty.set(insert);
    }

    public void setIua(int targetIuaId) {
        this.iuaProperty.set(targetIuaId);
    }

    public SimpleIntegerProperty iuaProperty() {
        return iuaProperty;
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


    public long getPriorityOrder() {
        return taskOrderProperty.get();
    }

    public Long getStartTime() {
        return startTime;
    }

    public void setStartTime(Long startTime) {
        this.startTime = startTime;
    }

    public static class Builder {
        private int taskOrder;
        private int taskId;
        private long createdTime;
        private long updateTime;
        private long startTime;
        private long endTime;
        private String title;
        private String content;
        private boolean finished;
        private String attachment;
        private StoreStatus storeStatus;

        public Builder taskId(int taskId) {
            this.taskId = taskId;
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

        public Builder attachment(String finished) {
            this.attachment = finished;
            return this;
        }

        public Builder storeStatus(StoreStatus storeStatus) {
            this.storeStatus = storeStatus;
            return this;
        }

        public Builder taskOrder(int storeStatus) {
            this.taskOrder = storeStatus;
            return this;
        }

        public TaskDto build() {
            return new TaskDto(this);
        }

    }

    public static TaskDto parse(TaskDo taskDo) {
        return TaskDto.builder().taskId(taskDo.getTaskId()).content(taskDo.getContent()).title(taskDo.getTitle())
                .createdTime(taskDo.getCreateTime()).updateTime(taskDo.getUpdateTime()).startTime(taskDo.getStartTime())
                .endTime(taskDo.getEndTime()).finished(taskDo.getFinished()).attachment(taskDo.getAttachment())
                .storeStatus(StoreStatus.findByCode(taskDo.getStoreStatus())).taskOrder(taskDo.getTaskOrder()).build();
    }

    public TaskDo parse() {
        return TaskDo.builder().taskId(taskIdProperty.get()).content(contentProperty.get()).title(titleProperty.get())
                .createTime(createdTimeProperty.get()).updateTime(updateTimeProperty.get())
                .startTime(startTimeProperty.get()).endTime(endTimeProperty.get()).finished(finishedProperty.get())
                .attachment(attachmentProperty.get()).storeStatus(storeStatusProperty.get().getCode())
                .taskOrder(taskOrderProperty.get()).build();
    }
}
