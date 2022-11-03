package cn.nannar.mgr.nannarmgr.modular.system.user.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import org.springframework.data.annotation.Id;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * @author LTJ
 * @date 2022/10/24
 */
@Data
@TableName("sys_user")
public class SysUser {
    @TableId
    private Long id;

    @NotNull
    private String username;

    @NotNull
    private String password;

    private String avatar;

    private String email;

    private String phone;

    private Integer isLocked;

    private Integer isEnable;

    private Date createTime;

    private String createBy;

    private Date updateTime;

    private String updateBy;


}
