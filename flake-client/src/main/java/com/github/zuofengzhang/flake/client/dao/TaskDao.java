package com.github.zuofengzhang.flake.client.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.github.zuofengzhang.flake.client.entity.dos.TaskDo;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author averyzhang
 */
@Mapper
public interface TaskDao extends BaseMapper<TaskDo> {
}
