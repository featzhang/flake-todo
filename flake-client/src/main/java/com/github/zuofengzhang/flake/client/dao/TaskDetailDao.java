package com.github.zuofengzhang.flake.client.dao;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.github.zuofengzhang.flake.client.entity.dto.TaskDetailDto;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @author averyzhang
 */
@Mapper
public interface TaskDetailDao extends BaseMapper<TaskDetailDto> {

        @Select("select  task_id,title,content,create_time,update_time,start_time,"
                        + "end_time,finished,priority_order,importance_urgency_axis,attachment,"
                        + "store_status from task ${ew.customSqlSegment} ")
        @Results({ @Result(column = "task_id", property = "taskId", id = true),
                        @Result(column = "title", property = "title"),
                        @Result(column = "content", property = "content"),
                        @Result(column = "create_time", property = "createTime"),
                        @Result(column = "update_time", property = "updateTime"),
                        @Result(column = "start_time", property = "startTime"),
                        @Result(column = "end_time", property = "endTime"),
                        @Result(column = "finished", property = "finished"),
                        @Result(column = "priority_order", property = "priorityOrder"),
                        @Result(column = "importance_urgency_axis", property = "importanceUrgencyAxis"),
                        @Result(column = "attachment", property = "attachment"),
                        @Result(column = "store_status", property = "storeStatus"),
                        @Result(column = "task_id", property = "dailyTasks", many = @Many(select = "com.github.zuofengzhang.flake.client.dao.DailyTaskDao.getListByTaskId")) })
        List<TaskDetailDto> getList(@Param(Constants.WRAPPER) QueryWrapper<TaskDetailDto> wrapper);

}
