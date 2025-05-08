package com.yhxjs.magicpacket.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yhxjs.magicpacket.pojo.entity.User;

public interface UserService extends IService<User> {

    String getSaltByName(String name);

    User getInfoByName(String name, String pwd);
}
