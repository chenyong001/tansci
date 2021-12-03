package com.tansci.security;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.tansci.domain.SysUser;
import com.tansci.service.SysUserService;
import com.tansci.utils.SecurityUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * @ClassName： UserDetailsServiceImpl.java
 * @ClassPath： com.tansci.security.UserDetailsServiceImpl.java
 * @Description： 用户认证
 * @Author： tanyp
 * @Date： 2021/10/22 17:30
 **/
@Slf4j
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private SysUserService sysUserService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            SysUser user = sysUserService.getOne(Wrappers.<SysUser>lambdaQuery().eq(SysUser::getUsername, username));
            if (Objects.isNull(user)) {
                throw new UsernameNotFoundException("用户名获取密码有误！");
            }

            // 获取角色
//            SysUserRole role = sysUserRoleService.getOne(Wrappers.<SysUserRole>lambdaQuery().eq(SysUserRole::getUserId, user.getId()));
//            if (Objects.nonNull(role)) {
                user.setRole("1");
//            }
            return new SecurityUtils(user);
        } catch (UsernameNotFoundException e) {
            log.error("=====用户权限认证异常===={}", e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

}
