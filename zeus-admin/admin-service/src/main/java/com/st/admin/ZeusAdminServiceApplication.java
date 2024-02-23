package com.st.admin;

import com.alicp.jetcache.anno.config.EnableMethodCache;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author leon
 * @date 2024/2/24 01:11
 */
@SpringBootApplication
@EnableDiscoveryClient
@MapperScan("com.st.admin.mapper")
@EnableMethodCache(basePackages = "com.st.admin.service.impl")
public class ZeusAdminServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(ZeusAdminServiceApplication.class, args);
    }
}
