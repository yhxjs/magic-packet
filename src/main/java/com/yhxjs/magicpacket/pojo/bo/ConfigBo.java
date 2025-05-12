package com.yhxjs.magicpacket.pojo.bo;

import com.yhxjs.magicpacket.validation.AddGroup;
import com.yhxjs.magicpacket.validation.DeleteGroup;
import com.yhxjs.magicpacket.validation.QueryGroup;
import com.yhxjs.magicpacket.validation.UpdateGroup;
import com.yhxjs.magicpacket.validation.validator.IP;
import com.yhxjs.magicpacket.validation.validator.MAC;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class ConfigBo implements Serializable {

    @NotNull(groups = {UpdateGroup.class, DeleteGroup.class})
    private Integer id;

    @NotEmpty(groups = {AddGroup.class, UpdateGroup.class})
    private String name;

    private String desc;

    @NotEmpty(groups = {AddGroup.class, UpdateGroup.class})
    @MAC(groups = {AddGroup.class, UpdateGroup.class})
    private String mac;

    @NotEmpty(groups = {AddGroup.class, UpdateGroup.class})
    @IP(groups = {AddGroup.class, UpdateGroup.class})
    private String ip;

    @NotNull(groups = {AddGroup.class, UpdateGroup.class})
    @Range(min = 1, max = 32, groups = {AddGroup.class, UpdateGroup.class})
    private Integer mask;

    @NotNull(groups = {AddGroup.class, UpdateGroup.class})
    @Range(min = 1, max = 65535, groups = {AddGroup.class, UpdateGroup.class})
    private Integer port;

    @NotNull(groups = {QueryGroup.class})
    private Integer page;

    @NotNull(groups = {QueryGroup.class})
    private Integer size;
}
