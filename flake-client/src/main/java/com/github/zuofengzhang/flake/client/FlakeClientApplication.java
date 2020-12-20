package com.github.zuofengzhang.flake.client;

import javafx.application.Application;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class FlakeClientApplication {
    public static void main(String[] args) {
        Application.launch(FlackClientDashboard.class, args);
    }
}
