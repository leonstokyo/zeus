package com.st.admin.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.st.admin.mapper.SysRoleMapper;
import com.st.admin.domain.SysRole;
import com.st.admin.service.SysRoleService;
import org.springframework.util.StringUtils;

/**
 * @author leon
 * @date 2024/2/24 01:06   
 */
@Service
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements SysRoleService{

    @Autowired
    private SysRoleMapper sysRoleMapper;
    @Override
    public boolean isSuperAdmin(Long userId) {
        // 当用户的角色code 为 ROLE_ADMIN 时，该用户为超级管理员
        String roleCode = sysRoleMapper.getUserRoleCode(userId);
        return StringUtils.hasLength(roleCode) && "ROLE_ADMIN".equals(roleCode);
    }
}
