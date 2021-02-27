package com.github.zuofengzhang.flake.client.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;

/**
 * @author averyzhang
 * @date 2021/2/26
 */
@Data
@Slf4j
@Builder
@NoArgsConstructor
@TableName("daily_task_detail_view")
public class DailyTaskDetailViewDo implements Serializable {
    private Integer dailyTaskId;
    private Integer taskId;
    private Integer storeStatus;
    private Integer dayId;
    private Integer typeId;
    private String  notes;
    private Long    createTime;
    private Long    updateTime;
    private Long    taskCreateTime;
    private Long    taskUpdateTime;
    private Long    taskStartTime;
    private Long    taskEndTime;
    private String  title;
    private String  content;
    private String  attachment;
    private Long priorityOrder;
    private Integer importanceUrgencyAxis;
    private Boolean taskFinished;
    private Integer taskStoreStatus;
}
