package com.st.admin.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.enums.ApiErrorCode;
import com.baomidou.mybatisplus.extension.exceptions.ApiException;
import com.st.admin.domain.SysMenu;
import com.st.admin.feign.JwtToken;
import com.st.admin.feign.OAuth2FeignClient;
import com.st.admin.model.LoginResult;
import com.st.admin.service.SysLoginService;
import com.st.admin.service.SysMenuService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.jwt.Jwt;
import org.springframework.security.jwt.JwtHelper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author leon
 * @date 2024/2/29 00:09
 */

@Service
@Slf4j
public class SysLoginServiceImpl implements SysLoginService {

    @Autowired
    private OAuth2FeignClient oAuth2FeignClient;

    @Autowired
    private SysMenuService sysMenuService;

    @Value("${basic.token:Basic Y29pbi1hcGk6Y29pbi1zZWNyZXQ=}")
    private String basicToken;
    @Override
    public LoginResult login(String username, String password) {
        log.info("用户{}开始登录", username);
        // 1. 获取token 需要远程调用 authorization-server
        ResponseEntity<JwtToken> tokenResponseResult = oAuth2FeignClient.getToken("password", username, password, "admin_type", basicToken);
        if (tokenResponseResult.getStatusCode() != HttpStatus.OK) {
            throw new ApiException(ApiErrorCode.FAILED);
        }
        JwtToken jwtToken = tokenResponseResult.getBody();
        log.info(" 远程调用授权服务器成功，获取的token为{}", JSON.toJSONString(jwtToken, true));
        assert jwtToken != null;
        String token = jwtToken.getAccessToken();
        // 2.查询菜单数据
        Jwt jwt = JwtHelper.decode(token);
        String jwtJson = jwt.getClaims();
        JSONObject jwtJsonObject = JSON.parseObject(jwtJson);
        Long userId = Long.valueOf(jwtJsonObject.getString("user_name"));
        List<SysMenu> menus = sysMenuService.getMenusByUserId(userId);
        // 3. 权限数据怎么查询 -- 不需要查询了，jwt里面包含了
        JSONArray authoritiesJsonArray = jwtJsonObject.getJSONArray("authorities");
        List<SimpleGrantedAuthority> authorities = authoritiesJsonArray.stream()
                .map(authoritiesJson -> new SimpleGrantedAuthority(authoritiesJson.toString()))
                .collect(Collectors.toList());
        return new LoginResult(token, menus, authorities);
    }
}
