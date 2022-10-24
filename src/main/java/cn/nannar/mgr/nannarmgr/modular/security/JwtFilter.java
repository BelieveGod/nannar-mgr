package cn.nannar.mgr.nannarmgr.modular.security;

import cn.hutool.core.lang.Assert;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationDetailsSource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author LTJ
 * @date 2022/10/24
 */
@Slf4j
public class JwtFilter extends OncePerRequestFilter {
    private final String TOKEN_PREFIX = "Bearer ";


    private AuthenticationManager authenticationManager;

    private AuthenticationDetailsSource<HttpServletRequest, ?> authenticationDetailsSource;


    public JwtFilter(AuthenticationManager authenticationManager) {
        this(authenticationManager, new WebAuthenticationDetailsSource());
    }

    public JwtFilter(AuthenticationManager authenticationManager, AuthenticationDetailsSource<HttpServletRequest, ?> authenticationDetailsSource) {
        Assert.notNull(authenticationManager, "authenticationManager cannot be null");
        Assert.notNull(authenticationDetailsSource, "authenticationDetailsSource cannot be null");
        this.authenticationManager = authenticationManager;
        this.authenticationDetailsSource=authenticationDetailsSource;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // 判断是否已经有验证过来的Authentication
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication!=null && authentication.isAuthenticated()){
            filterChain.doFilter(request, response);
            return;
        }

        // 获取JWT的Authencication
        String header = request.getHeader(HttpHeaders.AUTHORIZATION);
        if(header==null || !header.startsWith(TOKEN_PREFIX)){
            filterChain.doFilter(request, response);
            return;
        }

        String jws = header.substring(TOKEN_PREFIX.length());
        JwtAuthenticationToken jwtAuthenticationToken = new JwtAuthenticationToken(jws);
        try {
            Authentication authResult = this.authenticationManager.authenticate(jwtAuthenticationToken);
            SecurityContextHolder.getContext().setAuthentication(authResult);
            // 验证成功后钩子
            log.info("JWT 验证成功");
        } catch (AuthenticationException e) {
            log.info("JWT 验证失败",e);
            // 验证失败
            // 清理holder
            // 失败操作的钩子
        }
        filterChain.doFilter(request, response);

    }
}
