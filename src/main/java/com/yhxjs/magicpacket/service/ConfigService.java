package com.yhxjs.magicpacket.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yhxjs.magicpacket.pojo.bo.ConfigBo;
import com.yhxjs.magicpacket.pojo.entity.Config;
import com.yhxjs.magicpacket.pojo.vo.PageVo;

public interface ConfigService extends IService<Config> {

    void add(ConfigBo bo);

    void update(ConfigBo bo);

    void delete(Integer id);

    PageVo pageQuery(Integer page, Integer size);
}
