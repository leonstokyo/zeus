package com.st.gateway.filter;

import com.alibaba.fastjson.JSONObject;
import com.google.common.net.HttpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Set;

/**
 * @author leon
 * @date 2024/2/12 00:29
 */
@Component
public class JwtCheckFilter implements GlobalFilter, Ordered {

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Value("${no.require.urls:/admin/login}")
    private Set<String> noRequireTokenUrls;
    /**
     *
     * 过滤器拦截到用户的请求后干什么
     * @param exchange
     * @param chain
     * @return
     */
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        // 1.该接口是否需要token才能访问
        if (!isRequireToken(exchange)) {
            return chain.filter(exchange);
        }
        // 2.取出用户的token
        String token = getUserToken(exchange);
        // 3.判断用户的token是否有效
        if (StringUtils.isEmpty(token)) {
            return buildNoAuthorizationResult(exchange);
        }
        Boolean hasKey = redisTemplate.hasKey(token);
        if (hasKey != null && hasKey) {
            return chain.filter(exchange);
        }
        return buildNoAuthorizationResult(exchange);
    }

    /**
     * 给用户响应一个没有token的错误
     * @return
     */
    private Mono<Void> buildNoAuthorizationResult(ServerWebExchange exchange) {
        ServerHttpResponse response = exchange.getResponse();
        response.getHeaders().set("Content-Type", "application/json");
        response.setStatusCode(HttpStatus.UNAUTHORIZED);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("error", "NoAuthorization");
        jsonObject.put("errorMsg", "Token is Null Error");
        DataBuffer wrap = response.bufferFactory().wrap(jsonObject.toJSONString().getBytes());
        return response.writeWith(Flux.just(wrap));

    }

    private String getUserToken(ServerWebExchange exchange) {
        String token = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
        return token == null ? null : token.replace("bearer ", "");
    }

    private boolean isRequireToken(ServerWebExchange exchange) {
        String path = exchange.getRequest().getURI().getPath();
        return !noRequireTokenUrls.contains(path);
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
