package com.st.admin.feign;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * @author leon
 * @date 2024/2/29 00:15
 */

@Data
public class JwtToken {
    /**
     * access_token
     */
    @JsonProperty("access_token")
    private String accessToken;

    /**
     * token类型
     */
    @JsonProperty("token_type")
    private String tokenType;

    /**
     * refresh_token
     */
    @JsonProperty("refresh_token")
    private String refreshToken;

    /**
     * 过期时间
     */
    @JsonProperty("expires_in")
    private Long expiresIn;

    private String scope;

    private String jti;

}
