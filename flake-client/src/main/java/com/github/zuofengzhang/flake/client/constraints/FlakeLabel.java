package com.github.zuofengzhang.flake.client.constraints;

import static com.github.zuofengzhang.flake.client.utils.ResourceUtil.label;

public class FlakeLabel {
    public static final String application_name = label("application_name");
    public static final String CURRENT_DAY      = label("current_day");
    public static final String WORKING          = label("working");
    public static final String FOCUS            = label("focus");
    public static final String BREAKING         = label("breaking");
    public static final String TIME_TO_WEAK     = label("time_to_weak");
    public static final String TASK_EDIT        = label("task_edit");
    public static final String CREATE           = label("create");
    public static final String LAST_UPDATE      = label("last_update");
    public static final String FINISH           = label("finish");

    // iua
    public static final String IMPORTANCE_URGENCY         = label("importance_urgency");
    public static final String IMPORTANCE_BUT_NOT_URGENCY = label("importance_but_not_urgency");
    public static final String NOT_IMPORTANCE_BUT_URGENCY = label("not_importance_but_urgency");
    public static final String NOT_IMPORTANCE_NOT_URGENCY = label("not_importance_not_urgency");

    //
    public static final String SETTING = label("setting");

    //
    public static final String TODAY_PLAN       = label("today_plan");
    public static final String TOMATO_POTATO    = label("tomato_potato");
    public static final String TODAY_SUMMARY    = label("today_summary");
    public static final String YESTERDAY_REVIEW = label("yesterday_review");
    // iua

    //    menu
    public static final String MENU_MOVE_TO                 = label("menu_move_to");
    public static final String MENU_IMPORTANCE_URGENCY_AXIS = label("menu_importance_urgency_axis");

    // menu order
    public static final String MENU_ORDER     = label("menu_order");
    public static final String MENU_MOVE_TOP  = label("menu_move_top");
    public static final String MENU_MOVE_UP   = label("menu_move_up");
    public static final String MENU_MOVE_DOWN = label("menu_move_down");

    // menu delete or recovery
    public static final String MENU_DELETE_RECOVERY = label("menu_delete_undelete");
    public static final String MENU_DELETE          = label("menu_delete");
    public static final String MENU_RECOVERY        = label("menu_undelete");

    // menu tools
    public static final String MENU_TOOLS  = label("menu_tools");
    public static final String MENU_SEARCH = label("menu_search");


    // repetition
    public static final String REPETITION_NONE                 = label("repetition_none");
    public static final String REPETITION_EVERY_DAY            = label("repetition_every_day");
    public static final String REPETITION_EVERY_WEEK           = label("repetition_every_week");
    public static final String REPETITION_EVERY_MONTH          = label("repetition_every_month");
    public static final String REPETITION_EVERY_YEAR           = label("repetition_every_year");
    public static final String REPETITION_EVERY_LUNAR_YEAR     = label("repetition_every_lunar_year");
    public static final String REPETITION_LEGAL_WORKDAY        = label("repetition_legal_workday");
    public static final String REPETITION_EBBINGHAUS_MNEMONICS = label("repetition_ebbinghaus_mnemonics");


}
