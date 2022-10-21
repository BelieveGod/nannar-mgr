package cn.nannar.mgr.nannarmgr.modular.system.user;

import cn.nannar.mgr.nannarmgr.common.web.RestResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author LTJ
 * @date 2022/10/21
 */
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    @Lazy
    private AuthenticationManager authenticationManager;

    @PostMapping("/login")
    public RestResponse login(String username,String password){
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(username, password);
        try {
            Authentication authenticate = authenticationManager.authenticate(usernamePasswordAuthenticationToken);

        } catch (AuthenticationException e) {
            e.printStackTrace();
            return RestResponse.fail();
        }
        return RestResponse.success();
    }

    @PostMapping("/logout")
    public RestResponse loout(HttpServletRequest request, HttpServletResponse response){
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();
        new SecurityContextLogoutHandler().logout(request,response,authentication);
        return RestResponse.success();
    }
}
