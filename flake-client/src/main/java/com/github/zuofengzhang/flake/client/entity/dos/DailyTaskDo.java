package com.github.zuofengzhang.flake.client.entity.dos;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author averyzhang
 * @date 2021/2/25
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("daily_task")
public class DailyTaskDo implements Serializable {

    @TableId(type = IdType.AUTO)
    private Integer dailyTaskId;
    private Integer taskId;
    private Integer storeStatus;
    private Integer dayId;
    private Integer typeId;
    private Long createTime;
    private Long updateTime;
    private String notes;
    /**
     * <url>https://corporatefinanceinstitute.com/resources/uncategorized/eisenhower-matrix/<url/>
     * The Eisenhower Matrix – also known as the Eisenhower Decision Matrix, the
     * Eisenhower Important/Urgent Matrix, or simply as the Important/Urgent Matrix
     * – is a graphical tool used to prioritize tasks by ranking them on two key
     * attributes: Importance and Urgency. The Eisenhower Matrix was derived from a
     * quote attributed to former U.S. leader Dwight D. Eisenhower. 1. Important but
     * Not Urgent Tasks 2. Important and Urgent Tasks 3. Not Important and Not
     * Urgent Tasks 4. Not Important but Urgent Tasks 0. default_value
     */
    protected Integer iua;
    private Integer dailyOrder;
}
