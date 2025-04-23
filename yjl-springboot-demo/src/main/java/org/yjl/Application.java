package org.yjl;

import lombok.extern.slf4j.Slf4j;
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
@Slf4j
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
