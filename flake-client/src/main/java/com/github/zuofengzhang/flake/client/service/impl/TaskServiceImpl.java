package com.github.zuofengzhang.flake.client.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.zuofengzhang.flake.client.constraints.FlakeConstants;
import com.github.zuofengzhang.flake.client.constraints.FlakeSettings;
import com.github.zuofengzhang.flake.client.dao.DailyTaskDao;
import com.github.zuofengzhang.flake.client.dao.DailyTaskDetailViewDao;
import com.github.zuofengzhang.flake.client.dao.TaskDao;
import com.github.zuofengzhang.flake.client.entity.*;
import com.github.zuofengzhang.flake.client.service.TaskService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * https://www.itzhimei.com/archives/1063.html
 *
 * @author zhangzuofeng1
 */
@Service
@Slf4j
public class TaskServiceImpl implements TaskService {

    private final FlakeSettings          settings = FlakeSettings.getInstance();
    @Resource
    private       TaskDao                dao;
    @Resource
    private       DailyTaskDetailViewDao viewDao;
    @Resource
    private       DailyTaskDao           dailyTaskDao;

    public List<DailyTaskDetailViewDto> selectDailyTaskOfType(int dayId, TaskType taskType) {
        return viewDao.selectList(
                new QueryWrapper<>(
                        DailyTaskDetailViewDo
                                .builder()
                                .dayId(dayId)
                                .typeId(taskType.getCId())
                                .build()
                )
                        .orderByAsc("importance_urgency_axis")
                        .orderByDesc("priority_order")
                        .orderByDesc("update_time")
        ).stream().map(x -> (DailyTaskDetailViewDto) x).collect(Collectors.toList());
    }

    public TaskWithDailyDo selectTask(){

    }

    @Override
    public List<TaskDto> findTasksByDayIdAndType(int dayId, TaskType taskType) {
        TaskDo.TaskDoBuilder builder = TaskDo.builder().dayId(dayId).typeId(taskType.getCId());
        if (!settings.getShowDeletedTask()) {
            builder.storeStatus(StoreStatus.YES.getCode());
        }

        TaskDo aDo = builder.build();
        return dao
                .selectList(
                        new QueryWrapper<>(
                                aDo
                        )
                                .orderByAsc("importance_urgency_axis")
                                .orderByDesc("priority_order", "update_time")
                )
                .stream()
                .map(TaskDto::parse)
                .collect(Collectors.toList());
    }

    @Override
    public void moveOrderTop(TaskDto task) {
        TaskDo taskDo        = dao.selectOne(new QueryWrapper<TaskDo>().orderByDesc("priority_order").last("limit 1"));
        Long   priorityOrder = taskDo.getPriorityOrder();
        task.setPriorityOrder(priorityOrder + 10);
        updateById(task);
    }

    @Override
    public void moveOrderUp(TaskDto task) {
        long   priorityOrder  = task.getPriorityOrder();
        TaskDo lastBiggest    = dao.selectOne(new QueryWrapper<TaskDo>().gt("priority_order", priorityOrder).orderByAsc("priority_order").last("limit 1"));
        Long   priorityOrder1 = lastBiggest.getPriorityOrder();
        task.setPriorityOrder(priorityOrder1 + 1);

        updateById(task);
    }

    @Override
    public void moveOrderDown(TaskDto task) {
        long   priorityOrder  = task.getPriorityOrder();
        TaskDo lastBiggest    = dao.selectOne(new QueryWrapper<TaskDo>().lt("priority_order", priorityOrder).orderByDesc("priority_order").last("limit 1"));
        Long   priorityOrder1 = lastBiggest.getPriorityOrder();
        task.setPriorityOrder(priorityOrder1 - 1);
        updateById(task);
    }


    @Override
    public void deleteById(TaskDto task) {
//        taskDao.deleteById(taskId);
        // logic deletes
        task.setStoreStatus(StoreStatus.NO);
        updateById(task);
    }

    @Override
    public void restoreById(TaskDto task) {
        task.setStoreStatus(StoreStatus.YES);
        updateById(task);
    }

    @Override
    public int updateById(TaskDto task) {
        TaskDo taskDo = task.parse();
        return dao.updateById(taskDo);
    }

    @Override
    public TaskDto findById(int taskId) {
        return TaskDto.parse(dao.selectById(taskId));
    }

    @Override
    public List<TaskDto> findAllUndoneTasks() {
        TaskDo.TaskDoBuilder builder = TaskDo.builder();
        if (!settings.getShowDeletedTask()) {
            builder.storeStatus(StoreStatus.YES.getCode());
        }
        if (!settings.getShowCompletedTask()) {
            builder.finished(false);
        }
        TaskDo aDo = builder.build();
        return dao
                .selectList(
                        new QueryWrapper<>(aDo)
                                .orderByAsc("importance_urgency_axis")
                                .orderByDesc("priority_order", "update_time")
                )
                .stream()
                .map(TaskDto::parse)
                .collect(Collectors.toList());
    }


    @Override
    public DailyTaskDetailViewDto addTask(String title, TaskType taskType, int dayId) {
        TaskDo taskDo
                = TaskDo.builder()
                .title(title)
                .priorityOrder(FlakeConstants.DEFAULT_PRIORITY_ORDER)
                .createTime(System.currentTimeMillis())
                .updateTime(System.currentTimeMillis())
                .importanceUrgencyAxis(FlakeConstants.DEFAULT_IUA.getCode())
                .build();
        int         insert      = dao.insert(taskDo);
        Integer     taskId      = taskDo.getTaskId();
        DailyTaskDo dailyTaskDo = DailyTaskDo.builder().taskId(taskId).dayId(dayId).createTime(System.currentTimeMillis()).updateTime(System.currentTimeMillis()).typeId(taskType.getCId()).build();
        dailyTaskDao.insert(dailyTaskDo);
        return DailyTaskDetailViewDto.parse(TaskDto.parse(taskDo), DailyTaskDto.parse(dailyTaskDo));
    }
}
