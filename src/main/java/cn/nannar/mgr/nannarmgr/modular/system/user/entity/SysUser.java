package cn.nannar.mgr.nannarmgr.modular.system.user.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;

/**
 * @author LTJ
 * @date 2022/10/24
 */
@Data
public class SysUser {
    @Id
    private Long id;

    private String username;

    private String password;

    private Integer locked;

    private Integer enabled;
}
