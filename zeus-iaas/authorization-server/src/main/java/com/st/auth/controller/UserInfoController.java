package com.st.auth.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

/**
 * @author leon
 * @date 2024/1/8 11:57
 */
@RestController
public class UserInfoController {

    @GetMapping("/user/info")
    public Principal userInfo(Principal principal) {
        // 使用threadLocal来实现的
        // Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return principal;
    }
}
