package cn.nannar.mgr.nannarmgr.common.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * @author LTJ
 * @date 2022/11/3
 */
@Component
@Slf4j
public class JacksonUtil {
    private static ObjectMapper objectMapper;

    @Autowired
    public void setObjectMapper(Optional<ObjectMapper> objectMapperOpt){
        ObjectMapper objectMapper = objectMapperOpt.orElse(new ObjectMapper());
        log.info("JacksonUtil 注入 objectMapper：{}", objectMapper);
        JacksonUtil.objectMapper=objectMapper;
    }

    public static String toJson(Object obj) throws JsonProcessingException {
        return objectMapper.writeValueAsString(obj);
    }
}
