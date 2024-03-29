package com.github.featzhang.flake.client.utils;

import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

@Slf4j
public class ResourceUtil {
    public static Optional<String> loadFile(String fileName) {
        InputStream inputStream = ResourceUtil.class.getClassLoader().getResourceAsStream(fileName);
        if (inputStream == null) {
            log.error("file absent :{}", fileName);
            return Optional.empty();
        }
        StringBuilder stringBuilder = new StringBuilder();
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);
            }
        } catch (IOException e) {
            log.error("Can not properly read resource: " + fileName, e);
            return Optional.empty();
        }
        String value = stringBuilder.toString();
        log.info("load file: {}, length: {}", fileName, value.length());
        return Optional.of(value);
    }
}
