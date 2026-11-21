package com.stitch.mynovel.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SexEnum {
    MAN(0, "男"),
    WOMAN(1, "女");

    @EnumValue
    private Integer sex;
    private String desc;
}
