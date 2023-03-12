package com.github.featzhang.flake.client.utils;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.function.BiConsumer;

import static com.github.featzhang.flake.client.consts.FlakeConst.DB_PATH;

@Slf4j
public class DbChecker {
    private static final String SCHEMA_SQL_FILE_NAME = "/sql/schema.sql";
    private static final String DB_URL = "jdbc:sqlite:" + DB_PATH;
    private static final String DRIVER_NAME = "org.sqlite.JDBC";


    public static void checkAndCreate() {
        // check database if exist
        createDatabasePathIfAbsent();
        // check table if exist
        if (!tableExists("task")) {
            executeInitStatement();
        }
        log.info("Check finished.");
    }

    private static void executeInitStatement() {
        ResourceUtil.loadFile(SCHEMA_SQL_FILE_NAME).ifPresent(sql -> {
            log.info("Init db ...");
            sqlExecute((connection, statement) -> {
                try {
                    statement.execute(sql);
                } catch (SQLException e) {
                    log.error("Can not properly execute init statement.", e);
                }
            });
        });
    }

    private static boolean tableExists(String tableName) {
        boolean[] flag = {false};
        sqlExecute((connection, statement) -> {
            try (ResultSet resultSet = connection.getMetaData().getTables(null, null, tableName, null)) {
                flag[0] = resultSet.next();
            } catch (SQLException e) {
                log.error("Can not properly query table meta!", e);
            }

        });
        return flag[0];
    }

    private static void sqlExecute(BiConsumer<Connection, Statement> consumer) {
        Connection conn = null;
        Statement stmt = null;
        try {
            Class.forName(DRIVER_NAME);

            log.info("Connecting db: {} ...", DB_URL);
            conn = DriverManager.getConnection(DB_URL);

            log.info("Init statement ...");
            stmt = conn.createStatement();
            consumer.accept(conn, stmt);
            log.info("Execute success.");
        } catch (Exception se) {
            log.error("Can not properly execute !", se);
        } finally {
            try {
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException ignored) {
            }
        }
    }


    private static void createDatabasePathIfAbsent() {
        log.info("Check database if exist ...");
        if (DB_PATH.contains("/")) {
            String parentPath = DB_PATH.substring(0, DB_PATH.lastIndexOf("/"));
            Path path = Paths.get(parentPath);
            if (!Files.exists(path)) {
                log.info("The path of database not exist, creating ...");
                try {
                    Files.createDirectories(path);
                } catch (IOException e) {
                    log.error("Can not properly create database path!", e);
                }
            }
        }
    }
}

