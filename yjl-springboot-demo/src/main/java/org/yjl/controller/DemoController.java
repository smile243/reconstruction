package org.yjl.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StopWatch;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;
import org.yjl.domain.R;
import org.yjl.domain.req.TestName;
import org.yjl.service.IDemoService;

import java.util.concurrent.Executor;

/**
 * @author yjl
 * @since 2025/4/14
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/test")
@Slf4j
@Tag(name = "测试")
public class DemoController {
    private final Executor futureExecutor;
    private final IDemoService service;

    @PostMapping("/name")
    public void name(@RequestBody TestName testName) {
        futureExecutor.execute(() -> {
            log.info("iPhone:" + testName.getIPhone());
        });
    }

    @GetMapping(value = "/testTransaction")
    @Operation(summary = "测试事务")
    public R<?> test() {
        service.transaction();
        return R.ok();
    }

    @PostMapping("/postTest")
    @Operation(summary = "线程池获取参数")
    public String postTest(HttpServletRequest request) {
        String age1 = request.getParameter("age");
        String name1 = request.getParameter("name");
        System.out.println("age1=" + age1 + ",name1=" + name1);
        new Thread(() -> {
            String age2 = request.getParameter("age");
            String name2 = request.getParameter("name");
            System.out.println("age2=" + age2 + ",name2=" + name2);
            //模拟业务请求
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            age2 = request.getParameter("age");
            name2 = request.getParameter("name");
        }).start();
        return "post success";
    }

    @GetMapping("/testDeferredResult")
    @Operation(summary = "DeferredResult")
    public DeferredResult<String> getDeferredResult(Long sleepTime){
        DeferredResult<String> result=new DeferredResult<>(5000L,"time out");
        new Thread(()->{
            try {
                Thread.sleep(sleepTime);
                result.setResult("i am yes");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
        return result;
    }

    @GetMapping("/testLock")
    @Operation(summary = "可重入锁")
    public R<?> testLock() throws InterruptedException {
        service.testLock();
        return R.ok();
    }

    @GetMapping("/insertBatch")
    @Operation(summary = "多线程事务")
    public R<?> insertBatch(){
        try {
            StopWatch stopWatch=new StopWatch();
            stopWatch.start();
            service.insertBatch();
            stopWatch.stop();
            log.info("insertBatch执行耗时:{}s",stopWatch.getTotalTimeSeconds());
        }catch (Exception e){
            e.printStackTrace();
        }
        return R.ok();
    }

    @GetMapping("/mybatisTest")
    @Operation(summary = "mybatis一级缓存漏洞")
    public R<?> mybatisTest(){
        service.mybatisTest();
        return R.ok();
    }

    @GetMapping("/transactionCallBack")
    @Operation(summary = "测试事务回调")
    public R<?> transactionCallBack(){
        service.transactionCallBack();
        return R.ok();
    }

    @GetMapping("/multiDatasource")
    @Operation(summary = "测试多数据源事务,需要打开配置中的多数据源连接")
    public R<?> multiDatasource(){
        service.multiDatasource();
        return R.ok();
    }

    @GetMapping("/transactionEvent")
    @Operation(summary = "事务型事件")
    public R<?> transactionEvent(){
        service.transactionEvent();
        return R.ok();
    }

    @GetMapping("/dataSourcePool")
    @Operation(summary = "默认数据库线程池容量")
    public R<?> dataSourcePool(){
        service.dataSourcePool();
        return R.ok();
    }

}
