package cn.nannar.mgr.nannarmgr.config;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.Formatter;
import org.springframework.format.FormatterRegistry;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * @author LTJ
 * @date 2022/10/21
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Override
    public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        // 修改fastjson
    }

    // 加入时间解析器

    /**
     * 处理时间格式
     * @param registry
     */
    @Override
    public void addFormatters(FormatterRegistry registry) {
        Formatter<Date> formatter = new Formatter<Date>() {
            @Override
            public Date parse(String text, Locale locale) throws ParseException {
                DateTime parse = DateUtil.parse(text);
                return parse;
            }

            @Override
            public String print(Date object, Locale locale) {
                String format = DateUtil.format(object, DatePattern.NORM_DATETIME_PATTERN);
                return format;
            }
        };
        registry.addFormatter(formatter);
    }

    /**
     * 处理跨域
     * allowCredentials is not enabled by default,
     * since that establishes a trust level that exposes sensitive user-specific
     * information (such as cookies and CSRF tokens) and
     * should only be used where appropriate. When it is enabled either
     * allowOrigins must be set to one or more specific domain
     * (but not the special value "*") or alternatively the allowOriginPatterns property
     * may be used to match to a dynamic set of origins.
     * @param registry
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOriginPatterns("*")
                .allowedMethods("*")
                .allowedHeaders("*")
                .exposedHeaders("*")
                .allowCredentials(false).maxAge(3600);
    }
}
