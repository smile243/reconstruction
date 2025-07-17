package org.yjl;

import com.github.xiaolyuh.cache.config.EnableLayeringCache;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @author yjl
 * @since 2025/3/24
 */
@SpringBootApplication
@MapperScan(value={"org.yjl.mapper"})
@Slf4j
//多级缓存
//@EnableLayeringCache
public class Application {
    public static void main(String[] args) throws UnknownHostException {
        SpringApplication app = new SpringApplication(Application.class);
        ConfigurableApplicationContext applicationContext = app.run(args);
        ConfigurableEnvironment environment = applicationContext.getEnvironment();
        log.info("Doc: \thttp://{}:{}{}/doc.html\n",
            InetAddress.getLocalHost().getHostAddress(),
            environment.getProperty("server.port"),
            environment.getProperty("server.servlet.context-path"));
        log.info("application started successfully");
    }

}
