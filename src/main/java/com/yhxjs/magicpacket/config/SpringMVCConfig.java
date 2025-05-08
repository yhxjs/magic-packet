package com.yhxjs.magicpacket.config;

import com.yhxjs.magicpacket.interceptor.LegalInterceptor;
import com.yhxjs.magicpacket.interceptor.LoginInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;

@Configuration
public class SpringMVCConfig implements WebMvcConfigurer {

    @Resource
    private LegalInterceptor legalInterceptor;

    @Resource
    private LoginInterceptor loginInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(legalInterceptor).addPathPatterns("/**");
        registry.addInterceptor(loginInterceptor).addPathPatterns("/**")
                .excludePathPatterns("/imgCode", "/user/login");
    }
}