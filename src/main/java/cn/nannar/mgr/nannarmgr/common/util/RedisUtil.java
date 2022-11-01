package cn.nannar.mgr.nannarmgr.common.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * @author LTJ
 * @date 2022/10/27
 */
@Component
@Slf4j
public class RedisUtil {
    private static RedisTemplate<String,String> redisTemplate;
    private static ObjectMapper objectMapper;

    @Autowired
    public void setRedisTemplate(RedisTemplate<String,String> redisTemplate){
        log.info("RedisUtil 注入 redisTemplate：{}",redisTemplate);
        RedisUtil.redisTemplate=redisTemplate;
    }

    @Autowired
    public void setObjectMapper(Optional<ObjectMapper> objectMapperOpt){
        ObjectMapper objectMapper = objectMapperOpt.orElse(new ObjectMapper());
        log.info("RedisUtil 注入 objectMapper：{}",objectMapper);
        RedisUtil.objectMapper=objectMapper;
    }

    private static void checkAndInit(){
        if(redisTemplate==null){
            synchronized (RedisUtil.class){
                if(redisTemplate==null){
                    redisTemplate = SpringContextHolder.getBean("redisTemplate");
                }
            }
        }
    }

    public static <T> T getValue(String key,Class<T> type) {
        String s = redisTemplate.opsForValue().get(key);
        T t = null;
        try {
            t = objectMapper.readValue(s, type);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return t;
    }

    public static void setValue(String key,Object value){
        String valueStr=null;
        if(value instanceof String){
            valueStr=(String)value;
        }else{
            try {
                valueStr = objectMapper.writeValueAsString(value);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        }
        redisTemplate.opsForValue().set(key,valueStr);
    }
}
