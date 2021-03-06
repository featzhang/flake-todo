package com.github.zuofengzhang.flake.client.entity.dto;

import com.github.zuofengzhang.flake.client.entity.TaskType;
import com.github.zuofengzhang.flake.client.entity.dos.DailyTaskDo;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * @author averyzhang
 * @date 2021/2/25
 */
public class DailyTaskDto {
    private final SimpleIntegerProperty          dailyTaskId;
    private final SimpleIntegerProperty          taskId;
    private final SimpleIntegerProperty          storeStatus;
    private final SimpleIntegerProperty          dayId;
    private final SimpleObjectProperty<TaskType> taskType;
    private final SimpleLongProperty             createTime;
    private final SimpleLongProperty             updateTime;
    private final SimpleStringProperty           notes;
    private final SimpleIntegerProperty          iua;
    private final SimpleIntegerProperty          dailyOrder;

    public DailyTaskDto() {
        dailyTaskId = new SimpleIntegerProperty();
        taskId      = new SimpleIntegerProperty();
        storeStatus = new SimpleIntegerProperty();
        dayId       = new SimpleIntegerProperty();
        taskType    = new SimpleObjectProperty<>();
        createTime  = new SimpleLongProperty();
        updateTime  = new SimpleLongProperty();
        notes       = new SimpleStringProperty();
        iua         = new SimpleIntegerProperty();
        dailyOrder  = new SimpleIntegerProperty();
    }

    public DailyTaskDto(Builder builder) {
        this();

        dailyTaskId.set(builder.dailyTaskId);
        taskId.set(builder.taskId);
        storeStatus.set(builder.storeStatus);
        dayId.set(builder.dayId);
        taskType.set(builder.taskType);
        createTime.set(builder.createTime);
        updateTime.set(builder.updateTime);
        notes.set(builder.notes);
        iua.set(builder.iua);
        dailyOrder.set(builder.dailyOrder);
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

    public SimpleLongProperty createTimeProperty() {
        return createTime;
    }

    public SimpleLongProperty updateTimeProperty() {
        return updateTime;
    }

    public SimpleIntegerProperty iuaProperty() {
        return iua;
    }

    public SimpleIntegerProperty dailyOrderProperty() {
        return dailyOrder;
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

    public Long getCreateTime() {
        return createTime.get();
    }

    public Long getUpdateTime() {
        return updateTime.get();
    }

    public String getNotes() {
        return notes.get();
    }

    public int getIua() {
        return iua.get();
    }

    public int getDailyOrder() {
        return dailyOrder.get();
    }

    public TaskType getTaskType() {
        return taskType.get();
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

    public void setTaskType(TaskType typeId) {
        this.taskType.set(typeId);
    }

    public void setCreateTime(Long createTime) {
        this.createTime.set(createTime);
    }

    public void setUpdateTime(Long updateTime) {
        this.updateTime.set(updateTime);
    }

    public void setIua(int iua) {
        this.iua.set(iua);
    }

    public void setDailyOrder(int dailyOrder) {
        this.dailyOrder.set(dailyOrder);
    }

    public static class Builder {
        private Integer  dailyTaskId;
        private Integer  taskId;
        private Integer  storeStatus;
        private Integer  dayId;
        private TaskType taskType;
        private Long     createTime;
        private Long     updateTime;
        private String   notes;
        private Integer  iua;
        private Integer  dailyOrder;

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

        public Builder iua(int iua) {
            this.iua = iua;
            return this;
        }

        public Builder dailyOrder(int iua) {
            this.dailyOrder = iua;
            return this;
        }

        public Builder taskType(TaskType type) {
            this.taskType = type;
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
        return DailyTaskDto.builder().dailyTaskId(di.getDailyTaskId()).dayId(di.getDayId())
                .createTime(di.getCreateTime()).updateTime(di.getUpdateTime()).storeStatus(di.getStoreStatus())
                .notes(di.getNotes()).taskType(TaskType.findById(di.getTypeId())).taskId(di.getTaskId())
                .iua(di.getIua()).dailyOrder(di.getDailyOrder()).build();
    }

    public DailyTaskDo parse() {
        return DailyTaskDo.builder().dailyTaskId(dailyTaskId.get()).taskId(taskId.get()).storeStatus(storeStatus.get())
                .dayId(dayId.get()).typeId(taskType.get().getCode()).createTime(createTime.get())
                .updateTime(updateTime.get()).notes(notes.get()).iua(iua.get()).dailyOrder(dailyOrder.get()).build();
    }
}
