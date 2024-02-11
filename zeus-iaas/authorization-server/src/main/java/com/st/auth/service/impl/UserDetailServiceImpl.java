package com.st.auth.service.impl;

import com.st.auth.constant.LoginConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * @author leon
 * @date 2024/2/12 00:54
 */
@Service
public class UserDetailServiceImpl implements UserDetailsService {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        String loginType = requestAttributes.getRequest().getParameter("login_type"); // 区分是后台人员还是用户
        if (StringUtils.isEmpty(loginType)) {
            throw new AuthenticationServiceException("登录类型不能为null");

        }
        switch (loginType) {
            case LoginConstant.ADMIN_TYPE:
                return loadAdminUserByUsername(username);
            case LoginConstant.MEMBER_TYPE:
                return loadMemberUserByUsername(username);
            default:
                throw new AuthenticationServiceException("暂不支持的登录方式:" + loginType);
        }
    }

    private UserDetails loadMemberUserByUsername(String username) {

        return null;
    }

    private UserDetails loadAdminUserByUsername(String username) {
        return null;
    }
}
