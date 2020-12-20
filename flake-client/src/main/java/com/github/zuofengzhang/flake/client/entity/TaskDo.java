package com.github.zuofengzhang.flake.client.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;

@Data
@Builder
@Slf4j
@TableName("task")
public class TaskDo implements Serializable {
    @TableId(type = IdType.AUTO)
    private Integer taskId;
    private String title;
    private String content;
    private Integer typeId;
    private Long createTime;
    private Long updateTime;
    private Long startTime;
    private Long endTime;
    private int dayId;
}
