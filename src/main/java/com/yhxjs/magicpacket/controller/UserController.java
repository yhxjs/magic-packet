package com.yhxjs.magicpacket.controller;

import com.yhxjs.magicpacket.constants.RedisKeyConstants;
import com.yhxjs.magicpacket.pojo.bo.UserLoginBo;
import com.yhxjs.magicpacket.pojo.entity.User;
import com.yhxjs.magicpacket.service.RedisService;
import com.yhxjs.magicpacket.service.UserService;
import com.yhxjs.magicpacket.utils.JwtUtil;
import com.yhxjs.magicpacket.utils.LimitUtil;
import com.yhxjs.magicpacket.utils.SHAUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Slf4j
@Validated
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Resource
    private JwtUtil jwtUtil;

    @Resource
    private RedisService redisService;

    @PostMapping("/login")
    public Result login(HttpServletRequest req, @Valid @RequestBody UserLoginBo bo) {
        String code = bo.getCode();
        String name = bo.getName();
        String password = bo.getPassword();
        if (!LimitUtil.getLoginIPStatus(req)) {
            return Result.error(Code.LOGIN_FAIL, LimitUtil.getLoginIPString(req, name));
        }
        String sessionId = jwtUtil.getSessionId(req);
        String captcha = (String) redisService.get(RedisKeyConstants.SESSION_IMG_CODE + sessionId);
        if (code != null && code.equalsIgnoreCase(captcha)) {
            try {
                name = SHAUtil.decrypt(name, sessionId);
                password = SHAUtil.decrypt(password, sessionId);
            } catch (Exception e) {
                redisService.delete(RedisKeyConstants.SESSION_IMG_CODE + sessionId);
                return Result.error(Code.CODE_FAIL, "验证码错误！");
            }
            String salt = userService.getSaltByName(name);
            if (salt != null) {
                String realpwd = SHAUtil.getSHA(salt + password);
                User user = userService.getInfoByName(name, realpwd);
                if (user != null) {
                    user.setSessionId(sessionId);
                    redisService.set(RedisKeyConstants.USER_INFO + user.getId(), user, 7, TimeUnit.DAYS);
                    return Result.success(Code.LOGIN_SUCCESS, jwtUtil.createToken(user, 7 * 24 * 60 * 60 * 1000L), "七天免登录成功！");
                }
            }
            redisService.delete(RedisKeyConstants.SESSION_IMG_CODE + sessionId);
            return Result.error(Code.LOGIN_FAIL, LimitUtil.getLoginIPString(req, name));
        }
        redisService.delete(RedisKeyConstants.SESSION_IMG_CODE + sessionId);
        return Result.error(Code.CODE_FAIL, "验证码错误！");
    }

    @PostMapping("/logout")
    public Result logout() {
        int userId = jwtUtil.getUserId();
        redisService.delete(RedisKeyConstants.USER_INFO + userId);
        return Result.success(Code.OK, "退出成功！");
    }

    @PostMapping("/")
    public Result info() {
        User user = jwtUtil.getUser();
        return Result.querySuccess(user);
    }

    @PostMapping("/getSalt")
    public Result getSalt(HttpServletRequest req, @RequestParam String password) {
        String sessionId = jwtUtil.getSessionId(req);
        try {
            password = SHAUtil.decrypt(password, sessionId);
        } catch (Exception e) {
            return Result.error(Code.QUERY_FAIL, "获取盐值失败！");
        }
        String salt = SHAUtil.getSalt();
        String realpwd = SHAUtil.getSHA(salt + password);
        Map<String, String> map = Map.of("salt", salt, "password", realpwd);
        return Result.querySuccess(map);
    }
}
