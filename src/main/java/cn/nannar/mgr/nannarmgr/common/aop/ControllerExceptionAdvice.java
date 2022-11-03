package cn.nannar.mgr.nannarmgr.common.aop;

import cn.nannar.mgr.nannarmgr.common.util.JacksonUtil;
import cn.nannar.mgr.nannarmgr.common.web.RestResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

/**
 * @author LTJ
 * @date 2022/10/21
 */
@RestControllerAdvice
@Slf4j
public class ControllerExceptionAdvice {

    @ExceptionHandler(Exception.class)
    public RestResponse handleUncaught(Exception e, HttpServletRequest request) throws JsonProcessingException {
        log.error("Exception:request url:[{}],request params:[{}];",request.getRequestURI(), JacksonUtil.toJson(request.getParameterMap()));
        log.error("未捕获异常:", e);
        return RestResponse.fail("请求异常");
    }
}
