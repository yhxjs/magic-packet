package com.yhxjs.magicpacket.controller;

import com.yhxjs.magicpacket.exception.AccessIllegalException;
import com.yhxjs.magicpacket.pojo.bo.WakeBo;
import com.yhxjs.magicpacket.pojo.entity.Config;
import com.yhxjs.magicpacket.service.ConfigService;
import com.yhxjs.magicpacket.service.WakeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@Slf4j
@Validated
@RestController
@RequestMapping("/wake")
public class WakeController {

    @Autowired
    private ConfigService configService;

    @Autowired
    private WakeService wakeService;

    @PostMapping("/send")
    public Result send(@RequestBody WakeBo bo) {
        Config config = configService.getById(bo.getId());
        if (config == null) {
            throw new AccessIllegalException();
        }
        try {
            wakeService.send(config.getIp(), config.getMask(), config.getMac(), config.getPort());
            configService.updateLastSendTime(config.getId());
        } catch (IOException e) {
            return Result.error(Code.SYSTEM_ERROR, "发送失败，请稍后再试");
        }
        return Result.success(Code.OK, "发送成功");
    }
}
