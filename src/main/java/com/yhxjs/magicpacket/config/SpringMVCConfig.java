package com.yhxjs.magicpacket.config;

import com.yhxjs.magicpacket.interceptor.LegalInterceptor;
import com.yhxjs.magicpacket.interceptor.LoginInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;

@Configuration
public class SpringMVCConfig implements WebMvcConfigurer {

    @Resource
    private LegalInterceptor legalInterceptor;

    @Resource
    private LoginInterceptor loginInterceptor;

    @Override
    public void configurePathMatch(PathMatchConfigurer configurer) {
        configurer.addPathPrefix("/magic", item -> item.isAnnotationPresent(RestController.class));
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(legalInterceptor).addPathPatterns("/magic/**");
        registry.addInterceptor(loginInterceptor).addPathPatterns("/magic/**")
                .excludePathPatterns("/magic/imgCode", "/magic/user/login");
    }
}