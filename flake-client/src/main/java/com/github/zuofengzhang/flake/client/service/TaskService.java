package com.github.zuofengzhang.flake.client.service;

import com.github.zuofengzhang.flake.client.entity.DailyTaskDetailViewDto;
import com.github.zuofengzhang.flake.client.entity.TaskDto;
import com.github.zuofengzhang.flake.client.entity.TaskType;

import java.util.List;

public interface TaskService {


    void deleteById(TaskDto task);

    void restoreById(TaskDto task);

    int updateById(TaskDto selectedItem);

    TaskDto findById(int taskId);

    List<TaskDto> findAllUndoneTasks();

    public List<TaskDto> findTasksByDayIdAndType(int dayId, TaskType taskType);

    void moveOrderTop(TaskDto selectedItem);

    void moveOrderUp(TaskDto selectedItem);

    void moveOrderDown(TaskDto selectedItem);

    DailyTaskDetailViewDto addTask(String title, TaskType taskType, int dayId);
}
