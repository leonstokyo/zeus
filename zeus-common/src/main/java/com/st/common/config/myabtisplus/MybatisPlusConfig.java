package com.st.common.config.myabtisplus;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.core.incrementer.IKeyGenerator;
import com.baomidou.mybatisplus.extension.incrementer.H2KeyGenerator;
import com.baomidou.mybatisplus.extension.plugins.OptimisticLockerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.springframework.context.annotation.Configuration;

/**
 * @author leon
 * @date 2024/2/12 01:54
 */
@Configuration
public class MybatisPlusConfig {
    /**
     * 分页插件
     */
    public PaginationInterceptor paginationInterceptor() {
        PaginationInterceptor paginationInterceptor = new PaginationInterceptor();
        paginationInterceptor.setDbType(DbType.MYSQL);

        return paginationInterceptor;
    }

    /**
     * 乐观锁
     */
    public OptimisticLockerInterceptor optimisticLockerInterceptor() {
        return new OptimisticLockerInterceptor();
    }
    /**
     * 主键序列生成
     */
    public IKeyGenerator iKeyGenerator() {
        H2KeyGenerator h2KeyGenerator = new H2KeyGenerator();
        return h2KeyGenerator;
    }
}
