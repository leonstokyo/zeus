package com.st.admin.service;

import com.st.admin.model.LoginResult;

/**
 * @author leon
 * @date 2024/2/29 00:07
 */
public interface SysLoginService {
    /**
     *
     * @param username 用户名
     * @param password 用户密码
     * @return
     */
    LoginResult login(String username, String password);
}
