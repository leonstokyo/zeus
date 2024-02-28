package com.st.admin.service.impl;

import com.st.admin.service.SysRoleService;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.st.admin.domain.SysMenu;
import com.st.admin.mapper.SysMenuMapper;
import com.st.admin.service.SysMenuService;

/**
 * @author leon
 * @date 2024/2/24 01:06   
 */
@Service
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenu> implements SysMenuService{

    @Autowired
    private SysRoleService sysRoleService;

    @Autowired
    private SysMenuMapper sysMenuMapper;
    /**
     * 通过用户ID 查询用户菜单数据
     */
    @Override
    public List<SysMenu> getMenusByUserId(Long userId) {
        // 1. 如果该用户是一个超级管理员 --> 拥有所有的菜单
        if (sysRoleService.isSuperAdmin(userId)) {
            return list(); // 查询所有
        }
        // 2. 如果该用户不是超级管理员 --> 查询角色 -->查询菜单
        return sysMenuMapper.selectMenusByUserId(userId);

    }


}
