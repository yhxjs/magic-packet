package com.yhxjs.magicpacket.pojo.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.io.Serializable;

@Data
public class User implements Serializable {

    @TableId(type = IdType.AUTO)
    private Integer id;

    private String name;

    @JsonIgnore
    @JSONField(serialize = false)
    private String salt;

    @JsonIgnore
    @JSONField(serialize = false)
    @TableField("`password`")
    private String password;

    @TableField(exist = false)
    private String sessionId;
}
