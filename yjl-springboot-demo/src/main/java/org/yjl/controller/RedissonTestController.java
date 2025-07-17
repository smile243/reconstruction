package org.yjl.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RScript;
import org.redisson.api.RedissonClient;
import org.springframework.web.bind.annotation.*;
import org.yjl.domain.R;
import org.yjl.domain.req.TestName;

import java.util.Arrays;

@RequiredArgsConstructor
@RestController
@RequestMapping("/redisson")
@Slf4j
@Tag(name = "redisson测试")
public class RedissonTestController {
    private final RedissonClient redissonClient;

    @GetMapping("/lua")
    @Operation(summary = "lua脚本执行")
    public R<?> name(String key) {
        RScript script = redissonClient.getScript();
        //java到redis中的数字需要使用tonumber转换，然后转换也和redisconfig的序列化有关，codec方法
        String s = "local score = tonumber(ARGV[1])\n" +
            "if score == nil then\n" +
            "  return -1\n" +
            "end\n" +
            "if redis.call('exists', KEYS[1]) == 0 then\n" +
            "  local addResult = redis.call('zadd', KEYS[1], score, ARGV[2])\n" +
            "  if addResult == 1 then\n" +
            "    return 1\n" +
            "  end\n" +
            "  return 0\n" +
            "end\n" +
            "return redis.call('zcard', KEYS[1])";

        // 使用String.format确保数值格式的一致性
        Long result = script.eval(RScript.Mode.READ_WRITE, s, RScript.ReturnType.INTEGER,
            Arrays.asList(key),  // KEYS[1]: key名称
            1.0,           // ARGV[1]: score分数
            "member1"           // ARGV[2]: member成员名称
        );

        if (result == -1) {
            return R.fail("分数值格式错误");
        }
        return R.ok(result);
    }
}
