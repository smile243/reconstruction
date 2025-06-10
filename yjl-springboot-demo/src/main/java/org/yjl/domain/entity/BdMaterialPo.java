package org.yjl.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 新物料
 * </p>
 *
 * @author yjl
 * @since 2025-06-10
 */
@Getter
@Setter
@ToString
@TableName("bd_material")
@Tag(name = "BdMaterialPo对象", description = "新物料")
public class BdMaterialPo implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 新物料
     */
    @Schema(description = "新物料")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 唯一标识符
     */
    @TableField("uid")
    @Schema(description = "唯一标识符")
    private String uid;

    /**
     * 创建时间
     */
    @Schema(description = "创建时间")
    @TableField("create_time")
    private Date createTime;

    /**
     * 更新时间
     */
    @Schema(description = "更新时间")
    @TableField("update_time")
    private Date updateTime;

    /**
     * 应用版本
     */
    @Schema(description = "应用版本")
    @TableField("app_version")
    private String appVersion;

    /**
     * 创建人
     */
    @TableField("creator")
    @Schema(description = "创建人")
    private String creator;

    /**
     * 流程状态
     */
    @Schema(description = "流程状态")
    @TableField("process_status")
    private String processStatus;

    /**
     * 编码
     */
    @TableField("fnumber")
    @Schema(description = "编码")
    private String fnumber;

    /**
     * 物料分类ID
     */
    @TableField("fgroupid")
    @Schema(description = "物料分类ID")
    private String fgroupid;

    /**
     * 控制策略
     */
    @Schema(description = "控制策略")
    @TableField("fctrlstrategy")
    private String fctrlstrategy;

    /**
     * 助记码
     */
    @Schema(description = "助记码")
    @TableField("fhelpcode")
    private String fhelpcode;

    /**
     * 基础单位
     */
    @TableField("fbaseunit")
    @Schema(description = "基础单位")
    private String fbaseunit;

    /**
     * 毛重
     */
    @Schema(description = "毛重")
    @TableField("fgrossweight")
    private String fgrossweight;

    /**
     * 净重
     */
    @Schema(description = "净重")
    @TableField("fnetweight")
    private String fnetweight;

    /**
     * 重量单位
     */
    @Schema(description = "重量单位")
    @TableField("fweightunit")
    private String fweightunit;

    /**
     * 长
     */
    @Schema(description = "长")
    @TableField("flength")
    private String flength;

    /**
     * 宽
     */
    @TableField("fwidth")
    @Schema(description = "宽")
    private String fwidth;

    /**
     * 高
     */
    @Schema(description = "高")
    @TableField("fheight")
    private String fheight;

    /**
     * 体积
     */
    @TableField("fvolume")
    @Schema(description = "体积")
    private String fvolume;

    /**
     * 长度单位
     */
    @Schema(description = "长度单位")
    @TableField("flengthunit")
    private String flengthunit;

    /**
     * 体积单位
     */
    @Schema(description = "体积单位")
    @TableField("fvolumeunit")
    private String fvolumeunit;

    /**
     * 可采购
     */
    @Schema(description = "可采购")
    @TableField("fispurchasing")
    private String fispurchasing;

    /**
     * 可销售
     */
    @TableField("fissales")
    @Schema(description = "可销售")
    private String fissales;

    /**
     * 是否计划
     */
    @TableField("fisplan")
    @Schema(description = "是否计划")
    private String fisplan;

    /**
     * 使用状态
     */
    @TableField("fenable")
    @Schema(description = "使用状态")
    private String fenable;

    /**
     * 数据状态
     */
    @TableField("fstatus")
    @Schema(description = "数据状态")
    private String fstatus;

    /**
     * 创建组织id
     */
    @Schema(description = "创建组织id")
    @TableField("fcreateorgid")
    private String fcreateorgid;

    /**
     * 组织id
     */
    @TableField("forgid")
    @Schema(description = "组织id")
    private String forgid;

    /**
     * 修改人id
     */
    @Schema(description = "修改人id")
    @TableField("fmodifierid")
    private String fmodifierid;

    /**
     * 名称
     */
    @TableField("fname")
    @Schema(description = "名称")
    private String fname;

    /**
     * 规格
     */
    @TableField("fmodel")
    @Schema(description = "规格")
    private String fmodel;

    /**
     * 描述
     */
    @Schema(description = "描述")
    @TableField("fdescription")
    private String fdescription;

    /**
     * 是否自动扣款原料
     */
    @TableField("fisautodeduct")
    @Schema(description = "是否自动扣款原料")
    private String fisautodeduct;

    /**
     * 物料辅助单位
     */
    @TableField("fauxptyunit")
    @Schema(description = "物料辅助单位")
    private String fauxptyunit;

    /**
     * 默认税率
     */
    @TableField("ftaxrate")
    @Schema(description = "默认税率")
    private String ftaxrate;

    /**
     * 别名
     */
    @Schema(description = "别名")
    @TableField("another_name")
    private String anotherName;

    @TableField("easid")
    private String easid;

    /**
     * 简称
     */
    @Schema(description = "简称")
    @TableField("simple_name")
    private String simpleName;

    /**
     * 更新人
     */
    @Schema(description = "更新人")
    @TableField("sys_update_user")
    private String sysUpdateUser;

    /**
     * 审核人
     */
    @Schema(description = "审核人")
    @TableField("sys_audit_user")
    private String sysAuditUser;

    /**
     * 审核时间
     */
    @Schema(description = "审核时间")
    @TableField("sys_audit_time")
    private Date sysAuditTime;

    /**
     * 逻辑删除
     */
    @Schema(description = "逻辑删除")
    @TableField("sys_deleted")
    private Long sysDeleted;

    /**
     * 物料类型
     */
    @TableField("matertype")
    @Schema(description = "物料类型")
    private String matertype;
}
