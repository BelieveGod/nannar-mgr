package cn.nannar.mgr.nannarmgr.modular.system.user.service.impl;

import cn.nannar.mgr.nannarmgr.modular.security.UserBO;
import cn.nannar.mgr.nannarmgr.modular.system.user.entity.SysUser;
import cn.nannar.mgr.nannarmgr.modular.system.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashMap;

/**
 * @author LTJ
 * @date 2022/10/24
 */
@Service
@Slf4j
public class UserServiceImpl implements UserDetailsService, UserService {

    @Autowired
    @Lazy
    private AuthenticationManager authenticationManager;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        HashMap<String, SysUser> stringSysUserHashMap = new HashMap<>();
        SysUser sysUser = new SysUser();
        sysUser.setId(1L);
        sysUser.setUsername("user");
        String user123 = passwordEncoder.encode("user123");
        System.out.println("user123 = " + user123);
        sysUser.setPassword(user123);
        sysUser.setLocked(0);
        sysUser.setEnabled(1);
        stringSysUserHashMap.put(sysUser.getUsername(), sysUser);

        sysUser = new SysUser();
        sysUser.setId(2L);
        sysUser.setUsername("admin");
        String admin123 = passwordEncoder.encode("admin123");
        System.out.println("admin123 = " + admin123);
        sysUser.setPassword(admin123);
        sysUser.setLocked(0);
        sysUser.setEnabled(1);
        stringSysUserHashMap.put(sysUser.getUsername(), sysUser);

        SysUser sysUser1 = stringSysUserHashMap.get(username);
        UserBO userBO = new UserBO(Collections.emptyList(), sysUser1);
        return userBO;
    }

    @Override
    public SysUser getUser(String username) {
        log.info("getUser 被调用了");
        if("user".equals(username)){
            SysUser sysUser = new SysUser();
            sysUser.setUsername("user");
            sysUser.setPassword("*****");
            sysUser.setId(1L);
            return sysUser;
        }
        return null;
    }
}
