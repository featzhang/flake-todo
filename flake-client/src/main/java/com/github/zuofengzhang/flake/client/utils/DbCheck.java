package com.github.zuofengzhang.flake.client.utils;

import com.github.zuofengzhang.flake.client.constraints.FlakeConsts;
import com.google.common.base.Joiner;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.*;
import java.util.List;

import static com.github.zuofengzhang.flake.client.constraints.FlakeConsts.SQL_SCHEMA_PATH;

@Slf4j
public class DbCheck {

    public static void check() {
        Path path = Paths.get(FlakeConsts.DB_FILE_PATH);
        File file = path.toFile();
        if (!file.exists() || file.length() == 0) {
            log.warn("file: {} is not exist!", file.getPath());
            File parentFile = file.getParentFile();
            if (!parentFile.exists()) {
                parentFile.mkdirs();
                log.warn("parent file: {} is not exist! now create.", parentFile.getPath());
            }
            try {
                file.createNewFile();
            } catch (IOException e) {
                log.error("create new file error.", e);
            }
            try {
                createDb();
            } catch (ClassNotFoundException | SQLException | IOException e) {
                log.error("create db error.", e);
            }
        }
    }

    private static void createDb() throws ClassNotFoundException, SQLException, IOException {
        Class.forName("org.sqlite.JDBC");
        String url = "jdbc:sqlite:" + FlakeConsts.DB_FILE_PATH;
        log.info("url: {}.", url);

        Connection   connection  = DriverManager.getConnection(url);
        InputStream  inputStream = DbCheck.class.getClassLoader().getResourceAsStream(SQL_SCHEMA_PATH);
        List<String> lines       = IOUtils.readLines(inputStream, "UTF-8");
        String       sql         = Joiner.on("\n").join(lines);
        log.info("sql: {}.", sql);


        Statement preparedStatement = connection.createStatement();
        preparedStatement.executeUpdate(sql);

        preparedStatement.close();
        connection.close();
        log.info("close connection.");
    }
}
