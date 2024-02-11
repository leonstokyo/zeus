package com.st.common.config.swagger;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author leon
 * @date 2024/2/12 02:25
 */
@Data
@ConfigurationProperties(prefix = "swagger2")
public class SwaggerProperties {

    private String basePackage;

    private String name;

    private String url;

    private String email;

    private String title;

    private String description;

    private String version;

    private String termsOfServiceUrl;
}
