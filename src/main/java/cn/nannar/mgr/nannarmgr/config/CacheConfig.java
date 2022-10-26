package cn.nannar.mgr.nannarmgr.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.jcache.JCacheCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import javax.cache.Caching;
import javax.cache.spi.CachingProvider;
import java.io.IOException;
import java.net.URI;

/**
 * @author LTJ
 * @date 2022/10/26
 */
@Configuration
@EnableCaching
@Slf4j
public class CacheConfig {

    /**
     * 又springboot 自动配置更好，配置可以外部化，并且有多customizer
     * @return
     * @throws IOException
     */
    @Deprecated
    CacheManager cacheManager()throws IOException{
        /* begin ==========创建一个java cahce 的 cachemanager============= */

        // Java Cache 加载 ehcache 作为实现方
        CachingProvider cachingProvider = Caching.getCachingProvider("org.ehcache.jsr107.EhcacheCachingProvider");
        // 加载ehcache3 的配置文件
        ClassPathResource classPathResource = new ClassPathResource("ehcache.xml");
        URI uri=null;
        try {
            uri = classPathResource.getURI();
        } catch (IOException e) {
            log.error("getting ehcache3 config file occurs a exception",e);
            throw e;
        }
        javax.cache.CacheManager jcacheManager = cachingProvider.getCacheManager(uri,null);
        /* end ============创建一个java cahce 的 cachemanager============ */

        /* begin ==========创建spring cache 抽象的，包装jcache的wrapper============= */

        JCacheCacheManager jCacheCacheManagerWrapper = new JCacheCacheManager(jcacheManager);
        /* end ============创建spring cache 抽象的，包装jcache的wrapper============ */
        log.info("基于ehcache3的缓存管理器bean已生成");
        return jCacheCacheManagerWrapper;
    }


}
