package com.github.zuofengzhang.flake.client.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.github.zuofengzhang.flake.client.entity.dos.DailyTaskDo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author averyzhang
 */
@Mapper
public interface DailyTaskDao extends BaseMapper<DailyTaskDo> {

    @Select("SELECT daily_task_id,       task_id,       type_id,       create_time,\n"
            + "       update_time,       store_status,       day_id,       notes\n"
            + "  FROM daily_task where task_id=#{taskId}")
    List<DailyTaskDo> getListByTaskId(int taskId);

}
