package com.yhxjs.magicpacket.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yhxjs.magicpacket.mapper.UserMapper;
import com.yhxjs.magicpacket.pojo.entity.User;
import com.yhxjs.magicpacket.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Override
    public User getInfoByName(String name, String pwd) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getName, name)
                .eq(User::getPassword, pwd);
        return this.getOne(queryWrapper);
    }

    @Override
    public String getSaltByName(String name) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getName, name);
        User user = this.getOne(queryWrapper);
        return user != null ? user.getSalt() : null;
    }
}
