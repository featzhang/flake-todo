package com.github.featzhang.flake.client.utils;

import java.util.ResourceBundle;

public class FlakeResource {
    private static final ResourceBundle resourceBundle;

    static {
        resourceBundle = ResourceBundle.getBundle("flake-client");
    }

    public static String label(String key) {
        return resourceBundle.getString(key);
    }

}
