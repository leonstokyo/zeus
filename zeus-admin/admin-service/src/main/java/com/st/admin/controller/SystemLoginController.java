package com.st.admin.controller;

import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author leon
 * @date 2024/2/28 22:30
 */
@RestController
@Api(tags = "登录控制器")
public class SystemLoginController {

    @PostMapping("/login")
    public String login() {
        return "dsadadas";
    }
}
