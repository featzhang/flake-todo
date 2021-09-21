package com.github.zuofengzhang.flake.client.service.impl;

import com.github.zuofengzhang.flake.client.components.ItemChoiceData;
import com.github.zuofengzhang.flake.client.service.CommonItemService;
import com.google.common.collect.Lists;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CommonItemServiceImpl implements CommonItemService {

    @Override
    public List<ItemChoiceData> queryItemDataList() {
        List<ItemChoiceData> list = Lists.newArrayList();
        list.add(ItemChoiceData.builder().text("讨论").sit(true).id(1).selected(true).build());
        list.add(ItemChoiceData.builder().text("吃饭").sit(false).id(2).build());
        list.add(ItemChoiceData.builder().text("睡觉").sit(false).id(3).build());
        list.add(ItemChoiceData.builder().text("上厕所").sit(false).id(4).build());
        list.add(ItemChoiceData.builder().text("其他").sit(true).id(0).build());
        return list;
    }
}
