package cn.nannar.mgr.nannarmgr.modular.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import lombok.extern.slf4j.Slf4j;

import java.security.Key;

/**
 * @author LTJ
 * @date 2022/5/31
 */
@Slf4j
public class JwtHelper {

    private Key key;

    public JwtHelper(Key key) {
        this.key = key;
    }

    public JwtHelper(String base64UrlKey){
        key = Keys.hmacShaKeyFor(Decoders.BASE64URL.decode(base64UrlKey));
    }

    public String createJWS(String userName){
        String compact = Jwts.builder().setSubject(userName).signWith(key).compact();
        System.out.println("compact = " + compact);
        return compact;
    }

    public boolean parseJWS(String jws){
        log.info("要解析的jws:{}", jws);
        try {
            Jws<Claims> claimsJws = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(jws);
            System.out.println("claimsJws.getBody().getSubject() = " + claimsJws.getBody().getSubject());
            return true;
        } catch (ExpiredJwtException e) {
            e.printStackTrace();
        } catch (UnsupportedJwtException e) {
            e.printStackTrace();
        } catch (MalformedJwtException e) {
            e.printStackTrace();
        } catch (SignatureException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
        return false;
    }
}
