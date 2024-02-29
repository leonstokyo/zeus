package com.st.admin.controller;

import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.st.admin.domain.SysPrivilege;
import com.st.admin.service.SysPrivilegeService;
import com.st.common.model.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;

/**
 * @author leon
 * @date 2024/3/1 00:41
 */
@RestController
@RequestMapping("/privileges")
@Api(tags = "权限的管理")
public class SysPrivilegeController {

    @Autowired
    private SysPrivilegeService sysPrivilegeService ;

    /**
     * <h2>权限数据的分页查询</h2>
     * @param page
     **/
    @ApiOperation(value = "分页查询权限数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "current" ,value = "当前页") ,
            @ApiImplicitParam(name = "size" ,value = "每页显示的大小")
    })
    @PreAuthorize("hasAuthority('sys_privilege_query')")
    @GetMapping
    public ResponseResult<Page<SysPrivilege>> findByPage(@ApiIgnore Page<SysPrivilege> page) {
        // 查询时，我们将最近新增的、修改的数据优先展示-> 排序->lastUpdateTime
        page.addOrder(OrderItem.desc("last_update_time"));
        Page<SysPrivilege> result = sysPrivilegeService.page(page);
        return ResponseResult.ok(result);
    }

    @PostMapping
    @ApiOperation(value = "新增一个权限")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sysPrivilege" ,value = "sysPrivilege 的json数据")
    })
    @PreAuthorize("hasAuthority('sys_privilege_create')")
    public ResponseResult<?> add(@RequestBody @Validated SysPrivilege sysPrivilege){
        boolean save = sysPrivilegeService.save(sysPrivilege);
        if(save){
            return ResponseResult.ok("新增成功") ;
        }
        return  ResponseResult.fail("新增失败") ;
    }

    @PatchMapping
    @ApiOperation(value = "修改一个权限")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sysPrivilege" ,value = "sysPrivilege 的json数据")
    })
    @PreAuthorize("hasAuthority('sys_privilege_update')")
    public ResponseResult<?> update(@RequestBody @Validated SysPrivilege sysPrivilege){
        boolean save = sysPrivilegeService.updateById(sysPrivilege);
        if(save){
            return ResponseResult.ok("修改成功") ;
        }
        return  ResponseResult.fail("修改失败") ;
    }

    @PostMapping("/delete")
    @ApiOperation(value = "删除一个权限")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sysPrivilege" ,value = "权限id的str集合")
    })
    @PreAuthorize("hasAuthority('sys_privilege_delete')")
    public ResponseResult<?> delete(List<String> ids) {
        ids.forEach(id -> sysPrivilegeService.removeById(id));
        return ResponseResult.ok("删除成功");
    }
}
