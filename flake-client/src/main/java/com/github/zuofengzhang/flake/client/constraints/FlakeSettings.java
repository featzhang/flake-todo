package com.github.zuofengzhang.flake.client.constraints;

import java.util.concurrent.TimeUnit;
import java.util.prefs.Preferences;

/**
 * @author zhangzuofeng1
 */
public class FlakeSettings {

    private static final String FOCUS_TIME_IN_SECS = "focus.time.in.secs";
    private static final String NAP_TIME_IN_SECS = "nap.time.in.secs";
    private static final long DEFAULT_FOCUS_IN_SECONDS = TimeUnit.MINUTES.toSeconds(30);
    private static final long DEFAULT_NAP_TIME_IN_SECONDS = TimeUnit.MINUTES.toSeconds(30);
    private static boolean show_deleted_task = false;

    private static final FlakeSettings SETTINGS;

    static {
        SETTINGS = new FlakeSettings();
    }

    public static FlakeSettings getInstance() {
        return SETTINGS;
    }

    private final Preferences preferences;

    private FlakeSettings() {
        this.preferences = Preferences.userNodeForPackage(FlakeSettings.class);
    }

    public long getFocusTimeInSeconds() {
        return preferences.getLong(FOCUS_TIME_IN_SECS, DEFAULT_FOCUS_IN_SECONDS);
    }

    public void setFocusTimeInSecs(long s) {
        this.preferences.putLong(FOCUS_TIME_IN_SECS, s);
    }

    public long getNapTimeInSeconds() {
        return preferences.getLong(NAP_TIME_IN_SECS, DEFAULT_NAP_TIME_IN_SECONDS);
    }

    public void setNapTimeInSecs(long s) {
        this.preferences.putLong(NAP_TIME_IN_SECS, s);
    }

    public void setShowDeletedTask(boolean showDeletedTask) {
        show_deleted_task = showDeletedTask;
    }

    public boolean getShowDeletedTask() {
        return show_deleted_task;
    }

}
