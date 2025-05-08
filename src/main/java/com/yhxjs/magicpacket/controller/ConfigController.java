package com.yhxjs.magicpacket.controller;

import com.yhxjs.magicpacket.enums.Operate;
import com.yhxjs.magicpacket.pojo.bo.ConfigBo;
import com.yhxjs.magicpacket.service.ConfigService;
import com.yhxjs.magicpacket.validation.AddGroup;
import com.yhxjs.magicpacket.validation.DeleteGroup;
import com.yhxjs.magicpacket.validation.QueryGroup;
import com.yhxjs.magicpacket.validation.UpdateGroup;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Slf4j
@Validated
@RestController
@RequestMapping("/config")
public class ConfigController {

    @Autowired
    private ConfigService configService;

    @Validated(AddGroup.class)
    @PostMapping("/add")
    public Result add(@Valid @RequestBody ConfigBo bo) {
        configService.add(bo);
        return Result.success(Operate.INSERT, "添加成功！");
    }

    @Validated(UpdateGroup.class)
    @PostMapping("/update")
    public Result update(@Valid @RequestBody ConfigBo bo) {
        configService.update(bo);
        return Result.success(Operate.UPDATE, "修改成功！");
    }

    @Validated(DeleteGroup.class)
    @PostMapping("/delete")
    public Result delete(@Valid @RequestBody ConfigBo bo) {
        configService.delete(bo.getId());
        return Result.success(Operate.DELETE, "删除成功！");
    }

    @Validated(QueryGroup.class)
    @PostMapping("/query")
    public Result query(@Valid @RequestBody ConfigBo bo) {
        return Result.querySuccess(configService.pageQuery(bo.getPage(), bo.getSize()));
    }
}
