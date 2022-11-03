package cn.nannar.mgr.nannarmgr.modular.system.user.service.impl;

import cn.nannar.mgr.nannarmgr.modular.security.UserBO;
import cn.nannar.mgr.nannarmgr.modular.system.user.dao.UserMapper;
import cn.nannar.mgr.nannarmgr.modular.system.user.entity.SysUser;
import cn.nannar.mgr.nannarmgr.modular.system.user.service.UserService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
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
public class UserServiceImpl extends ServiceImpl<UserMapper,SysUser> implements UserDetailsService, UserService {

    @Autowired
    @Lazy
    private AuthenticationManager authenticationManager;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        LambdaQueryWrapper<SysUser> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysUser::getUsername, username);
        SysUser sysUser = baseMapper.selectOne(wrapper);
        UserBO userBO = new UserBO(Collections.emptyList(), sysUser);
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

    @Override
    public void create(SysUser sysUser) {
        String unEncodedPwd = sysUser.getPassword();
        String encodePwd = passwordEncoder.encode(unEncodedPwd);
        sysUser.setPassword(encodePwd);
        baseMapper.insert(sysUser);
    }
}
