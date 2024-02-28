package com.st.admin.controller;

import com.st.admin.model.LoginResult;
import com.st.admin.service.SysLoginService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author leon
 * @date 2024/2/28 22:30
 */
@RestController
@Api(tags = "登录控制器")
public class SysLoginController {

    @Autowired
    private SysLoginService sysLoginService;

    @PostMapping("/login")
    @ApiOperation(value = "后台管理人员用户登录")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(name = "username", value = "用户名称"),
                    @ApiImplicitParam(name = "password", value = "用户密码")
            }
    )
    public LoginResult login(@RequestParam String username, @RequestParam String password) {
        return sysLoginService.login(username, password);
    }
}
