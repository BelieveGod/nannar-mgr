package cn.nannar.mgr.nannarmgr.common.web;

import lombok.Data;

/**
 * @author LTJ
 * @date 2021/11/26
 */
@Data
public class RestResponse {

    private Integer code;
    private String msg;
    private Object data;

    public RestResponse(Integer code, String msg, Object data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public RestResponse() {
    }

    public static RestResponse success(String msg, Object data){
        RestResponse restResponse = new RestResponse(ResponseCode.SUCCESS.getCode(), msg, data);
        return restResponse;
    }

    public static RestResponse success(Object data){
        RestResponse restResponse = new RestResponse(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getDescription(), data);
        return restResponse;
    }

    public static RestResponse success(String msg){
        RestResponse restResponse = new RestResponse(ResponseCode.SUCCESS.getCode(), msg, null);
        return restResponse;
    }

    public static RestResponse success(){
        RestResponse restResponse = new RestResponse(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getDescription(), null);
        return restResponse;
    }

    public static RestResponse fail(String msg, Object data){
        RestResponse restResponse = new RestResponse(ResponseCode.ERROR.getCode(), msg, data);
        return restResponse;
    }

    public static RestResponse fail(Object data){
        RestResponse restResponse = new RestResponse(ResponseCode.ERROR.getCode(), ResponseCode.ERROR.getDescription(), data);
        return restResponse;
    }

    public static RestResponse fail(String msg){
        RestResponse restResponse = new RestResponse(ResponseCode.ERROR.getCode(), msg, null);
        return restResponse;
    }

    public static RestResponse fail(){
        RestResponse restResponse = new RestResponse(ResponseCode.ERROR.getCode(), ResponseCode.ERROR.getDescription(), null);
        return restResponse;
    }
}
