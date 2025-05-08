package com.yhxjs.magicpacket;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@MapperScan("com.yhxjs.magicpacket.mapper")
@SpringBootApplication
public class MagicPacketApplication {

    public static void main(String[] args) {
        SpringApplication.run(MagicPacketApplication.class, args);
    }
}
