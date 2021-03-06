package com.github.zuofengzhang.flake.client.entity.dos;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import com.github.zuofengzhang.flake.client.entity.dos.DailyTaskDo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Builder
@Data
@Slf4j
@AllArgsConstructor
public class SingleDailyTaskDo implements Serializable {

    public SingleDailyTaskDo() {
    }

    @TableId(type = IdType.AUTO)
    private Integer dailyTaskId;
    private Long taskCreateTime;
    private Long taskUpdateTime;
    private String notes;
    protected Integer taskId;
    protected String title;
    protected String content;
    protected Integer typeId;
    protected Long createTime;
    protected Long updateTime;
    protected Long startTime;
    protected Long endTime;
    protected Boolean finished;
    protected Integer iua;
    protected Integer dayId;
    protected String attachment;
    protected Integer storeStatus;
    private Integer taskOrder;
    private Integer dailyOrder;
}
