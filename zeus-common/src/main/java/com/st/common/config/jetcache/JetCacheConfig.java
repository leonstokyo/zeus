package com.st.common.config.jetcache;

import com.alicp.jetcache.anno.config.EnableCreateCacheAnnotation;
import com.alicp.jetcache.anno.config.EnableMethodCache;
import org.springframework.context.annotation.Configuration;

/**
 * @author leon
 * @date 2024/2/12 01:53
 */
@Configuration
@EnableCreateCacheAnnotation
@EnableMethodCache(basePackages = "com.st.service.impl")
public class JetCacheConfig {
}
