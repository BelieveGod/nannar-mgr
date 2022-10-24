package cn.nannar.mgr.nannarmgr.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;
import org.springframework.stereotype.Component;

/**
 * @author LTJ
 * @date 2022/10/21
 */
@Data
@ConfigurationProperties("nannar")
@Component
public class NannarProperties {

    @NestedConfigurationProperty
    private JwtProperties jwt;


    @Data
    public static class JwtProperties{
        private String base64urlSecretKey;
    }
}
