package cn.nannar.mgr.nannarmgr.modular.security;

import cn.nannar.mgr.nannarmgr.modular.system.user.entity.SysUser;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

/**
 * @author LTJ
 * @date 2022/10/24
 */
public class UserBO implements UserDetails {
    private Collection<? extends GrantedAuthority> authorities;
    private SysUser sysUser;

    public UserBO(Collection<? extends GrantedAuthority> authorities, SysUser sysUser) {
        this.authorities = authorities;
        this.sysUser = sysUser;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return sysUser.getPassword();
    }

    @Override
    public String getUsername() {
        return sysUser.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return sysUser.getLocked().equals(0);
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return sysUser.getEnabled().equals(1);
    }

}
