package org.yjl.domain.req;

import lombok.Data;

/**
 * @author yjl
 * @since 2025/4/23
 */
@Data
public class TestName {
    /**
     * 参数命名第一个字母小写，第二个字母大写尽量避免
     * <p>因为jackson和lombok对get、set方法处理的逻辑不一致，导致属性名不匹配
     * <p>get请求不会经过jackson，post才会
     * <p><a href="https://www.cnblogs.com/techcorner/p/17391439.html">jackson和lombok具体逻辑</a>
     */
    private String iPhone;
}
