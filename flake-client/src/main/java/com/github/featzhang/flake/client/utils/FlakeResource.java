package com.github.featzhang.flake.client.utils;

import com.github.featzhang.flake.client.common.EncodableResourceControl;

import java.nio.charset.StandardCharsets;
import java.util.ResourceBundle;

public class FlakeResource {
    private static final ResourceBundle resourceBundle;

    static {
        resourceBundle = ResourceBundle.getBundle("flake-client", new EncodableResourceControl(StandardCharsets.UTF_8));
    }

    public static String label(String key) {
        return resourceBundle.getString(key);
    }

}
