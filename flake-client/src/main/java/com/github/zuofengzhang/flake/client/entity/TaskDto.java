package com.github.zuofengzhang.flake.client.entity;

import javafx.beans.property.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;

/**
 * @author zhangzuofeng1
 */
public class TaskDto implements Cloneable {
    private static final Logger log = LoggerFactory.getLogger(TaskDto.class);
    private final SimpleIntegerProperty taskId = new SimpleIntegerProperty();
    private final SimpleIntegerProperty dayId = new SimpleIntegerProperty();
    private final SimpleLongProperty createdTime = new SimpleLongProperty();
    private final SimpleLongProperty updateTime = new SimpleLongProperty();
    private final SimpleLongProperty startTime = new SimpleLongProperty();
    private final SimpleLongProperty endTime = new SimpleLongProperty();
    private final SimpleStringProperty title = new SimpleStringProperty();
    private final SimpleStringProperty content = new SimpleStringProperty();
    private final SimpleBooleanProperty finished = new SimpleBooleanProperty(false);
    private final SimpleObjectProperty<TaskType> taskType = new SimpleObjectProperty<>();
    private final SimpleIntegerProperty importanceUrgencyAxis = new SimpleIntegerProperty(0);
    private final SimpleLongProperty priorityOrder = new SimpleLongProperty(0);
    private final SimpleStringProperty attachment = new SimpleStringProperty();
    private final SimpleObjectProperty<StoreStatus> storeStatus = new SimpleObjectProperty<>(StoreStatus.YES);
    private final SimpleIntegerProperty percent = new SimpleIntegerProperty(0);
    private final SimpleIntegerProperty expirationDay = new SimpleIntegerProperty(0);
    private final SimpleIntegerProperty expirationTime = new SimpleIntegerProperty(0);
    private final SimpleObjectProperty<REPETITION_TYPE> repetition = new SimpleObjectProperty<>(REPETITION_TYPE.NONE);

    public TaskDto() {
    }

    public long getStartTime() {
        return startTime.get();
    }

    public void setStartTime(long startTime) {
        this.startTime.setValue(startTime);
    }

    public void setEndTime(long endTime) {
        this.endTime.setValue(endTime);
    }

    public void setFinished(Boolean finished) {
        this.finished.setValue(finished);
    }
    public void setImportanceUrgencyAxis(Integer importanceUrgencyAxis) {
        this.importanceUrgencyAxis.setValue(importanceUrgencyAxis);
    }

    public Integer getImportanceUrgencyAxis() {
        return importanceUrgencyAxis.getValue();
    }

    public void setAttachment(String arrayAttachment) {
        this.attachment.setValue(arrayAttachment);
    }

    public void setPercent(Integer percent) {
        this.percent.setValue(percent);
    }

    public void setRepetition(REPETITION_TYPE repetition) {
        this.repetition.setValue(repetition);
    }

    private TaskDto(Builder builder) {
        this.taskId.set(builder.taskId);
        this.dayId.set(builder.dayId);
        this.createdTime.set(builder.createdTime);
        this.updateTime.set(builder.updateTime);
        this.startTime.set(builder.startTime);
        this.endTime.set(builder.endTime);
        this.title.set(builder.title);
        this.content.set(builder.content);
        this.finished.set(builder.finished);
        this.taskType.set(builder.taskType);
        this.importanceUrgencyAxis.set(builder.importanceUrgencyAxis);
        this.attachment.set(builder.attachment);
        this.storeStatus.set(builder.storeStatus);
        this.priorityOrder.set(builder.priorityOrder);
        //
        this.percent.set(builder.percent);
        this.expirationDay.set(builder.expirationDay);
        this.expirationTime.set(builder.expirationTime);
        this.repetition.set(builder.repetition);
    }

    public static Builder builder() {
        return new Builder();
    }

    public static TaskDto parse(TaskDo taskDo) {
        return TaskDto.builder().taskId(taskDo.getTaskId()).taskType(TaskType.findById(taskDo.getTypeId()))
                .content(taskDo.getContent()).title(taskDo.getTitle()).dayId(taskDo.getDayId())
                .createdTime(taskDo.getCreateTime()).updateTime(taskDo.getUpdateTime()).startTime(taskDo.getStartTime())
                .endTime(taskDo.getEndTime()).finished(taskDo.getFinished())
                .importanceUrgencyAxis(taskDo.getImportanceUrgencyAxis()).attachment(taskDo.getAttachment())
                .storeStatus(StoreStatus.findByCode(taskDo.getStoreStatus())).priorityOrder(taskDo.getPriorityOrder())
                .percent(taskDo.getPercent()).expirationDay(taskDo.getExpirationDay())
                .expirationTime(taskDo.getExpirationTime()).repetition(REPETITION_TYPE.valueOf(taskDo.getRepetition()))
                .build();
    }

    public SimpleIntegerProperty taskIdProperty() {
        return taskId;
    }

    public SimpleIntegerProperty dayIdProperty() {
        return dayId;
    }

    public SimpleLongProperty createdTimeProperty() {
        return createdTime;
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

    public SimpleStringProperty titleProperty() {
        return title;
    }

    public String getContent() {
        return content.get();
    }

    public void setContent(String text) {
        this.content.set(text);
    }

    public SimpleStringProperty contentProperty() {
        return content;
    }

    public SimpleBooleanProperty finishedProperty() {
        return finished;
    }

    public SimpleObjectProperty<TaskType> taskTypeProperty() {
        return taskType;
    }

    public SimpleObjectProperty<StoreStatus> storeStatusProperty() {
        return storeStatus;
    }

    public StoreStatus getStoreStatus() {
        return storeStatus.get();
    }

    public void setStoreStatus(StoreStatus storeStatus) {
        this.storeStatus.set(storeStatus);
    }

    public int getIua() {
        return importanceUrgencyAxis.get();
    }

    public void setIua(int targetIuaId) {
        this.importanceUrgencyAxis.set(targetIuaId);
    }

    public int getTaskId() {
        return taskId.get();
    }

    public void setTaskId(int insert) {
        this.taskId.set(insert);
    }

    public void setCreatedTime(Long createdTime) {
        this.createdTime.setValue(createdTime);
    }

    public void setUpdateTime(Long updatedTime) {
        this.updateTime.setValue(updatedTime);
    }

    public TaskType getTaskType() {
        return taskType.get();
    }

    public void setTaskType(TaskType taskType) {
        this.taskType.set(taskType);
    }

    public String getTitle() {
        return title.get();
    }

    public void setTitle(String title) {
        this.title.set(title);
    }

    public int getDayId() {
        return dayId.get();
    }

    public Boolean isFinished() {
        return finished.get();
    }

    @Override
    public String toString() {
        return "TaskDto{" +
                "taskId=" + taskId +
                ", dayId=" + dayId +
                ", createdTime=" + createdTime +
                ", updateTime=" + updateTime +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", title=" + title +
                ", content=" + content +
                ", finished=" + finished +
                ", taskType=" + taskType +
                ", importanceUrgencyAxis=" + importanceUrgencyAxis +
                ", priorityOrder=" + priorityOrder +
                ", attachment=" + attachment +
                ", storeStatus=" + storeStatus +
                ", percent=" + percent +
                ", expirationDay=" + expirationDay +
                ", expirationTime=" + expirationTime +
                ", repetition=" + repetition +
                '}';
    }

    public SimpleIntegerProperty iuaProperty() {
        return importanceUrgencyAxis;
    }

    public Long getCreatedTime() {
        return createdTime.get();
    }

    public long getUpdateTime() {
        return updateTime.get();
    }

    public long getEndTime() {
        return endTime.get();
    }

    public SimpleStringProperty attachmentProperty() {
        return attachment;
    }

    public String getAttachment() {
        return attachment.get();
    }

    public long getPriorityOrder() {
        return priorityOrder.get();
    }

    public void setPriorityOrder(long l) {
        this.priorityOrder.set(l);
    }

    public int getPercent() {
        return percent.get();
    }

    public Integer getExpirationDay() {
        return expirationDay.get();
    }

    public void setExpirationDay(int value) {
        this.expirationDay.set(value);
    }

    public Integer getExpirationTime() {
        return expirationTime.get();
    }

    public void setExpirationTime(int value) {
        this.expirationTime.set(value);
    }

    public REPETITION_TYPE getRepetition() {
        return repetition.get();
    }

    public SimpleIntegerProperty percentProperty() {
        return percent;
    }

    public SimpleIntegerProperty expirationDayProperty() {
        return expirationDay;
    }

    public SimpleIntegerProperty expirationTimeProperty() {
        return expirationTime;
    }

    public SimpleObjectProperty<REPETITION_TYPE> repetitionProperty() {
        return repetition;
    }


    public void setDayId(Integer dayId) {
        this.dayId.setValue(dayId);
    }

    @Override
    public TaskDto clone() {
        TaskDto taskDto = new TaskDto();
        BeanUtils.copyProperties(this, taskDto);
        return taskDto;
    }

    public void copy(TaskDto detailViewTaskDto) {
        BeanUtils.copyProperties(detailViewTaskDto, this);
    }

    public TaskDo parse() {
        return TaskDo.builder().taskId(taskId.get()).typeId(taskType.get().getCId())
                .content(content.get()).title(title.get()).dayId(dayId.get())
                .createTime(createdTime.get()).updateTime(updateTime.get())
                .startTime(startTime.get()).endTime(endTime.get()).finished(finished.get())
                .importanceUrgencyAxis(importanceUrgencyAxis.get()).attachment(attachment.get())
                .storeStatus(storeStatus.get().getCode()).priorityOrder(priorityOrder.get())
                .percent(percent.get()).expirationDay(expirationDay.get()).expirationTime(expirationTime.get())
                .repetition(repetition.get().name()).build();
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
        //
        private int percent = 0;
        private int expirationTime = 0;
        private int expirationDay = 0;
        private REPETITION_TYPE repetition = REPETITION_TYPE.NONE;

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

        public Builder percent(int percent) {
            this.percent = percent;
            return this;
        }

        public Builder expirationTime(int expirationTime) {
            this.expirationTime = expirationTime;
            return this;
        }

        public Builder expirationDay(int expirationDay) {
            this.expirationDay = expirationDay;
            return this;
        }

        public Builder repetition(REPETITION_TYPE repetition) {
            this.repetition = repetition;
            return this;
        }
    }


}
