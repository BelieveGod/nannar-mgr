package cn.nannar.mgr.nannarmgr.common.web;

/**
 * @author LTJ
 * @date 2021/11/26
 */
public enum ResponseCode {
    /**
     * 请求成功
     */
    SUCCESS(200, "请求成功"),
    /**
     * 请求错误
     */
    ERROR(400,"请求错误"),
    /**
     * 未知错误
     */
    CRASH(500, "未知错误");

    private Integer code;
    private String description;

    ResponseCode(Integer code, String description) {
        this.code = code;
        this.description = description;
    }

    public Integer getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }
}
