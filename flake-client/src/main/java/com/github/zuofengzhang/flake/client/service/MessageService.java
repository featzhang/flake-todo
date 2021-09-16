package com.github.zuofengzhang.flake.client.service;

import java.util.function.Consumer;

public interface MessageService {

    void addConsumer(Consumer<String> consumer);

    void sendMessage(String message);
}
