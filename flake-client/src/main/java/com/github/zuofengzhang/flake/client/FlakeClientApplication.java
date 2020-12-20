package com.github.zuofengzhang.flake.client;

import javafx.application.Application;
import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.annotation.MapperScans;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.github.zuofengzhang.flake.client.dao")
public class FlakeClientApplication {
    public static void main(String[] args) {
        Application.launch(FlackClientDashboard.class, args);
    }
}
