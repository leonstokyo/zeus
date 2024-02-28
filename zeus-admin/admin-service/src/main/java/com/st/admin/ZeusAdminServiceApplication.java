package com.st.admin;

import com.alicp.jetcache.anno.config.EnableMethodCache;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author leon
 * @date 2024/2/24 01:11
 */
@SpringBootApplication
@EnableDiscoveryClient
@MapperScan("com.st.admin.mapper")
@EnableMethodCache(basePackages = "com.st.admin.service.impl")
@ComponentScan(basePackages = {"com.st.admin", "com.st.common"})
@EnableFeignClients
public class ZeusAdminServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(ZeusAdminServiceApplication.class, args);
    }
}
