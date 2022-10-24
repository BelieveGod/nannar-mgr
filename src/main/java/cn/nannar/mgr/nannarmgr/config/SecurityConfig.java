package cn.nannar.mgr.nannarmgr.config;

import cn.nannar.mgr.nannarmgr.config.properties.NannarProperties;
import cn.nannar.mgr.nannarmgr.modular.security.JwtAuthenticationProvider;
import cn.nannar.mgr.nannarmgr.modular.security.JwtFilter;
import cn.nannar.mgr.nannarmgr.modular.security.JwtHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;

/**
 * @author LTJ
 * @date 2022/10/21
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private NannarProperties nannarProperties;


    @Override
    public void configure(WebSecurity web) throws Exception {
        // 忽略静态资源的url
        web.ignoring().antMatchers("/resource/**");
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        JwtFilter jwtFilter = new JwtFilter(authenticationManagerBean());

        http.authorizeRequests().antMatchers("/user/login").anonymous()
                .anyRequest().authenticated()
                .and()
                // 禁用csrf
                .csrf().disable()
                // 使用JWT ,不需要会话管理
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilterAt(jwtFilter, UsernamePasswordAuthenticationFilter.class);
    }



    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        JwtAuthenticationProvider jwtAuthenticationProvider = new JwtAuthenticationProvider(jwtHelper());

        auth.userDetailsService(userDetailsService);
        auth.authenticationProvider(jwtAuthenticationProvider);
    }

    @Bean("authenticationManager")
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        AuthenticationManager authenticationManager = super.authenticationManagerBean();

        return authenticationManager;
    }

    @Bean
    public JwtHelper jwtHelper(){
        return new JwtHelper(nannarProperties.getJwt().getBase64urlSecretKey());
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        return bCryptPasswordEncoder;
    }







}
