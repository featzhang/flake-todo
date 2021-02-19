package com.github.zuofengzhang.flake.client.service;

import com.github.zuofengzhang.flake.client.entity.TaskDto;

import java.util.List;

public interface TaskService {
    List<TaskDto> findAllTasks();

    List<TaskDto> findAllTasksByDayId(int dayId);

    int insert(TaskDto taskDto);

    void deleteById(int taskId);

    int updateById(TaskDto selectedItem);

    TaskDto findById(int taskId);

    List<TaskDto> findAllUndoneTasks();
}
