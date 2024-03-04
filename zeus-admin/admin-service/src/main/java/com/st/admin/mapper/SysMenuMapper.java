package com.st.admin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.st.admin.domain.SysMenu;

import java.util.List;


/**
 * @author leon
 * @date 2024/2/24 01:06   
 */
public interface SysMenuMapper extends BaseMapper<SysMenu> {

    /**
     * 通过用户的ID查询用户的菜单数据
     * @param userId
     * @return
     */
    List<SysMenu> selectMenusByUserId(Long userId);
}