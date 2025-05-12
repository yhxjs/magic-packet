package com.yhxjs.magicpacket.utils;

import cn.hutool.core.date.DateUtil;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ThreadPoolExecutor;

@Slf4j
@Component
@EnableScheduling
public class LimitUtil {

    @Resource
    private ThreadPoolExecutor defaultExecutor;

    private static final int ILLEGAL_LIMIT_COUNT = 5;

    private static final int LOGIN_LIMIT_TIME = 10 * 60 * 1000;

    public static String getIpAddr(HttpServletRequest request) {
        final String unknownIP = "unknown";
        final String loopbackIP = "0:0:0:0:0:0:0:1";
        if (request == null) {
            return unknownIP;
        }
        String ip = request.getHeader("x-forwarded-for");

        if (StringUtils.isBlank(ip) || unknownIP.equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (StringUtils.isBlank(ip) || unknownIP.equalsIgnoreCase(ip)) {
            ip = request.getHeader("X-Forwarded-For");
            if (!StringUtils.isBlank(ip) && !unknownIP.equalsIgnoreCase(ip)) {
                int index = ip.indexOf(',');
                if (index != -1) {
                    ip = ip.substring(0, index);
                }
            }
        }
        if (StringUtils.isBlank(ip) || unknownIP.equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (StringUtils.isBlank(ip) || unknownIP.equalsIgnoreCase(ip)) {
            ip = request.getHeader("X-Real-IP");
        }
        if (StringUtils.isBlank(ip) || unknownIP.equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        if (loopbackIP.equals(ip)) {
            return "127.0.0.1";
        }
        if ("127.0.0.1".equals(ip) || "localhost".equalsIgnoreCase(ip) && StringUtils.isBlank(request.getRemoteAddr())) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }

    @Scheduled(initialDelay = 10 * 60 * 1000, fixedRate = 10 * 60 * 1000)
    protected void startClearOverDue() {
        defaultExecutor.execute(() -> {
            long nowTime = System.currentTimeMillis();
            for (Map.Entry<String, IllegalLimitIP> loginLimitIPEntry : LOGIN_LIMIT_IP_MAP.entrySet()) {
                if (nowTime - loginLimitIPEntry.getValue().getLastTime() > LOGIN_LIMIT_TIME) {
                    LOGIN_LIMIT_IP_MAP.remove(loginLimitIPEntry.getKey());
                }
            }
        });
    }

    private static final Map<String, IllegalLimitIP> ILLEGAL_LIMIT_IP_MAP = new ConcurrentHashMap<>();
    private static final Map<String, IllegalLimitIP> LOGIN_LIMIT_IP_MAP = new ConcurrentHashMap<>();

    public static boolean getIllegalIPStatus(HttpServletRequest req) {
        String ip = getIpAddr(req);
        IllegalLimitIP illegalLimitIP = ILLEGAL_LIMIT_IP_MAP.get(ip);
        return illegalLimitIP == null || illegalLimitIP.getCount() < ILLEGAL_LIMIT_COUNT;
    }

    public static boolean getLoginIPStatus(HttpServletRequest req) {
        String ip = getIpAddr(req);
        IllegalLimitIP illegalLimitIP = LOGIN_LIMIT_IP_MAP.get(ip);
        return illegalLimitIP == null || illegalLimitIP.getCount() < ILLEGAL_LIMIT_COUNT;
    }

    public static String getIllegalIPString() {
        HttpServletRequest req = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
        return getIllegalIPString(req);
    }

    public static String getIllegalIPString(HttpServletRequest req) {
        String ip = getIpAddr(req);
        IllegalLimitIP illegalLimitIP = ILLEGAL_LIMIT_IP_MAP.get(ip);
        if (illegalLimitIP != null) {
            if (getIllegalIPStatus(req)) {
                illegalLimitIP.setCount(illegalLimitIP.getCount() + 1);
                illegalLimitIP.setLastTime(System.currentTimeMillis());
                return "非法访问！";
            }
            if (illegalLimitIP.getCount() == ILLEGAL_LIMIT_COUNT) {
                log.warn("({})非法访问次数过多，封禁访问时间为：{}", ip, DateUtil.format(new Date(illegalLimitIP.getLastTime()), "yyyy-MM-dd'T'HH:mm:ss"));
                illegalLimitIP.setCount(ILLEGAL_LIMIT_COUNT + 1);
            }
            return "非法访问次数过多，封禁访问时间为：" + DateUtil.format(new Date(illegalLimitIP.getLastTime()), "yyyy-MM-dd'T'HH:mm:ss") + "，请稍后再试！";
        }
        ILLEGAL_LIMIT_IP_MAP.put(ip, new IllegalLimitIP(1, System.currentTimeMillis()));
        return "非法访问！";
    }

    public static String getLoginIPString(HttpServletRequest req, String name) {
        String ip = getIpAddr(req);
        IllegalLimitIP illegalLimitIP = LOGIN_LIMIT_IP_MAP.get(ip);
        long now = System.currentTimeMillis();
        if (illegalLimitIP != null && now - illegalLimitIP.getLastTime() <= LOGIN_LIMIT_TIME) {
            illegalLimitIP.setCount(illegalLimitIP.getCount() + 1);
            illegalLimitIP.setLastTime(now);
            if (getLoginIPStatus(req)) {
                return "用户名或者密码错误！";
            }
            log.warn("({}，{})登录失败次数过多，最后尝试登录时间为：{}", ip, name, DateUtil.format(new Date(now), "yyyy-MM-dd'T'HH:mm:ss"));
            return "尝试登录次数过多，请稍后再试！";
        }
        LOGIN_LIMIT_IP_MAP.put(ip, new IllegalLimitIP(1, now));
        return "用户名或者密码错误！";
    }

    @Scheduled(cron = "0 0 3 * * ?")
    protected void startClearOverDueIllegalIP() {
        defaultExecutor.execute(ILLEGAL_LIMIT_IP_MAP::clear);
    }

    /* 非法访问的IP类 */
    @Getter
    @Setter
    private static class IllegalLimitIP {
        private int count;
        private long lastTime;

        IllegalLimitIP(int count, long lastTime) {
            this.count = count;
            this.lastTime = lastTime;
        }
    }
}
