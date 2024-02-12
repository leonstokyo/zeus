package com.st.common;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author leon
 * @date 2024/2/12 12:56
 */
@SpringBootApplication
@EnableDiscoveryClient
public class ZeusCommonApplication {
    public static void main(String[] args) {
        SpringApplication.run(ZeusCommonApplication.class, args);
    }
}
