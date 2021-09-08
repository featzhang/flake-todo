package com.github.zuofengzhang.flake.client.constraints;

import com.github.zuofengzhang.flake.client.utils.ResourceUtil;

public class FlakeLabel {
    public static final String application_name           = label("application_name");
    public static final String CURRENT_DAY                = label("label_current_day");
    public static final String WORKING                    = label("label_working");
    public static final String FOCUS                      = label("label_focus");
    public static final String BREAKING                   = label("label_breaking");
    public static final String TIME_TO_WEAK               = label("label_time_to_weak");
    public static final String TASK_EDIT                  = label("label_task_edit");
    public static final String CREATE                     = label("label_create");
    public static final String LAST_UPDATE                = label("label_last_update");
    public static final String FINISH                     = label("label_finish");
    public static final String IMPORTANCE_URGENCY         = label("label_importance_urgency");
    public static final String IMPORTANCE_BUT_NOT_URGENCY = label("label_importance_but_not_urgency");
    public static final String NOT_IMPORTANCE_BUT_URGENCY = label("label_not_importance_but_urgency");
    public static final String NOT_IMPORTANCE_NOT_URGENCY = label("label_not_importance_not_urgency");

    public static String label(String s) {
        return ResourceUtil.label(s);
    }
}
