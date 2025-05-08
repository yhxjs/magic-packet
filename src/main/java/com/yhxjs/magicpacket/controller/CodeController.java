package com.yhxjs.magicpacket.controller;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.ICaptcha;
import com.yhxjs.magicpacket.constants.RedisKeyConstants;
import com.yhxjs.magicpacket.service.RedisService;
import com.yhxjs.magicpacket.utils.JwtUtil;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Validated
@RestController
public class CodeController {

    @Resource
    private RedisService redisService;

    @Resource
    private JwtUtil jwtUtil;

    @GetMapping("/imgCode")
    public void getImageCode(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        ICaptcha captcha = CaptchaUtil.createLineCaptcha(150, 40, 4, 200);
        captcha.write(resp.getOutputStream());
        String sessionId = Optional.ofNullable(jwtUtil.getSessionId(req)).orElse(req.getSession().getId());
        redisService.set(RedisKeyConstants.SESSION_IMG_CODE + sessionId, captcha.getCode(), 10, TimeUnit.MINUTES);
    }
}
