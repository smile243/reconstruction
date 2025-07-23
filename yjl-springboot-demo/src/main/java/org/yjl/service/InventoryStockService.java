package org.yjl.service;

import org.yjl.domain.dto.EntryOutInventoryBatchDTO;
import org.yjl.domain.entity.InventoryStockPo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 库存记录表 服务类
 * </p>
 *
 * @author yjl
 * @since 2025-07-21
 */
public interface InventoryStockService extends IService<InventoryStockPo> {

    void entry(EntryOutInventoryBatchDTO dto);

    void out(EntryOutInventoryBatchDTO dto);
}
