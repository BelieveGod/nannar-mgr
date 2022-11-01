package cn.nannar.mgr.nannarmgr.config;

import cn.hutool.core.date.DatePattern;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.SerializationFeature;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

/**
 * @author LTJ
 * @date 2022/10/27
 */
@Configuration
@Slf4j
public class JacksonConfig {

    @Bean
    public Jackson2ObjectMapperBuilderCustomizer Jackson2ObjectMapperBuilderCustomizer(){
        Jackson2ObjectMapperBuilderCustomizer jackson2ObjectMapperBuilderCustomizer = new Jackson2ObjectMapperBuilderCustomizer() {
            @Override
            public void customize(Jackson2ObjectMapperBuilder jacksonObjectMapperBuilder) {
                log.info("objectMapper 的定制被执行了");
                jacksonObjectMapperBuilder.failOnEmptyBeans(false)
                        .failOnUnknownProperties(false)
                        .simpleDateFormat(DatePattern.NORM_DATETIME_PATTERN)
                        .featuresToDisable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,
                                SerializationFeature.WRITE_DATES_AS_TIMESTAMPS,
                                SerializationFeature.WRITE_DURATIONS_AS_TIMESTAMPS,
                                SerializationFeature.FAIL_ON_EMPTY_BEANS)
                        .simpleDateFormat(DatePattern.NORM_DATETIME_PATTERN);
            }
        };
        return jackson2ObjectMapperBuilderCustomizer;
    }
}
