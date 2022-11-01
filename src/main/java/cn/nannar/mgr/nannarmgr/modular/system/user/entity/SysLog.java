package cn.nannar.mgr.nannarmgr.modular.system.user.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;

import java.util.Date;

/**
 * @author LTJ
 * @date 2022/10/31
 */
@Data
@Document(indexName = "sys")
public class SysLog {
    @Id()
    private Long id;
    @Field
    private Date logTime;
    @Field
    private String username;
    @Field
    private String action;
}
