package org.yjl.domain.dto;

import lombok.Data;

import java.util.List;

@Data
public class EntryOutInventoryBatchDTO {
    private String orgCode;
    private String warehouseCode;
    private List<InventoryDTO> inventoryList;
}
