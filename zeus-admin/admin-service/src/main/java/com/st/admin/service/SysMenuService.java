package com.st.admin.service;

import com.st.admin.domain.SysMenu;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @author leon
 * @date 2024/2/24 01:06   
 */
public interface SysMenuService extends IService<SysMenu>{

    List<SysMenu> getMenusByUserId(Long userId);

}
