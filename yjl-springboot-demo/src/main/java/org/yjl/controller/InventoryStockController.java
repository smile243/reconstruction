package org.yjl.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.stereotype.Controller;
import org.yjl.domain.R;
import org.yjl.domain.dto.EntryOutInventoryBatchDTO;
import org.yjl.domain.entity.Demo;
import org.yjl.service.InventoryStockService;

/**
 * <p>
 * 库存记录表 前端控制器
 * </p>
 *
 * @author yjl
 * @since 2025-07-21
 */
@Controller
@RequestMapping("/inventoryStockPo")
@RequiredArgsConstructor
public class InventoryStockController {
    private final InventoryStockService inventoryStockService;

    //入库
    @PostMapping("/entry")
    @Operation(summary = "入库")
    public R<?> put(@RequestBody EntryOutInventoryBatchDTO dto) {
        inventoryStockService.entry(dto);
        return R.ok();
    }
    //出库
    @PostMapping("/out")
    @Operation(summary = "出库")
    public R<?> put(@RequestBody EntryOutInventoryBatchDTO dto) {
        inventoryStockService.out(dto);
        return R.ok();
    }
    //查询库存

}
