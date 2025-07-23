package org.yjl.service.impl;

import org.yjl.domain.dto.EntryOutInventoryBatchDTO;
import org.yjl.domain.entity.InventoryStockPo;
import org.yjl.mapper.InventoryStockMapper;
import org.yjl.service.InventoryStockService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 库存记录表 服务实现类
 * </p>
 *
 * @author yjl
 * @since 2025-07-21
 */
@Service
public class InventoryStockServiceImpl extends ServiceImpl<InventoryStockMapper, InventoryStockPo> implements InventoryStockService {

    @Override
    public void entry(EntryOutInventoryBatchDTO dto) {
        //根据 库存组织+仓库 从redis批量获取库存

        //数据库记录入库流水
        //修改redis库存
        //发送mq消息数据库库存修改
    }

    @Override
    public void out(EntryOutInventoryBatchDTO dto) {

    }
}
