package com.github.zuofengzhang.flake.client.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;

/**
 * @author averyzhang
 * @date 2021/2/25
 */
@Data
@Slf4j
@Builder
@TableName("daily_task")
public class DailyTaskDo implements Serializable {
    private Integer dailyTaskId;
    private Integer taskId;
    private Integer storeStatus;
    private Integer dayId;
    private Integer typeId;
    private Long createTime;
    private Long updateTime;
    private String notes;
}
