package com.yhxjs.magicpacket.interceptor;

import com.alibaba.fastjson.JSON;
import com.yhxjs.magicpacket.controller.Code;
import com.yhxjs.magicpacket.controller.Result;
import com.yhxjs.magicpacket.utils.JwtUtil;
import com.yhxjs.magicpacket.utils.LimitUtil;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class LegalInterceptor implements HandlerInterceptor {

    @Resource
    private JwtUtil jwtUtil;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
        if (jwtUtil.getSessionId(request) == null) {
            Cookie cookie = new Cookie("tempId", request.getSession().getId());
            cookie.setPath("/");
            cookie.setMaxAge(60 * 60 * 24 * 7);
            response.addCookie(cookie);
        }
        if (!LimitUtil.getIllegalIPStatus(request)) {
            response.setContentType("application/json;charset=utf-8");
            response.getWriter().write(JSON.toJSONString(Result.error(Code.ACCESS_ILLEGAL, LimitUtil.getIllegalIPString(request))));
            return false;
        }
        return true;
    }
}
