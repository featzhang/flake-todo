package com.github.zuofengzhang.flake.client.dao;

import java.util.List;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.github.zuofengzhang.flake.client.entity.dos.SingleDailyTaskDo;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

/**
 * @author averyzhang
 */
@Mapper
public interface SingleTaskDao extends BaseMapper<SingleDailyTaskDo> {

        @Select("SELECT dt.daily_task_id,dt.task_id, t.create_time AS task_create_time,dt.day_id,t.update_time AS task_update_time,"
                        + "t.title, t.content, t.start_time, t.end_time, daily_order,task_order, iua, finished, "
                        + "attachment, dt.store_status, parent_task_id, dt.type_id, dt.create_time, dt.update_time, dt.notes "
                        + "FROM task t LEFT JOIN daily_task dt ON t.task_id = dt.task_id ${ew.customSqlSegment}")
        @Results({ @Result(column = "daily_Task_Id", property = "dailyTaskId", id = true),
                        @Result(column = "task_Create_Time", property = "taskCreateTime"),
                        @Result(column = "task_Update_Time", property = "taskUpdateTime"),
                        @Result(column = "notes", property = "notes"), @Result(column = "taskId", property = "taskId"),
                        @Result(column = "title", property = "title"),
                        @Result(column = "content", property = "content"),
                        @Result(column = "type_Id", property = "typeId"),
                        @Result(column = "task_id", property = "taskId"),
                        @Result(column = "create_Time", property = "createTime"),
                        @Result(column = "update_Time", property = "updateTime"),
                        @Result(column = "start_Time", property = "startTime"),
                        @Result(column = "end_Time", property = "endTime"),
                        @Result(column = "finished", property = "finished"),
                        @Result(column = "daily_order", property = "dailyOrder"),
                        @Result(column = "task_order", property = "taskOrder"),
                        @Result(column = "iua", property = "iua"), @Result(column = "day_Id", property = "dayId"),
                        @Result(column = "attachment", property = "attachment"),
                        @Result(column = "store_Status", property = "storeStatus"), })
        List<SingleDailyTaskDo> getList(@Param(Constants.WRAPPER) QueryWrapper<SingleDailyTaskDo> wrapper);

}
