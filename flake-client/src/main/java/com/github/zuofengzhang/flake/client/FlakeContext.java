package com.github.zuofengzhang.flake.client;

import com.github.zuofengzhang.flake.client.constraints.FlakeConsts;

public class FlakeContext {
    public static void init() {
        System.setProperty("flake_db_path", FlakeConsts.DB_FILE_PATH);
    }
}
