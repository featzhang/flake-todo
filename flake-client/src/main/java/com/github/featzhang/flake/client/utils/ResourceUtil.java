package com.github.featzhang.flake.client.utils;

import java.util.ResourceBundle;

public class ResourceUtil {
    private static ResourceBundle resourceBundle;

    static {
        resourceBundle = ResourceBundle.getBundle("flake-client");
    }

    public static final String label(String key) {
        return resourceBundle.getString(key);
    }

}
