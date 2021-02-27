package com.github.zuofengzhang.flake.client.constraints;

import com.github.zuofengzhang.flake.client.entity.IUA;
import com.github.zuofengzhang.flake.client.entity.TaskType;

/**
 * @author averyzhang
 * @date 2021/2/26
 */
public class FlakeConstants {
    public static final Long     DEFAULT_PRIORITY_ORDER = 0L;
    public static final IUA      DEFAULT_IUA            = IUA.NOT_IMPORTANCE_NOT_URGENCY;
    public static final TaskType DEFAULT_TASK_TYPE      = TaskType.TODAY_PLAN;
}
