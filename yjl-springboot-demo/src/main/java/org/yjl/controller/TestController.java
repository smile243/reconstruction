package org.yjl.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.yjl.domain.req.TestName;

/**
 * @author yjl
 * @since 2025/4/14
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/test")
@Slf4j
public class TestController {

    @PostMapping("/name")
    public void name(@RequestBody TestName testName) {
        log.info("iPhone:"+testName.getIPhone());
    }
}
