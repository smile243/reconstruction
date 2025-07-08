package org.yjl.service;

import org.yjl.domain.entity.BdMaterialPo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 新物料 服务类
 * </p>
 *
 * @author yjl
 * @since 2025-06-10
 */
public interface BdMaterialService extends IService<BdMaterialPo> {

    List<BdMaterialPo> initCache(List<BdMaterialPo> list);

    BdMaterialPo getByNo(String no);

    void delAll();

    void delByNo(String no);

    void test();
}
