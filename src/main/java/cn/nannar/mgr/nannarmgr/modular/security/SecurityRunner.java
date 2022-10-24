package cn.nannar.mgr.nannarmgr.modular.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;
import org.springframework.stereotype.Component;

/**
 * @author LTJ
 * @date 2022/10/24
 */
@Component
@Slf4j
public class SecurityRunner implements ApplicationRunner , ApplicationContextAware {
    private ApplicationContext applicationContext;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        Object bean = applicationContext.getBean(AbstractSecurityWebApplicationInitializer.DEFAULT_FILTER_NAME);
        if(bean!=null){
            log.info("securtiy filter chain exist)");
        }else{
            log.warn("securtiy filter chain not exist)");
        }
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
