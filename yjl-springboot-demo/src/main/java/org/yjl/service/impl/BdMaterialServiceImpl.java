package org.yjl.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.xiaolyuh.annotation.BatchCacheable;
import com.github.xiaolyuh.annotation.CacheEvict;
import com.github.xiaolyuh.annotation.Cacheable;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.yjl.domain.entity.BdMaterialPo;
import org.yjl.mapper.BdMaterialMapper;
import org.yjl.service.BdMaterialService;

import java.util.List;

/**
 * <p>
 * 新物料 服务实现类
 * </p>
 *
 * @author yjl
 * @since 2025-06-10
 */
@Service
@Slf4j
public class BdMaterialServiceImpl extends ServiceImpl<BdMaterialMapper, BdMaterialPo> implements BdMaterialService {

    @Override
    @BatchCacheable(value = "cache-prefix:material", keys = "#list.![fnumber]", depict = "物料基础资料")
    public List<BdMaterialPo> initCache(List<BdMaterialPo> list) {
        log.info("初始化物料缓存成功");
        return list;
    }

    @Override
    @Cacheable(value = "cache-prefix:material", key = "#no", depict = "用户信息缓存")
    public BdMaterialPo getByNo(String no) {
        log.info("物料no:{}未命中缓存", no);
        return getOne(Wrappers.<BdMaterialPo>lambdaQuery().eq(BdMaterialPo::getFnumber, no));
    }

    @Override
    @CacheEvict(value = "cache-prefix:material", allEntries = true)
    public void delAll() {
        log.info("删除物料所有缓存成功");
    }

    @Override
    @CacheEvict(value = "cache-prefix:material", key = "#no")
    public void delByNo(String no) {
        log.info("删除物料no:{}缓存成功", no);
    }
}
