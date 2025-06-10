package org.yjl.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.yjl.domain.R;
import org.yjl.domain.entity.BdMaterialPo;
import org.yjl.service.BdMaterialService;

/**
 * <p>
 * 新物料 前端控制器
 * </p>
 *
 * @author yjl
 * @since 2025-06-10
 */
@RestController
@Tag(name = "新物料")
@RequestMapping("/material")
@RequiredArgsConstructor
public class BdMaterialController {
    private final BdMaterialService bdMaterialService;

    @GetMapping("/initCache")
    @Operation(summary = "添加缓存")
    public R<?> initCache() {
        bdMaterialService.initCache(bdMaterialService.list());
        return R.ok();
    }

    @GetMapping("/getByNo")
    @Operation(summary = "查询")
    public R<?> getByNo(String no) {
        BdMaterialPo bdMaterialPo = bdMaterialService.getByNo(no);
        return R.ok();
    }

    @GetMapping("/delAll")
    @Operation(summary = "删除缓存")
    public R<?> delAll() {
        bdMaterialService.delAll();
        return R.ok();
    }

    @GetMapping("/delByNo")
    @Operation(summary = "按照no删除缓存")
    public R<?> del(String no) {
        bdMaterialService.delByNo(no);
        return R.ok();
    }
}
