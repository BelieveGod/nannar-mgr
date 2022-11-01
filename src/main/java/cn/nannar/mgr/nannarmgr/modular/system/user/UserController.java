package cn.nannar.mgr.nannarmgr.modular.system.user;

import cn.nannar.mgr.nannarmgr.common.util.RedisUtil;
import cn.nannar.mgr.nannarmgr.common.web.RestResponse;
import cn.nannar.mgr.nannarmgr.modular.security.JwtHelper;
import cn.nannar.mgr.nannarmgr.modular.system.user.entity.SysLog;
import cn.nannar.mgr.nannarmgr.modular.system.user.entity.SysUser;
import cn.nannar.mgr.nannarmgr.modular.system.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.query.Criteria;
import org.springframework.data.elasticsearch.core.query.CriteriaQuery;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.security.Principal;
import java.util.Date;
import java.util.Enumeration;

/**
 * @author LTJ
 * @date 2022/10/21
 */
@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {
    @Autowired
    @Lazy
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtHelper jwtHelper;

    @Autowired
    private UserService userService;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private ElasticsearchRestTemplate elasticsearchRestTemplate;

    @PostMapping("/login")
    public RestResponse login(@RequestParam String username, @RequestParam String password){
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(username, password);
        try {
            Authentication authenticate = authenticationManager.authenticate(usernamePasswordAuthenticationToken);

        } catch (AuthenticationException e) {
            e.printStackTrace();
            return RestResponse.fail();
        }

        // 成功后触发会话管理
        // 保存当前的security context 到securtiycontext repository，http.getshareobject
        return RestResponse.success(jwtHelper.createJWS(username));
    }


    @GetMapping("/currentUser")
    public RestResponse currentUser(HttpSession httpSession, Principal principal,HttpServletRequest request){
        String id = httpSession.getId();
        log.info("http session id:{}", id);
        Enumeration<String> attributeNames = httpSession.getAttributeNames();
        while (attributeNames.hasMoreElements()) {
            String attrName = attributeNames.nextElement();
            log.info("session attr {} : {}", attrName,httpSession.getAttribute(attrName));
        }
        log.info("pricipal:{}", principal.getName());
        Cookie[] cookies = request.getCookies();
        if(cookies!=null){
            for (Cookie cookie : cookies) {
                log.info("cookie {}:{} domain:{} path:{},secure:{},httponly:{},maxage:{}", cookie.getName(), cookie.getValue(), cookie.getDomain(), cookie.getPath(), cookie.getSecure(), cookie.isHttpOnly(), cookie.getMaxAge());
            }
        }
        return RestResponse.success(principal.getName());
    }

    @PostMapping("/logout")
    public RestResponse loout(HttpServletRequest request, HttpServletResponse response){
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();
        new SecurityContextLogoutHandler().logout(request,response,authentication);
        return RestResponse.success();
    }

    @GetMapping("/get")
    public RestResponse getUser(@RequestParam String username){
        SysUser user = userService.getUser(username);
        RedisUtil.setValue("redispre:"+username,user);
        SysUser o = RedisUtil.getValue("redispre:" + username, SysUser.class);
        System.out.println("o.getClass().getName() = " + o.getClass().getName());
//        mongoTemplate.insert(o);
        SysLog sysLog = new SysLog();
        sysLog.setId(1L);
        sysLog.setAction("获取用户");
        sysLog.setUsername(username);
        sysLog.setLogTime(new Date());
        elasticsearchRestTemplate.save(sysLog);

        Criteria criteria = new Criteria("username").is(username);
        CriteriaQuery criteriaQuery = new CriteriaQuery(criteria);
        SearchHit<SysLog> sysLogSearchHit = elasticsearchRestTemplate.searchOne(criteriaQuery, SysLog.class);
        if(sysLogSearchHit!=null){
            log.info("elasticsearch 结果：{}", sysLogSearchHit.toString());

            SysLog content = sysLogSearchHit.getContent();
            if(content==null){
                log.warn("不存在elasticsearch的log");
            }else{
                log.info("elasticsearch的结果：{}", content.toString());
            }
        }else{
            log.warn("不存在elasticsearch的log");
        }

        return RestResponse.success(user);
    }
}
