package com.github.zuofengzhang.flake.client.utils;

import java.util.ResourceBundle;

public class ResourceUtil {
    private static ResourceBundle resourceBundle;


    public static final String label(String key) {
        if (resourceBundle == null) {
            resourceBundle = ResourceBundle.getBundle("flake-client");
        }
        return resourceBundle.getString(key);
    }

}
