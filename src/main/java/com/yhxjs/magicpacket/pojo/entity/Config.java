package com.yhxjs.magicpacket.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class Config implements Serializable {

    @TableId(type = IdType.AUTO)
    private Integer id;

    private String name;

    private String desc;

    private String mac;

    String ip;

    Integer mask;

    Integer port;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    Date crtDt;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    Date updDt;
}
