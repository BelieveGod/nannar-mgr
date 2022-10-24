package cn.nannar.mgr.nannarmgr.modular.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.util.Assert;

import java.util.Collection;

/**
 * @author LTJ
 * @date 2022/10/24
 */
@Slf4j
public class JwtAuthenticationToken extends AbstractAuthenticationToken {

    private String jws;
    private final Object principal;

    public JwtAuthenticationToken(String jws ) {
        super(null);
        this.jws=jws;
        this.principal = retrieveUser(jws);
        setAuthenticated(false);
    }

    public JwtAuthenticationToken(String jws,Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.jws=jws;
        principal = retrieveUser(jws);
        super.setAuthenticated(true);
    }

    @Override
    public Object getCredentials() {
        return jws;
    }

    @Override
    public Object getPrincipal() {
        return principal;
    }

    @Override
    public void setAuthenticated(boolean isAuthenticated) {
        Assert.isTrue(!isAuthenticated,
                "Cannot set this token to trusted - use constructor which takes a GrantedAuthority list instead");
        super.setAuthenticated(false);
    }

    private String retrieveUser(String jws){
        int i = jws.lastIndexOf(".");
        String jwt = jws.substring(0, i+1 );
        System.out.println("jwt = " + jwt);
        String subject = "";
        try {
            Jwt<Header, Claims> headerClaimsJwt = Jwts.parserBuilder().build().parseClaimsJwt(jwt);
            subject = headerClaimsJwt.getBody().getSubject();
        } catch (Exception e) {
            log.error("",e);
        }
        return subject;
    }
}
