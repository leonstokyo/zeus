package com.st.auth.service.impl;

import com.st.auth.constant.LoginConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author leon
 * @date 2024/2/12 00:54
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        assert requestAttributes != null;
        String loginType = requestAttributes.getRequest().getParameter("login_type"); // 区分是后台人员还是用户
        if (StringUtils.isEmpty(loginType)) {
            throw new AuthenticationServiceException("登录类型不能为null");

        }
        UserDetails userDetails = null;
        try {
            switch (loginType) {
                case LoginConstant.ADMIN_TYPE:
                    userDetails = loadAdminUserByUsername(username);
                    break;
                case LoginConstant.MEMBER_TYPE:
                    userDetails =  loadMemberUserByUsername(username);
                    break;
                default:
                    throw new AuthenticationServiceException("暂不支持的登录方式:" + loginType);
            }
        } catch (IncorrectResultSizeDataAccessException e) {
            throw new UsernameNotFoundException("用户名" + username + "不存在");
        }
        return userDetails;
    }

    /**
     * 后台管理人员登录
     * @param username username
     * @return return
     */
    private UserDetails loadAdminUserByUsername(String username) {
        // 1. 使用用户名查询用户
        return jdbcTemplate.queryForObject(LoginConstant.QUERY_ADMIN_SQL, new RowMapper<User>() {
            @Override
            public User mapRow(@NonNull ResultSet resultSet, int i) throws SQLException {
                if (resultSet.wasNull()) {
                    throw new UsernameNotFoundException("用户名" + username + "不存在");
                }
                long id = resultSet.getLong("id"); // 用户id
                String password = resultSet.getString("password"); // 用户密码
                int status = resultSet.getInt("status");
                // 3. 封装成一个 UserDetails 对象
                return new User(
                        String.valueOf(id),
                        password,
                        status == 1,
                        true,
                        true,
                        true,
                        getSysUserPermission(id)

                );
            }
        }, username);
    }


    /**
     * 会员登录
     * @param username username
     * @return return
     */
    private UserDetails loadMemberUserByUsername(String username) {
        return null;
    }

    /**
     *  2. 查询用户对应的权限
     * 通过用户id 查询权限
     * @param id id
     * @return
     */
    private Collection<? extends GrantedAuthority> getSysUserPermission(long id) {
        String roleCode = jdbcTemplate.queryForObject(LoginConstant.QUERY_ROLE_CODE_SQL, String.class, id);
        List<String> permissions;
        if (LoginConstant.ADMIN_ROLE_CODE.equals(roleCode)) {
            // 1. 当用户为超级管理员时，拥有所有的权限数据
            permissions = jdbcTemplate.queryForList(LoginConstant.QUERY_ALL_PERMISSIONS, String.class);
        } else {
            // 2. 普通用户，使用角色 -> 权限数据
            permissions = jdbcTemplate.queryForList(LoginConstant.QUERY_PERMISSION_SQL, String.class, id);
        }
        if (permissions.isEmpty()) {
            return Collections.emptySet();
        }
        return permissions.stream()
                .distinct()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toSet());

    }

}
