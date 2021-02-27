package com.github.zuofengzhang.flake.client.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.github.zuofengzhang.flake.client.entity.DailyTaskDetailViewDo;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author averyzhang
 * @date 2021/2/26
 */
@Mapper
public interface DailyTaskDetailViewDao extends BaseMapper<DailyTaskDetailViewDo> {
}
