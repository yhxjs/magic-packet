package com.yhxjs.magicpacket.enums;

import lombok.Getter;

@Getter
public enum Operate {

    INSERT("insert", "新增"),
    DELETE("delete", "删除"),
    UPDATE("update", "修改"),
    QUERY("query", "查询");

    private final String code;

    private final String desc;

    Operate(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
