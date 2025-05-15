package org.yjl.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

/**
 * Entity基类
 *
 * @author Lion Li
 */
@Data
public class BaseEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;
    @Schema(description = "主键id")
    private String id;
    @Schema(description = "创建人")
    private String createBy;
    @Schema(description = "创建时间")
    private Date createTime;
    @Schema(description = "更新人")
    private String updateBy;
    @Schema(description = "更新时间")
    private Date updateTime;

}
