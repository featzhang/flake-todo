package com.github.zuofengzhang.flake.client.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author averyzhang
 * @date 2021/2/26
 */
@Data
public class TaskWithDailyDo implements Serializable {
    @TableId(type = IdType.AUTO)
    private Integer           taskId;
    private String            title;
    private String            content;
    private Integer           typeId;
    private Long              createTime;
    private Long              updateTime;
    private Long              startTime;
    private Long              endTime;
    private Boolean           finished;
    /**
     *
     */
    private Long              priorityOrder;
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
    private Integer           importanceUrgencyAxis;
    private Integer           dayId;
    private String            attachment;
    private Integer           storeStatus;
    @TableField(exist = false)
    private List<DailyTaskDo> dailyTasks;
}
