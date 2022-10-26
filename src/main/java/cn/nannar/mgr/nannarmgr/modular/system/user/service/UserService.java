package cn.nannar.mgr.nannarmgr.modular.system.user.service;

import cn.nannar.mgr.nannarmgr.modular.system.user.entity.SysUser;
import org.springframework.cache.annotation.Cacheable;

/**
 * @author LTJ
 * @date 2022/10/24
 */
public interface UserService {

//    @Cacheable(cacheNames = "sys")
    SysUser getUser(String userName);
}
