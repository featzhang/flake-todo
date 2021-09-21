package com.github.zuofengzhang.flake.client.components;

import com.github.zuofengzhang.flake.client.entity.TaskDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ItemChoiceData implements Serializable, Cloneable {


    private String text;
    private boolean selected;
    private boolean sit;
    private int id;

    @Override
    public ItemChoiceData clone()  {
        ItemChoiceData taskDto = new ItemChoiceData();
        BeanUtils.copyProperties(this, taskDto);
        return taskDto;
    }
}
