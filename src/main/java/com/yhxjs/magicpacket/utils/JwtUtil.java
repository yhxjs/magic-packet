package com.yhxjs.magicpacket.utils;

import com.alibaba.fastjson.JSON;
import com.yhxjs.magicpacket.pojo.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.Base64;
import java.util.Date;
import java.util.UUID;

@Component
public class JwtUtil {

    private static final String JWT_KEY = "yhxjs";

    private static final String AES_KEY = "yhxjsmagicpacket";

    private static final ThreadLocal<User> USER_THREAD_LOCAL = new ThreadLocal<>();

    public String createToken(Object data, long exp) {
        long currentTime = System.currentTimeMillis();
        long expTime = currentTime + exp;
        try {
            return Jwts.builder()
                    .setId(UUID.randomUUID() + "")
                    .setSubject(SHAUtil.encryptByAES(JSON.toJSONString(data), AES_KEY))
                    .setIssuer("奕凰轩祭侍")
                    .setIssuedAt(new Date(currentTime))
                    .setExpiration(new Date(expTime))
                    .signWith(SignatureAlgorithm.HS256, encodeSecret(JWT_KEY))
                    .compact();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public SecretKey encodeSecret(String key) {
        byte[] encode = Base64.getEncoder().encode(key.getBytes());
        return new SecretKeySpec(encode, 0, encode.length, "AES");
    }

    public <T> T parseToken(String token, Class<T> clazz) {
        Claims body = Jwts.parser()
                .setSigningKey(encodeSecret(JWT_KEY))
                .parseClaimsJws(token)
                .getBody();
        try {
            return JSON.parseObject(SHAUtil.decryptByAES(body.getSubject(), AES_KEY), clazz);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public String getSessionId(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies == null) {
            return null;
        }
        for (Cookie cookie : cookies) {
            if ("tempId".equals(cookie.getName())) {
                return cookie.getValue();
            }
        }
        return null;
    }

    public void setUser(User user) {
        USER_THREAD_LOCAL.set(user);
    }

    public User getUser() {
        return USER_THREAD_LOCAL.get();
    }

    public int getUserId() {
        return USER_THREAD_LOCAL.get().getId();
    }

    public void removeUser() {
        USER_THREAD_LOCAL.remove();
    }
}
