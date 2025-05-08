package com.yhxjs.magicpacket.pojo.bo;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Data
public class UserLoginBo implements Serializable {

    @NotEmpty
    @Size(min = 4, max = 4)
    String code;

    @NotEmpty
    String name;

    @NotEmpty
    String password;
}
