package com.github.zuofengzhang.flake.client.entity.dto;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.github.zuofengzhang.flake.client.entity.dos.DailyTaskDo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @author averyzhang
 * @date 2021/2/26
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TaskDetailDto implements Serializable {
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

    private List<DailyTaskDo> dailyTasks;

}
