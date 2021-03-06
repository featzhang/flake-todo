package com.github.zuofengzhang.flake.client.entity.dos;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * @author zhangzuofeng
 */
@Data
@Builder
@Slf4j
@TableName("task")
public class TaskDo implements Serializable {
    @TableId(type = IdType.AUTO)
    protected Integer taskId;
    protected String title;
    protected String content;
    @TableField(fill = FieldFill.INSERT)
    protected Long createTime;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    protected Long updateTime;
    protected Long startTime;
    protected Long endTime;
    protected Boolean finished;
    protected String attachment;
    protected Integer storeStatus;
    private Integer taskOrder;

}
