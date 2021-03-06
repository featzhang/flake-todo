package com.github.zuofengzhang.flake.client.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.zuofengzhang.flake.client.constraints.FlakeSettings;
import com.github.zuofengzhang.flake.client.dao.DailyTaskDao;
import com.github.zuofengzhang.flake.client.dao.SingleTaskDao;
import com.github.zuofengzhang.flake.client.dao.TaskDao;
import com.github.zuofengzhang.flake.client.dao.TaskDetailDao;
import com.github.zuofengzhang.flake.client.entity.StoreStatus;
import com.github.zuofengzhang.flake.client.entity.TaskType;
import com.github.zuofengzhang.flake.client.entity.dos.DailyTaskDo;
import com.github.zuofengzhang.flake.client.entity.dos.SingleDailyTaskDo;
import com.github.zuofengzhang.flake.client.entity.dos.TaskDo;
import com.github.zuofengzhang.flake.client.entity.dto.DailyTaskDto;
import com.github.zuofengzhang.flake.client.entity.dto.SingleDailyTaskDto;
import com.github.zuofengzhang.flake.client.entity.dto.TaskDetailDto;
import com.github.zuofengzhang.flake.client.entity.dto.TaskDto;
import com.github.zuofengzhang.flake.client.service.TaskService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
public class TaskServiceImpl implements TaskService {
    private static final Logger logger = LoggerFactory.getLogger(TaskServiceImpl.class);

    private final FlakeSettings settings = FlakeSettings.getInstance();
    @Resource
    private       TaskDao       taskDao;
    @Resource
    private       DailyTaskDao  dailyTaskDao;

//    @Override
//    public List<TaskDto> findTasksByDailyDayIdAndType(final int dailyTaskDayId, final TaskType dailyTaskType) {
//
//        final TaskDo.TaskDoBuilder builder = TaskDo.builder().dayId(dailyTaskDayId).typeId(dailyTaskType.getCId());
//        if (!settings.getShowDeletedTask()) {
//            builder.storeStatus(StoreStatus.YES.getCode());
//        }
//
//        final TaskDo aDo = builder.build();
//        return taskDao.selectList(new QueryWrapper<>(aDo).orderByAsc("importance_urgency_axis")
//                .orderByDesc("priority_order", "update_time")).stream().map(TaskDto::parse)
//                .collect(Collectors.toList());
//    }

    @Override
    public void moveOrderTop(final TaskDto task) {
        final TaskDo taskDo = taskDao
                .selectOne(new QueryWrapper<TaskDo>().orderByDesc("priority_order").last("limit 1"));
        final int priorityOrder = taskDo.getTaskOrder();
        task.setTaskOrder(priorityOrder + 10);
        updateById(task);
    }

    @Override
    public void moveOrderTop(DailyTaskDto dailyTaskDto) {
        Integer dayId  = dailyTaskDto.getDayId();
        int     typeId = dailyTaskDto.getTaskType().getCode();

        final DailyTaskDo taskDo = dailyTaskDao
                .selectOne(new QueryWrapper<DailyTaskDo>(DailyTaskDo.builder().dayId(dayId).typeId(typeId).build())
                        .orderByDesc("daily_order").last("limit 1"));
        final int priorityOrder = taskDo.getDailyOrder();
        dailyTaskDto.setDailyOrder(priorityOrder + 10);
        updateById(dailyTaskDto);
    }

    @Override
    public void moveOrderUp(final TaskDto task) {
        final long priorityOrder = task.getPriorityOrder();
        final TaskDo lastBiggest = taskDao.selectOne(new QueryWrapper<TaskDo>().gt("priority_order", priorityOrder)
                .orderByAsc("priority_order").last("limit 1"));
        final int priorityOrder1 = lastBiggest.getTaskOrder();
        task.setTaskOrder(priorityOrder1 + 1);

        updateById(task);
    }

    @Override
    public void moveOrderDown(final TaskDto task) {
        final long priorityOrder = task.getPriorityOrder();
        final TaskDo lastBiggest = taskDao.selectOne(new QueryWrapper<TaskDo>().lt("priority_order", priorityOrder)
                .orderByDesc("priority_order").last("limit 1"));
        final int priorityOrder1 = lastBiggest.getTaskOrder();
        task.setTaskOrder(priorityOrder1 - 1);
        updateById(task);
    }

    @Override
    public void moveOrderDown(DailyTaskDto task) {
        final long priorityOrder = task.getDailyOrder();
        final DailyTaskDo lastBiggest = dailyTaskDao.selectOne(new QueryWrapper<>(DailyTaskDo.builder().dayId(task.getDayId()).typeId(task.getTaskType().getCode()).build()).lt("daily_order", priorityOrder)
                .orderByDesc("daily_order").last("limit 1"));
        final int priorityOrder1 = lastBiggest.getDailyOrder();
        task.setDailyOrder(priorityOrder1 - 1);
        updateById(task);
    }

    @Override
    public void moveOrderUp(DailyTaskDto task) {
        final long priorityOrder = task.getDailyOrder();
        final DailyTaskDo lastBiggest = dailyTaskDao.selectOne(new QueryWrapper<>(DailyTaskDo.builder().dayId(task.getDayId()).typeId(task.getTaskType().getCode()).build()).gt("daily_order", priorityOrder)
                .orderByAsc("daily_order").last("limit 1"));
        final int priorityOrder1 = lastBiggest.getDailyOrder();
        logger.info("this.order: {} biggest order : {}", priorityOrder, priorityOrder1);
        task.setDailyOrder(priorityOrder1 + 1);
        updateById(task);
    }

    @Override
    public void deleteById(final TaskDto task) {
        task.setStoreStatus(StoreStatus.NO);
        updateById(task);
    }

    @Override
    public void restoreById(final TaskDto task) {
        task.setStoreStatus(StoreStatus.YES);
        updateById(task);
    }

    @Override
    public int updateById(final TaskDto task) {
        final TaskDo taskDo = task.parse();
        return taskDao.updateById(taskDo);
    }

    @Override
    public TaskDto findById(final int taskId) {
        return TaskDto.parse(taskDao.selectById(taskId));
    }

    @Override
    public List<TaskDto> findAllUndoneTasks() {
        final TaskDo.TaskDoBuilder builder = TaskDo.builder();
        if (!settings.getShowDeletedTask()) {
            builder.storeStatus(StoreStatus.YES.getCode());
        }
        if (!settings.getShowCompletedTask()) {
            builder.finished(false);
        }
        final TaskDo aDo = builder.build();
        return taskDao.selectList(new QueryWrapper<>(aDo).orderByAsc("importance_urgency_axis")
                .orderByDesc("priority_order", "update_time")).stream().map(TaskDto::parse)
                .collect(Collectors.toList());
    }

    @Resource
    private SingleTaskDao singleTaskDao;

    @Override
    public SingleDailyTaskDo getSingleDailyTasksByDailyId(final int dailyTaskId) {
        final List<SingleDailyTaskDo> list = singleTaskDao.getList(new QueryWrapper<SingleDailyTaskDo>()
                .eq("daily_task_id", dailyTaskId).orderByAsc("iua").orderByDesc("daily_order"));
        return list.isEmpty() ? null : list.get(0);
    }

    @Override
    public List<SingleDailyTaskDto> getSingleDailyTaskListByDayIdAndTaskType(final int dayId, final TaskType taskType) {
        return singleTaskDao
                .getList(new QueryWrapper<SingleDailyTaskDo>().eq("day_id", dayId).eq("type_id", taskType.getCId()).orderByAsc("iua").orderByDesc("daily_order"))
                .stream().map(SingleDailyTaskDto::parse).collect(Collectors.toList());
    }

    @Resource
    private TaskDetailDao taskDetailDao;

    @Override
    public List<TaskDetailDto> getTaskDetailList() {
        return taskDetailDao.getList(new QueryWrapper<TaskDetailDto>());
    }

    @Override
    public int deleteDailyTaskById(final int diailyTaskId) {
        return dailyTaskDao.deleteById(diailyTaskId);
    }

    @Override
    public int updateDailyTask(final DailyTaskDto diailyTask) {
        return dailyTaskDao.updateById(diailyTask.parse());
    }

    @Override
    public TaskDetailDto getTaskDetailByTaskId(final int taskId) {
        final List<TaskDetailDto> list = taskDetailDao.getList(new QueryWrapper<TaskDetailDto>().eq("task_id", taskId));
        return list.isEmpty() ? null : list.get(0);
    }

    @Override
    public int restoreDailyTask(final DailyTaskDto dailyTask) {

        dailyTask.setStoreStatus(StoreStatus.YES.getCode());
        return dailyTaskDao.updateById(dailyTask.parse());
    }

    @Override
    public void addTask(String text, TaskType taskType, int dayId) {
        TaskDo taskDo = TaskDo.builder().title(text).createTime(System.currentTimeMillis())
                .updateTime(System.currentTimeMillis()).build();
        taskDao.insert(taskDo);
        Integer taskId = taskDo.getTaskId();
        DailyTaskDo dailyTask = DailyTaskDo.builder().dayId(dayId).typeId(taskType.getCId()).taskId(taskId)
                .createTime(System.currentTimeMillis()).updateTime(System.currentTimeMillis()).build();
        dailyTaskDao.insert(dailyTask);
    }

    @Override
    public int deleteById(SingleDailyTaskDto taskDto) {
        int dailyTaskId = taskDto.getDailyTaskId();
        return dailyTaskDao.deleteById(dailyTaskId);
    }

    @Override
    public int updateById(DailyTaskDto dailyTaskDto) {
        return dailyTaskDao.updateById(dailyTaskDto.parse());
    }

    @Override
    public int deleteById(DailyTaskDto dailyTaskDto) {
        return dailyTaskDao.deleteById(dailyTaskDto.parse());
    }

}
