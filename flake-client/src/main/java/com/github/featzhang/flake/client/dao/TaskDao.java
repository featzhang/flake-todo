package com.github.featzhang.flake.client.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.github.featzhang.flake.client.entity.TaskDo;
import org.apache.ibatis.annotations.Mapper;

/**
 */
@Mapper
public interface TaskDao extends BaseMapper<TaskDo> {
}
