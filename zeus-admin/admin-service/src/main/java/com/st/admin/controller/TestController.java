package com.st.admin.controller;

import com.st.admin.domain.SysUser;
import com.st.admin.service.SysUserService;
import com.st.common.model.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author leon
 * @date 2024/2/24 01:36
 */
@RestController
@Api(tags = "后台管理系统的测试接口")
public class TestController {

    @Autowired
    private SysUserService sysUserService;

    @ApiOperation(value = "查询用户详情")
    @GetMapping("/user/info/{id}")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "用户的ID")
    })
    public ResponseResult<SysUser> getSysUserInfo(@PathVariable("id")Long id) {
        SysUser sysUser = sysUserService.getById(id);
        return ResponseResult.ok(sysUser);
    }
}
