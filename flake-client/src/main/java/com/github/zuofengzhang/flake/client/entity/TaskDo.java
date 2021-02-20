package com.github.zuofengzhang.flake.client.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;

/**
 * @author zhangzuofeng1
 */
@TableName("task")
public class TaskDo implements Serializable {
    private static final Logger log = LoggerFactory.getLogger(TaskDo.class);
    @TableId(type = IdType.AUTO)
    private Integer taskId;
    private String title;
    private String content;
    private Integer typeId;
    private Long createTime;
    private Long updateTime;
    private Long startTime;
    private Long endTime;
    private Boolean finished;
    /**
     *
     */
    private Long priorityOrder;
    /**
     * <url>https://corporatefinanceinstitute.com/resources/uncategorized/eisenhower-matrix/<url/>
     * The Eisenhower Matrix – also known as the Eisenhower Decision Matrix, the Eisenhower Important/Urgent Matrix,
     * or simply as the Important/Urgent Matrix – is a graphical tool used to prioritize tasks by ranking them on two
     * key attributes: Importance and Urgency. The Eisenhower Matrix was derived from a quote attributed to former U.S.
     * leader Dwight D. Eisenhower.
     * 1. Important but Not Urgent Tasks
     * 2. Important and Urgent Tasks
     * 3. Not Important and Not Urgent Tasks
     * 4. Not Important but Urgent Tasks
     * 0. default_value
     */
    private Integer importanceUrgencyAxis;
    private Integer dayId;

    public static TaskDo.Builder builder() {
        return new Builder();
    }

    public Integer getTaskId() {
        return taskId;
    }

    public void setTaskId(Integer taskId) {
        this.taskId = taskId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getTypeId() {
        return typeId;
    }

    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public Long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Long updateTime) {
        this.updateTime = updateTime;
    }

    public Long getStartTime() {
        return startTime;
    }

    public void setStartTime(Long startTime) {
        this.startTime = startTime;
    }

    public Long getEndTime() {
        return endTime;
    }

    public void setEndTime(Long endTime) {
        this.endTime = endTime;
    }

    public Boolean getFinished() {
        return finished;
    }

    public void setFinished(Boolean finished) {
        this.finished = finished;
    }

    public Long getPriorityOrder() {
        return priorityOrder;
    }

    public void setPriorityOrder(Long priorityOrder) {
        this.priorityOrder = priorityOrder;
    }

    public Integer getImportanceUrgencyAxis() {
        return importanceUrgencyAxis;
    }

    public void setImportanceUrgencyAxis(Integer importanceUrgencyAxis) {
        this.importanceUrgencyAxis = importanceUrgencyAxis;
    }

    public Integer getDayId() {
        return dayId;
    }

    public void setDayId(Integer dayId) {
        this.dayId = dayId;
    }

    public static final class Builder {
        private Integer taskId;
        private String title;
        private String content;
        private Integer typeId;
        private Long createTime;
        private Long updateTime;
        private Long startTime;
        private Long endTime;
        private Boolean finished;
        private Long priorityOrder;
        private Integer importanceUrgencyAxis;
        private Integer dayId;

        private Builder() {
        }

        public static Builder aTaskDo() {
            return new Builder();
        }

        public Builder taskId(Integer taskId) {
            this.taskId = taskId;
            return this;
        }

        public Builder title(String title) {
            this.title = title;
            return this;
        }

        public Builder content(String content) {
            this.content = content;
            return this;
        }

        public Builder typeId(Integer typeId) {
            this.typeId = typeId;
            return this;
        }

        public Builder createTime(Long createTime) {
            this.createTime = createTime;
            return this;
        }

        public Builder updateTime(Long updateTime) {
            this.updateTime = updateTime;
            return this;
        }

        public Builder startTime(Long startTime) {
            this.startTime = startTime;
            return this;
        }

        public Builder endTime(Long endTime) {
            this.endTime = endTime;
            return this;
        }

        public Builder finished(Boolean finished) {
            this.finished = finished;
            return this;
        }

        public Builder priorityOrder(Long priorityOrder) {
            this.priorityOrder = priorityOrder;
            return this;
        }

        public Builder importanceUrgencyAxis(Integer importanceUrgencyAxis) {
            this.importanceUrgencyAxis = importanceUrgencyAxis;
            return this;
        }

        public Builder dayId(Integer dayId) {
            this.dayId = dayId;
            return this;
        }

        public TaskDo build() {
            TaskDo taskDo = new TaskDo();
            taskDo.setTaskId(taskId);
            taskDo.setTitle(title);
            taskDo.setContent(content);
            taskDo.setTypeId(typeId);
            taskDo.setCreateTime(createTime);
            taskDo.setUpdateTime(updateTime);
            taskDo.setStartTime(startTime);
            taskDo.setEndTime(endTime);
            taskDo.setFinished(finished);
            taskDo.setPriorityOrder(priorityOrder);
            taskDo.setImportanceUrgencyAxis(importanceUrgencyAxis);
            taskDo.setDayId(dayId);
            return taskDo;
        }
    }
}
