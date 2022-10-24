package cn.nannar.mgr.nannarmgr.modular.system.user.entity;

import lombok.Data;

/**
 * @author LTJ
 * @date 2022/10/24
 */
@Data
public class SysUser {
    private Long id;

    private String username;

    private String password;

    private Integer locked;

    private Integer enabled;
}
