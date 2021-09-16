package com.github.zuofengzhang.flake.client.service.impl;

import com.github.zuofengzhang.flake.client.entity.TaskDto;
import com.github.zuofengzhang.flake.client.service.MessageService;
import com.github.zuofengzhang.flake.client.service.TaskService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

@Component
public class IndexSchedulingComponent {
    private static final Logger logger = LoggerFactory.getLogger(IndexSchedulingComponent.class);

    @Resource
    private MessageService messageService;

    @Resource
    private TaskService taskService;

    @Scheduled(initialDelay = 60_000, fixedRate = 3600_000_0)
    public void updateIndex() {
        messageService.sendMessage("begin update index...");
        List<TaskDto> allTasks = taskService.findAllTasks();
        if (allTasks != null && !allTasks.isEmpty()) {
            logger.info("task count: {}.", allTasks.size());
            for (TaskDto taskDto : allTasks) {
                logger.info("update index of task: {}", taskDto.getTaskId());
                taskService.updateIndex(taskDto);
            }
        }
        messageService.sendMessage("Update index finished: " + (allTasks == null ? 0 : allTasks.size()));
    }
}
