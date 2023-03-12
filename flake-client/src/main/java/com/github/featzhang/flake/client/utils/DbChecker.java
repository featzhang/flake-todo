package com.github.featzhang.flake.client.utils;

import com.github.featzhang.flake.client.consts.FlakeConsts;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;

import static com.github.featzhang.flake.client.consts.FlakeConsts.DB_PATH;

public class DbChecker {
    private static final String SCHEMA_SQL_FILE_NAME = "/sql/schema.sql";
    private static final String DB_URL = "jdbc:sqlite:" + DB_PATH;
    private static final String DRIVER_NAME = "org.sqlite.JDBC";

    private String loadSchema() {
        Optional<String> sqlOptional = ResourceUtil.loadFile(SCHEMA_SQL_FILE_NAME);
    }


    public static void checkAndCreate() {
        // check database if exist
        if (DB_PATH.contains("/")) {
            String parentPath = DB_PATH.substring(0, DB_PATH.lastIndexOf("/"));
            Path path = Paths.get(parentPath);
            if (!Files.exists(path)) {
                Files.createDirectories(path);
            }
        }


        // check table if exist


        Connection conn = null;
        Statement stmt = null;
        try {
            // 注册 JDBC 驱动器
            Class.forName(DRIVER_NAME);

            // 打开一个连接
            System.out.println("连接到数据库...");
            conn = DriverManager.getConnection(DB_URL);

            // 执行查询
            System.out.println("实例化Statement对象...");
            stmt = conn.createStatement();
            String sql = "CREATE TABLE IF NOT EXISTS Employees " +
                    "(id INTEGER not NULL, " +
                    " age INTEGER not NULL, " +
                    " first VARCHAR(255), " +
                    " last VARCHAR(255), " +
                    " PRIMARY KEY ( id ))";
            stmt.executeUpdate(sql);
            System.out.println("创建表成功...");
        } catch (SQLException se) {
            // 处理 JDBC 错误
            se.printStackTrace();
        } catch (Exception e) {
            // 处理 Class.forName 错误
            e.printStackTrace();
        } finally {
            // 关闭资源
            try {
                if (stmt != null)
                    stmt.close();
            } catch (SQLException se2) {
            }
            try {
                if (conn != null)
                    conn.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
        System.out.println("Goodbye!");
    }
}

