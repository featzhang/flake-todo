package com.github.zuofengzhang.flake.client.entity.dto;

import com.github.zuofengzhang.flake.client.entity.StoreStatus;
import com.github.zuofengzhang.flake.client.entity.TaskType;
import com.github.zuofengzhang.flake.client.entity.dos.SingleDailyTaskDo;
import javafx.beans.property.*;

public class SingleDailyTaskDto {
    private final SimpleBooleanProperty             finished;
    private final SimpleIntegerProperty             dailyTaskId;
    private final SimpleIntegerProperty             dayId;
    private final SimpleIntegerProperty             iua;
    private final SimpleObjectProperty<StoreStatus> storeStatus;
    private final SimpleIntegerProperty             taskId;
    private final SimpleIntegerProperty             taskOrder;
    private final SimpleIntegerProperty             dailyOrder;
    private final SimpleObjectProperty<TaskType>    taskType;
    private final SimpleLongProperty                createTime;
    private final SimpleLongProperty                endTime;
    private final SimpleLongProperty                priorityOrder;
    private final SimpleLongProperty                startTime;
    private final SimpleLongProperty                taskCreateTime;
    private final SimpleLongProperty                taskUpdateTime;
    private final SimpleLongProperty                updateTime;
    private final SimpleStringProperty              attachment;
    private final SimpleStringProperty              content;
    private final SimpleStringProperty              notes;
    private final SimpleStringProperty              title;

    public SingleDailyTaskDto() {
        finished       = new SimpleBooleanProperty();
        dailyTaskId    = new SimpleIntegerProperty();
        dayId          = new SimpleIntegerProperty();
        iua            = new SimpleIntegerProperty();
        storeStatus    = new SimpleObjectProperty<>();
        taskId         = new SimpleIntegerProperty();
        taskOrder      = new SimpleIntegerProperty();
        dailyOrder     = new SimpleIntegerProperty();
        taskType       = new SimpleObjectProperty<>();
        createTime     = new SimpleLongProperty();
        endTime        = new SimpleLongProperty();
        priorityOrder  = new SimpleLongProperty();
        startTime      = new SimpleLongProperty();
        taskCreateTime = new SimpleLongProperty();
        taskUpdateTime = new SimpleLongProperty();
        updateTime     = new SimpleLongProperty();
        attachment     = new SimpleStringProperty();
        content        = new SimpleStringProperty();
        notes          = new SimpleStringProperty();
        title          = new SimpleStringProperty();
    }

    public SimpleBooleanProperty finishedProperty() {
        return finished;
    }

    public SimpleIntegerProperty dailyTaskIdProperty() {
        return dailyTaskId;
    }

    public SimpleIntegerProperty dayIdProperty() {
        return dayId;
    }

    public SimpleIntegerProperty importanceUrgencyAxisProperty() {
        return iua;
    }

    public SimpleObjectProperty<StoreStatus> storeStatusProperty() {
        return storeStatus;
    }

    public SimpleIntegerProperty taskIdProperty() {
        return taskId;
    }

    public SimpleObjectProperty<TaskType> taskTypeProperty() {
        return taskType;
    }

    public SimpleLongProperty createTimeProperty() {
        return createTime;
    }

    public SimpleLongProperty endTimeProperty() {
        return endTime;
    }

    public SimpleLongProperty priorityOrderProperty() {
        return priorityOrder;
    }

    public SimpleLongProperty startTimeProperty() {
        return startTime;
    }

    public SimpleLongProperty taskCreateTimeProperty() {
        return taskCreateTime;
    }

    public SimpleLongProperty taskUpdateTimeProperty() {
        return taskUpdateTime;
    }

    public SimpleLongProperty updateTimeProperty() {
        return updateTime;
    }

    public SimpleStringProperty attachmentProperty() {
        return attachment;
    }

    public SimpleStringProperty contentProperty() {
        return content;
    }

    public SimpleStringProperty notesProperty() {
        return notes;
    }

    public SimpleStringProperty titleProperty() {
        return title;
    }

    public SimpleIntegerProperty taskOrderProperty() {
        return taskOrder;
    }

    public SimpleIntegerProperty dailyOrderProperty() {
        return dailyOrder;
    }

    public void setFinished(boolean finished) {
        this.finished.set(finished);
    }

    public void setDailyTaskId(int dailyTaskId) {
        this.dailyTaskId.set(dailyTaskId);
    }

    public void setDayId(int dayId) {
        this.dayId.set(dayId);
    }

    public void setIua(int importanceUrgencyAxis) {
        this.iua.set(importanceUrgencyAxis);
    }

    public void setStoreStatus(StoreStatus storeStatus) {
        this.storeStatus.set(storeStatus);
    }

    public void setTaskId(int taskId) {
        this.taskId.set(taskId);
    }

    public void setTaskType(TaskType typeId) {
        this.taskType.set(typeId);
    }

    public void setCreateTime(long createTime) {
        this.createTime.set(createTime);
    }

    public void setEndTime(long endTime) {
        this.endTime.set(endTime);
    }

    public void setPriorityOrder(long priorityOrder) {
        this.priorityOrder.set(priorityOrder);
    }

    public void setStartTime(long startTime) {
        this.startTime.set(startTime);
    }

    public void setTaskCreateTime(long taskCreateTime) {
        this.taskCreateTime.set(taskCreateTime);
    }

    public void setTaskUpdateTime(long taskUpdateTime) {
        this.taskUpdateTime.set(taskUpdateTime);
    }

    public void setUpdateTime(long updateTime) {
        this.updateTime.set(updateTime);
    }

    public void setAttachment(String attachment) {
        this.attachment.set(attachment);
    }

    public void setContent(String content) {
        this.content.set(content);
    }

    public void setNotes(String notes) {
        this.notes.set(notes);
    }

    public void setTitle(String title) {
        this.title.set(title);
    }

    public void setTaskOrder(int taskOrder) {
        this.taskOrder.set(taskOrder);
    }

    public void setDailyOrder(int dailyOrder) {
        this.dailyOrder.set(dailyOrder);
    }

    public boolean getFinished() {
        return finished.get();
    }

    public int getDailyTaskId() {
        return dailyTaskId.get();
    }

    public int getDayId() {
        return dayId.get();
    }

    public int getImportanceUrgencyAxis() {
        return iua.get();
    }

    public StoreStatus getStoreStatus() {
        return storeStatus.get();
    }

    public int getTaskId() {
        return taskId.get();
    }

    public TaskType getTaskType() {
        return taskType.get();
    }

    public long getCreateTime() {
        return createTime.get();
    }

    public long getEndTime() {
        return endTime.get();
    }

    public long getPriorityOrder() {
        return priorityOrder.get();
    }

    public long getStartTime() {
        return startTime.get();
    }

    public long getTaskCreateTime() {
        return taskCreateTime.get();
    }

    public long getTaskUpdateTime() {
        return taskUpdateTime.get();
    }

    public long getUpdateTime() {
        return updateTime.get();
    }

    public String getAttachment() {
        return attachment.get();
    }

    public String getContent() {
        return content.get();
    }

    public String getNotes() {
        return notes.get();
    }

    public String getTitle() {
        return title.get();
    }

    public int getDailyOrder() {
        return dailyOrder.get();
    }

    public int getTaskOrder() {
        return taskOrder.get();
    }

    public static SingleDailyTaskDto parse(SingleDailyTaskDo singleTaskDo) {
        SingleDailyTaskDto dto = new SingleDailyTaskDto();
        dto.setFinished(singleTaskDo.getFinished());
        dto.setDailyTaskId(singleTaskDo.getDailyTaskId());
        dto.setDayId(singleTaskDo.getDayId());
        dto.setIua(singleTaskDo.getIua());
        dto.setStoreStatus(StoreStatus.findByCode(singleTaskDo.getStoreStatus()));
        dto.setTaskId(singleTaskDo.getTaskId());
        dto.setTaskType(TaskType.findById(singleTaskDo.getTypeId()));
        dto.setCreateTime(singleTaskDo.getCreateTime());
        Long endTime2 = singleTaskDo.getEndTime();
        dto.setEndTime(endTime2 == null ? 0 : endTime2);
        Long startTime2 = singleTaskDo.getStartTime();
        dto.setStartTime(startTime2 == null ? 0 : startTime2);
        dto.setTaskCreateTime(singleTaskDo.getTaskCreateTime());
        dto.setTaskUpdateTime(singleTaskDo.getTaskUpdateTime());
        dto.setUpdateTime(singleTaskDo.getUpdateTime());
        dto.setAttachment(singleTaskDo.getAttachment());
        dto.setContent(singleTaskDo.getContent());
        dto.setNotes(singleTaskDo.getNotes());
        dto.setTitle(singleTaskDo.getTitle());
        dto.setTaskOrder(singleTaskDo.getTaskOrder());
        dto.setDailyOrder(singleTaskDo.getDailyOrder());
        return dto;
    }

    public SingleDailyTaskDo parse() {
        SingleDailyTaskDo taskDo = new SingleDailyTaskDo();
        taskDo.setFinished(this.getFinished());
        taskDo.setDailyTaskId(this.getDailyTaskId());
        taskDo.setDayId(this.getDayId());
        taskDo.setIua(this.getImportanceUrgencyAxis());
        taskDo.setStoreStatus(this.getStoreStatus().getCode());
        taskDo.setTaskId(this.getTaskId());
        taskDo.setTypeId(this.getTaskType().getCId());
        taskDo.setCreateTime(this.getCreateTime());
        taskDo.setEndTime(this.getEndTime());
        taskDo.setStartTime(this.getStartTime());
        taskDo.setTaskCreateTime(this.getTaskCreateTime());
        taskDo.setTaskUpdateTime(this.getTaskUpdateTime());
        taskDo.setUpdateTime(this.getUpdateTime());
        taskDo.setAttachment(this.getAttachment());
        taskDo.setContent(this.getContent());
        taskDo.setNotes(this.getNotes());
        taskDo.setTitle(this.getTitle());
        taskDo.setDailyOrder(this.getDailyOrder());
        taskDo.setTaskOrder(this.getTaskOrder());
        return taskDo;
    }

    public int getIua() {
        return importanceUrgencyAxisProperty().get();
    }

    public SimpleIntegerProperty iuaProperty() {
        return importanceUrgencyAxisProperty();
    }

    public TaskDto toTaskDto() {
        return TaskDto.builder().taskId(taskId.get()).attachment(attachment.get()).content(content.get())
                .createdTime(createTime.get()).endTime(endTime.get()).finished(finished.get())
                .storeStatus(storeStatus.get()).title(title.get()).updateTime(updateTime.get())
                .startTime(startTime.get()).taskOrder(taskOrder.get()).build();
    }

    public DailyTaskDto toDailyTaskDto() {
        return DailyTaskDto.builder().dailyTaskId(dailyTaskId.get()).taskId(taskId.get())
                .storeStatus(storeStatus.get().getCode()).dayId(dayId.get()).taskType(taskType.get())
                .createTime(createTime.get()).updateTime(updateTime.get()).notes(notes.get()).iua(iua.get())
                .dailyOrder(dailyOrder.get()).build();
    }
}
