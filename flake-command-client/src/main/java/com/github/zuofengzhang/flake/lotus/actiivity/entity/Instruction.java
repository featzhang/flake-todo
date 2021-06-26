package com.github.zuofengzhang.flake.lotus.actiivity.entity;

import lombok.*;

import java.io.Serializable;

/**
 * @author averyzhang
 * @date 2021/6/8
 */
@Data
@Getter
@Setter
@Builder
@AllArgsConstructor
@ToString
public class Instruction implements Serializable {
    private COMMAND_TYPE type;
    private String       content;
}
