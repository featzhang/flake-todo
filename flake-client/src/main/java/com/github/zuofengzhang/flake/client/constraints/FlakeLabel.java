package com.github.zuofengzhang.flake.client.constraints;

import com.github.zuofengzhang.flake.client.utils.ResourceUtil;

public class FlakeLabel {
    public static final String application_name = ResourceUtil.label("application_name");
    public static final String CURRENT_DAY = ResourceUtil.label("label_current_day");
    public static final String WORKING = ResourceUtil.label("label_working");
    public static final String FOCUS = ResourceUtil.label("label_focus");
    public static final String BREAKING = ResourceUtil.label("label_breaking");
    public static final String TIME_TO_WEAK = ResourceUtil.label("label_time_to_weak");
    public static final String TASK_EDIT = ResourceUtil.label("label_task_edit");
    public static final String CREATE = ResourceUtil.label("label_create");
    public static final String LAST_UPDATE = ResourceUtil.label("label_last_update");
    public static final String FINISH = ResourceUtil.label("label_finish");
}
