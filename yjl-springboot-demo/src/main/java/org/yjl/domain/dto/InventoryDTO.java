package org.yjl.domain.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class InventoryDTO {
    private String batchNo;
    private String materialCode;
    private String productionDate;
    private String expirationDate;
    private BigDecimal qty;
}
