package com.yhxjs.magicpacket.interceptor;

import com.alibaba.fastjson.JSON;
import com.yhxjs.magicpacket.constants.RedisKeyConstants;
import com.yhxjs.magicpacket.controller.Code;
import com.yhxjs.magicpacket.controller.Result;
import com.yhxjs.magicpacket.pojo.entity.User;
import com.yhxjs.magicpacket.service.RedisService;
import com.yhxjs.magicpacket.utils.JwtUtil;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class LoginInterceptor implements HandlerInterceptor {

    @Resource
    private RedisService redisService;

    @Resource
    private JwtUtil jwtUtil;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader("Authentication");
        if (token != null) {
            try {
                User user = jwtUtil.parseToken(token, User.class);
                User realuser = (User) redisService.get(RedisKeyConstants.USER_INFO + user.getId());
                if (realuser == null) {
                    response.setContentType("application/json;charset=utf-8");
                    response.getWriter().write(JSON.toJSONString(Result.error(Code.TOKEN_FAIL, "登录信息有误，请重新登录！")));
                    return false;
                }
                if (user.getSessionId().equals(realuser.getSessionId())) {
                    jwtUtil.setUser(realuser);
                    return true;
                }
                response.setContentType("application/json;charset=utf-8");
                response.getWriter().write(JSON.toJSONString(Result.error(Code.TOKEN_FAIL, "您的账户在其他地方登录，请重新登录！")));
                return false;
            } catch (Exception ignored) {
                response.setContentType("application/json;charset=utf-8");
                response.getWriter().write(JSON.toJSONString(Result.error(Code.TOKEN_FAIL, "登录信息有误，请重新登录！")));
                return false;
            }
        }
        response.setContentType("application/json;charset=utf-8");
        response.getWriter().write(JSON.toJSONString(Result.error(Code.TOKEN_FAIL, "登录信息有误，请重新登录！")));
        return false;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        jwtUtil.removeUser();
    }
}
