package cn.nannar.mgr.nannarmgr.modular.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserCache;
import org.springframework.security.core.userdetails.cache.NullUserCache;

import java.util.Collections;

/**
 * @author LTJ
 * @date 2022/10/24
 */
@Slf4j
public class JwtAuthenticationProvider implements AuthenticationProvider {
    private UserCache userCache = new NullUserCache();
    private JwtHelper jwtHelper;

    public JwtAuthenticationProvider(JwtHelper jwtHelper) {
        this.jwtHelper = jwtHelper;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        if(!supports(authentication.getClass())){
            log.error("JWT 不支持的authentication");
            return null;
        }
        // 前置校验


        // 自定义校验

        // 后置校验

        // 返回验证成功的凭证
        return new JwtAuthenticationToken(authentication.getCredentials().toString(), Collections.emptyList());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return (JwtAuthenticationToken.class.isAssignableFrom(authentication));
    }
}
