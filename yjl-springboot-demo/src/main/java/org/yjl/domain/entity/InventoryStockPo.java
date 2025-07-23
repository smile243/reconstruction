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

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * 库存记录表
 * </p>
 *
 * @author yjl
 * @since 2025-07-21
 */
@Getter
@Setter
@ToString
@TableName("inventory_stock")
@Tag(name = "InventoryStockPo对象", description = "库存记录表")
public class InventoryStockPo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @Schema(description = "主键ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 库存组织编码
     */
    @TableField("org_code")
    @Schema(description = "库存组织编码")
    private String orgCode;

    /**
     * 仓库编码
     */
    @Schema(description = "仓库编码")
    @TableField("warehouse_code")
    private String warehouseCode;

    /**
     * 库位编码（用于多库位管理，可为空）
     */
    @TableField("location_code")
    @Schema(description = "库位编码（用于多库位管理，可为空）")
    private String locationCode;

    /**
     * 物料编码
     */
    @Schema(description = "物料编码")
    @TableField("material_code")
    private String materialCode;

    /**
     * 物料名称（冗余字段）
     */
    @TableField("material_name")
    @Schema(description = "物料名称（冗余字段）")
    private String materialName;

    /**
     * 批次号（支持批次管理，可为空）
     */
    @TableField("batch_no")
    @Schema(description = "批次号（支持批次管理，可为空）")
    private String batchNo;

    /**
     * 生产日期（可为空）
     */
    @Schema(description = "生产日期（可为空）")
    @TableField("production_date")
    private Date productionDate;

    /**
     * 到期日期（可为空）
     */
    @Schema(description = "到期日期（可为空）")
    @TableField("expiration_date")
    private Date expirationDate;

    /**
     * 单位（如PCS、KG）
     */
    @TableField("unit")
    @Schema(description = "单位（如PCS、KG）")
    private String unit;

    /**
     * 可用数量（库存可用数量）
     */
    @TableField("available_qty")
    @Schema(description = "可用数量（库存可用数量）")
    private BigDecimal availableQty;

    /**
     * 占用数量（如销售订单占用）
     */
    @TableField("occupied_qty")
    @Schema(description = "占用数量（如销售订单占用）")
    private BigDecimal occupiedQty;

    /**
     * 冻结数量（质检或盘点冻结）
     */
    @TableField("frozen_qty")
    @Schema(description = "冻结数量（质检或盘点冻结）")
    private BigDecimal frozenQty;

    /**
     * 在途数量（采购或调拨在途）
     */
    @TableField("in_transit_qty")
    @Schema(description = "在途数量（采购或调拨在途）")
    private BigDecimal inTransitQty;

    /**
     * 最后更新时间
     */
    @Schema(description = "最后更新时间")
    @TableField("last_updated")
    private Date lastUpdated;

    /**
     * 创建时间
     */
    @Schema(description = "创建时间")
    @TableField("created_at")
    private Date createdAt;

    /**
     * 备注信息
     */
    @TableField("remark")
    @Schema(description = "备注信息")
    private String remark;
}
