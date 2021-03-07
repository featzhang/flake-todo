package com.github.zuofengzhang.flake.client.entity.dto;

import com.github.zuofengzhang.flake.client.entity.StoreStatus;
import com.sun.javafx.collections.ObservableListWrapper;
import javafx.beans.property.*;

import java.util.List;

/**
 * @author averyzhang
 * @date 2021/2/26
 */
public class TaskDetailDto {
    private final SimpleIntegerProperty             taskId;
    private final SimpleStringProperty              title;
    private final SimpleStringProperty              content;
    private final SimpleLongProperty                createTime;
    private final SimpleLongProperty                updateTime;
    private final SimpleLongProperty                startTime;
    private final SimpleLongProperty                endTime;
    private final SimpleBooleanProperty             finished;
    private final SimpleStringProperty              attachment;
    private final SimpleObjectProperty<StoreStatus> storeStatus;
    private final SimpleIntegerProperty             taskOrder;
    private final SimpleListProperty<DailyTaskDto>  dailyTasks;

    public TaskDetailDto() {
        taskId      = new SimpleIntegerProperty();
        title       = new SimpleStringProperty();
        content     = new SimpleStringProperty();
        createTime  = new SimpleLongProperty();
        updateTime  = new SimpleLongProperty();
        startTime   = new SimpleLongProperty();
        endTime     = new SimpleLongProperty();
        finished    = new SimpleBooleanProperty();
        attachment  = new SimpleStringProperty();
        storeStatus = new SimpleObjectProperty<>();
        taskOrder   = new SimpleIntegerProperty();
        dailyTasks  = new SimpleListProperty<>();
    }

    public SimpleIntegerProperty taskIdProperty() {
        return taskId;
    }

    public SimpleStringProperty titleProperty() {
        return title;
    }

    public SimpleStringProperty contentProperty() {
        return content;
    }

    public SimpleLongProperty createTimeProperty() {
        return createTime;
    }

    public SimpleLongProperty updateTimeProperty() {
        return updateTime;
    }

    public SimpleLongProperty startTimeProperty() {
        return startTime;
    }

    public SimpleLongProperty endTimeProperty() {
        return endTime;
    }

    public SimpleBooleanProperty finishedProperty() {
        return finished;
    }

    public SimpleStringProperty attachmentProperty() {
        return attachment;
    }

    public SimpleObjectProperty<StoreStatus> storeStatusProperty() {
        return storeStatus;
    }

    public SimpleIntegerProperty taskOrderProperty() {
        return taskOrder;
    }

    public SimpleListProperty<DailyTaskDto> dailyTasksProperty() {
        return dailyTasks;
    }

    public Integer getTaskId() {
        return this.taskId.get();
    }

    public String getTitle() {
        return this.title.get();
    }

    public String getContent() {
        return this.content.get();
    }

    public Long getCreateTime() {
        return this.createTime.get();
    }

    public Long getUpdateTime() {
        return this.updateTime.get();
    }

    public Long getStartTime() {
        return this.startTime.get();
    }

    public Long getEndTime() {
        return this.endTime.get();
    }

    public Boolean getFinished() {
        return this.finished.get();
    }

    public String getAttachment() {
        return this.attachment.get();
    }

    public StoreStatus getStoreStatus() {
        return this.storeStatus.get();
    }

    public Integer getTaskOrder() {
        return this.taskOrder.get();
    }

    public List<DailyTaskDto> getDailyTasks() {
        return this.dailyTasks.get();
    }

    public void setTaskId(Integer taskId) {
        this.taskId.set(taskId);
    }

    public void setTitle(String title) {
        this.title.setValue(title);
    }

    public void setContent(String content) {
        this.content.set(content);
    }

    public void setCreateTime(Long createTime) {
        this.createTime.set(createTime);
    }

    public void setUpdateTime(Long updateTime) {
        this.updateTime.set(updateTime);
    }

    public void setStartTime(Long startTime) {
        this.startTime.set(startTime);
    }

    public void setEndTime(Long endTime) {
        this.endTime.set(endTime);
    }

    public void setFinished(Boolean finished) {
        this.finished.set(finished);
    }

    public void setAttachment(String attachment) {
        this.attachment.set(attachment);
    }


    public void setTaskOrder(Integer taskOrder) {
        this.taskOrder.set(taskOrder);
    }

    public void setDailyTasks(List<DailyTaskDto> dailyTasks) {
        this.dailyTasks.set(new ObservableListWrapper<>(dailyTasks));
    }

    public void setStoreStatus(int storeStatus) {
        this.storeStatus.set(StoreStatus.findByCode(storeStatus));
    }
}
