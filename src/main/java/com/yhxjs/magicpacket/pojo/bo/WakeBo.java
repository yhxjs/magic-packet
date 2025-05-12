package com.yhxjs.magicpacket.pojo.bo;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class WakeBo implements Serializable {

    @NotNull
    private Integer id;
}
