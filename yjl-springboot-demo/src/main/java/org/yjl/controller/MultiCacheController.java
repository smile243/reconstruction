package org.yjl.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.yjl.domain.R;
import org.yjl.domain.entity.Demo;
import org.yjl.service.IDemoService;

/**
 * @author yjl
 * @since 2025/6/10
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/test/multiCache")
@Slf4j
@Tag(name = "多级缓存测试")
public class MultiCacheController {
    private final IDemoService service;

    @PostMapping("/put")
    @Operation(summary = "添加缓存")
    public R<?> put(@RequestBody Demo demo) {
        Demo result = service.cachePut(demo);
        return R.ok();
    }

    @DeleteMapping("/del")
    @Operation(summary = "删除缓存")
    public R<?> del(Long id) {
        service.cacheRemove(id);
        return R.ok();
    }
}
