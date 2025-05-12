package com.yhxjs.magicpacket.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yhxjs.magicpacket.mapper.ConfigMapper;
import com.yhxjs.magicpacket.pojo.bo.ConfigBo;
import com.yhxjs.magicpacket.pojo.entity.Config;
import com.yhxjs.magicpacket.pojo.vo.PageVo;
import com.yhxjs.magicpacket.service.ConfigService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;

@Service
public class ConfigServiceImpl extends ServiceImpl<ConfigMapper, Config> implements ConfigService {

    @Override
    public void add(ConfigBo bo) {
        Config config = new Config();
        copyToConfig(bo, config);
        config.setCrtDt(new Date());
        this.save(config);
    }

    @Override
    public void update(ConfigBo bo) {
        Config config = new Config();
        config.setId(bo.getId());
        copyToConfig(bo, config);
        config.setUpdDt(new Date());
        this.updateById(config);
    }

    @Override
    public void delete(Integer id) {
        this.removeById(id);
    }

    @Override
    public PageVo pageQuery(Integer page, Integer size) {
        PageVo pageVo = new PageVo();
        LambdaQueryWrapper<Config> queryWrapper = new LambdaQueryWrapper<>();
        long total = this.count(queryWrapper);
        pageVo.setTotal(total);
        if (total == 0) {
            pageVo.setRows(new ArrayList<>());
            return pageVo;
        }
        Page<Config> configPage = new Page<>(page, size);
        pageVo.setRows(this.list(configPage, queryWrapper));
        return pageVo;
    }

    private void copyToConfig(ConfigBo bo, Config config) {
        config.setName(bo.getName());
        config.setDesc(bo.getDesc());
        config.setMac(bo.getMac().replace(":", "-"));
        config.setIp(bo.getIp());
        config.setMask(bo.getMask());
        config.setPort(bo.getPort());
    }
}
