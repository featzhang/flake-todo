package com.github.zuofengzhang.flake.client.entity;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;

import java.io.Serializable;

/**
 * @author averyzhang
 * @date 2021/2/25
 */
public class DailyTaskDto implements Serializable {
    private final SimpleIntegerProperty dailyTaskId;
    private final SimpleIntegerProperty taskId;
    private final SimpleIntegerProperty storeStatus;
    private final SimpleIntegerProperty dayId;
    private final SimpleIntegerProperty typeId;
    private final SimpleLongProperty    createTime;
    private final SimpleLongProperty    updateTime;
    private final SimpleStringProperty  notes;

    public DailyTaskDto() {
        dailyTaskId = new SimpleIntegerProperty();
        taskId      = new SimpleIntegerProperty();
        storeStatus = new SimpleIntegerProperty();
        dayId       = new SimpleIntegerProperty();
        typeId      = new SimpleIntegerProperty();
        createTime  = new SimpleLongProperty();
        updateTime  = new SimpleLongProperty();
        notes       = new SimpleStringProperty();
    }

    public DailyTaskDto(Builder builder) {
        this();

        dailyTaskId.set(builder.dailyTaskId);
        taskId.set(builder.taskId);
        storeStatus.set(builder.storeStatus);
        dayId.set(builder.dayId);
        typeId.set(builder.typeId);
        createTime.set(builder.createTime);
        updateTime.set(builder.updateTime);
        notes.set(builder.notes);
    }

    public SimpleIntegerProperty dailyTaskIdProperty() {
        return dailyTaskId;
    }

    public SimpleIntegerProperty taskIdProperty() {
        return taskId;
    }

    public SimpleIntegerProperty storeStatusProperty() {
        return storeStatus;
    }

    public SimpleIntegerProperty dayIdProperty() {
        return dayId;
    }

    public SimpleIntegerProperty typeIdProperty() {
        return typeId;
    }

    public SimpleLongProperty createTimeProperty() {
        return createTime;
    }

    public SimpleLongProperty updateTimeProperty() {
        return updateTime;
    }


    public Integer getDailyTaskId() {
        return dailyTaskId.get();
    }

    public Integer getTaskId() {
        return taskId.get();
    }

    public Integer getStoreStatus() {
        return storeStatus.get();
    }

    public Integer getDayId() {
        return dayId.get();
    }

    public Integer getTypeId() {
        return typeId.get();
    }

    public Long getCreateTime() {
        return createTime.get();
    }

    public Long getUpdateTime() {
        return updateTime.get();
    }

    public String getNotes() {
        return notes.get();
    }

    public void setDailyTaskId(Integer dailyTaskId) {
        this.dailyTaskId.set(dailyTaskId);
    }

    public void setTaskId(Integer taskId) {
        this.taskId.set(taskId);
    }

    public void setStoreStatus(Integer storeStatus) {
        this.storeStatus.set(storeStatus);
    }

    public void setDayId(Integer dayId) {
        this.dayId.set(dayId);
    }

    public void setTypeId(Integer typeId) {
        this.typeId.set(typeId);
    }

    public void setCreateTime(Long createTime) {
        this.createTime.set(createTime);
    }

    public void setUpdateTime(Long updateTime) {
        this.updateTime.set(updateTime);
    }


    public static class Builder {
        private Integer dailyTaskId;
        private Integer taskId;
        private Integer storeStatus;
        private Integer dayId;
        private Integer typeId;
        private Long    createTime;
        private Long    updateTime;
        private String  notes;

        public Builder dailyTaskId(Integer d) {
            this.dailyTaskId = d;
            return this;
        }

        public Builder taskId(Integer d) {
            this.taskId = d;
            return this;
        }

        public Builder storeStatus(Integer d) {
            this.storeStatus = d;
            return this;
        }

        public Builder dayId(Integer d) {
            this.dayId = d;
            return this;
        }

        public Builder typeId(Integer d) {
            this.typeId = d;
            return this;
        }

        public Builder createTime(Long d) {
            this.createTime = d;
            return this;
        }

        public Builder updateTime(Long d) {
            this.updateTime = d;
            return this;
        }

        public Builder notes(String notes) {
            this.notes = notes;
            return this;
        }

        public DailyTaskDto build() {
            return new DailyTaskDto(this);
        }
    }

    public static Builder builder() {
        return new Builder();
    }

    public static DailyTaskDto parse(DailyTaskDo di) {
        return DailyTaskDto.builder()
                .dailyTaskId(di.getDailyTaskId())
                .dayId(di.getDayId())
                .createTime(di.getCreateTime())
                .updateTime(di.getUpdateTime())
                .storeStatus(di.getStoreStatus())
                .notes(di.getNotes())
                .typeId(di.getTypeId())
                .taskId(di.getTaskId())
                .build();
    }

    public DailyTaskDo parse() {
        return DailyTaskDo.builder()
                .dailyTaskId(dailyTaskId.get())
                .taskId(taskId.get())
                .storeStatus(storeStatus.get())
                .dayId(dayId.get())
                .typeId(typeId.get())
                .createTime(createTime.get())
                .updateTime(updateTime.get())
                .notes(notes.get())
                .build();
    }
}
