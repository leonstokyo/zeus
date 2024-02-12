package com.st.common.controller;

import com.st.common.model.ResponseResult;
import io.swagger.annotations.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author leon
 * @date 2024/2/12 13:11
 */
@RestController
@Api(tags = "ZeusCommon测试接口")
public class TestController {

    @GetMapping("/common/test")
    @ApiOperation(value = "测试方法", authorizations = {@Authorization("Authorization")})
    @ApiImplicitParams({
            @ApiImplicitParam(name = "param", value = "参数1", dataType = "String", paramType = "query", example = "paramValue"),
            @ApiImplicitParam(name = "param1", value = "参数2", dataType = "String", paramType = "query", example = "paramValue")
    })
    public ResponseResult<String> testMethod(String param, String param1) {
       return ResponseResult.ok("ok");
    }
}
