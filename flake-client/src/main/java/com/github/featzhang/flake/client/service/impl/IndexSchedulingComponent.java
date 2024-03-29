package com.github.featzhang.flake.client.service.impl;

import com.github.featzhang.flake.client.entity.TaskDto;
import com.github.featzhang.flake.client.service.TaskService;
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
    private TaskService taskService;

    @Scheduled(initialDelay = 60_000, fixedRate = 3600_000_0)
    public void updateIndex() {
        List<TaskDto> allTasks = taskService.findAllTasks();
        if (allTasks != null && !allTasks.isEmpty()) {
            logger.info("task count: {}.", allTasks.size());
            for (TaskDto taskDto : allTasks) {
                logger.info("update index of task: {}", taskDto.getTaskId());
                taskService.updateIndex(taskDto);
            }
        }
    }
}
