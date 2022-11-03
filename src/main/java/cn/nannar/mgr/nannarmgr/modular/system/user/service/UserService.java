package cn.nannar.mgr.nannarmgr.modular.system.user.service;

import cn.nannar.mgr.nannarmgr.modular.system.user.entity.SysUser;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.cache.annotation.Cacheable;

/**
 * @author LTJ
 * @date 2022/10/24
 */
public interface UserService extends IService<SysUser> {

//    @Cacheable(cacheNames = "sys")
    SysUser getUser(String userName);

    void create(SysUser sysUser);
}
