package com.github.zuofengzhang.flake.client.service.impl;

import com.github.zuofengzhang.flake.client.service.MessageService;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

@Component
public class MessageServiceImpl implements MessageService {
    private final List<Consumer<String>> consumerList = new ArrayList<>(1);

    @Override
    public void addConsumer(Consumer<String> consumer) {
        this.consumerList.add(consumer);
    }

    @Override
    public void sendMessage(String message) {
        for (Consumer<String> stringConsumer : this.consumerList) {
            stringConsumer.accept(message);
        }
    }
}