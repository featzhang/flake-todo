/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.zuofengzhang.flake.client.dao;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.zuofengzhang.flake.client.entity.dos.DailyTaskDo;
import com.github.zuofengzhang.flake.client.entity.dos.SingleDailyTaskDo;
import com.github.zuofengzhang.flake.client.entity.dto.DailyTaskDto;
import com.github.zuofengzhang.flake.client.entity.dto.TaskDetailDto;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author zhangzuofeng1
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class TaskDaoTest {

    @Resource
    private TaskDao dao;
    @Resource
    private DailyTaskDao dailyDao;
    @Resource
    private TaskDetailDao taskDetailDao;
    @Resource
    private SingleTaskDao singleTaskDao;

    @Test
    public void getTaskDetailListsOfTask() {
        List<TaskDetailDto> list = taskDetailDao.getList(new QueryWrapper<TaskDetailDto>().eq("task_id", 49));
        System.out.println(list.size());
        for (TaskDetailDto taskDetailDto : list) {
            System.out.println(taskDetailDto.getTaskId() + ":\t" + taskDetailDto.getTitle());
            List<DailyTaskDto> dailyTasks = taskDetailDto.getDailyTasks();
            dailyTasks.forEach(System.out::println);
        }
    }

    @Test
    public void getSingleDailyTasksByDailyTask() {
        List<SingleDailyTaskDo> taskDetail = singleTaskDao
                .getList(new QueryWrapper<SingleDailyTaskDo>().eq("daily_task_id", 3));
        System.out.println(taskDetail);
    }

    @Test
    public void getSingleDailyTasksByDayId() {
        List<SingleDailyTaskDo> taskDetail = singleTaskDao
                .getList(new QueryWrapper<SingleDailyTaskDo>().eq("day_id", 20210227));
        System.out.println(taskDetail);
    }

    @Test
    public void getAllSingleDailyTasks() {
        List<SingleDailyTaskDo> taskDetail = singleTaskDao.getList(new QueryWrapper<SingleDailyTaskDo>());
        System.out.println(taskDetail);
    }

    @Test
    public void getListByHeroId() {
        List<DailyTaskDo> taskDetailDto = dailyDao.getListByTaskId(49);
        System.out.println(taskDetailDto);
    }

    @Test
    public void insert() {
        int insert = dailyDao.insert(DailyTaskDo.builder().taskId(49).createTime(System.currentTimeMillis())
                .updateTime(System.currentTimeMillis()).dayId(20210227).storeStatus(1).typeId(1)
                .notes("this is 20210227 notes").build());
        Assert.assertTrue(insert == 1);
    }

}
