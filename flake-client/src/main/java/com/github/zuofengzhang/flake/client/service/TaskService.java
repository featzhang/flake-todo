package com.github.zuofengzhang.flake.client.service;

import com.github.zuofengzhang.flake.client.entity.TaskType;
import com.github.zuofengzhang.flake.client.entity.dos.SingleDailyTaskDo;
import com.github.zuofengzhang.flake.client.entity.dto.DailyTaskDto;
import com.github.zuofengzhang.flake.client.entity.dto.SingleDailyTaskDto;
import com.github.zuofengzhang.flake.client.entity.dto.TaskDetailDto;
import com.github.zuofengzhang.flake.client.entity.dto.TaskDto;

import java.util.List;

public interface TaskService {

    void deleteById(TaskDto task);

    void restoreById(TaskDto task);

    int updateById(TaskDto selectedItem);

    TaskDto findById(int taskId);

    List<TaskDto> findAllUndoneTasks();

//    public List<TaskDto> findTasksByDailyDayIdAndType(int dailyTaskDayId, TaskType dailyTaskType);

    void moveOrderTop(TaskDto selectedItem);

    void moveOrderUp(TaskDto selectedItem);

    void moveOrderDown(TaskDto selectedItem);

    TaskDetailDto getTaskDetailByTaskId(int taskId);

    SingleDailyTaskDo getSingleDailyTasksByDailyId(int dailyTaskId);

    List<SingleDailyTaskDto> getSingleDailyTaskListByDayIdAndTaskType(int dayId, TaskType taskType);

    List<TaskDetailDto> getTaskDetailList();

    int deleteDailyTaskById(int diailyTaskId);

    int restoreDailyTask(DailyTaskDto dailyTask);

    int updateDailyTask(DailyTaskDto diailyTask);

    void addTask(String text, TaskType taskType, int dayId);

    int deleteById(SingleDailyTaskDto selectedItem);

    int updateById(DailyTaskDto dailyTaskDto);

    int deleteById(DailyTaskDto dailyTaskDto);

    void moveOrderTop(DailyTaskDto dailyTaskDto);

    void moveOrderDown(DailyTaskDto toDailyTaskDto);

    void moveOrderUp(DailyTaskDto toDailyTaskDto);

    List<TaskDetailDto> findAllUndoneTaskDetails();
}
