package com.devops.common;

import io.micrometer.common.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.core.env.Environment;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Optional;
import java.util.UUID;

@Slf4j
public abstract  class SpringBootApplicationBase {
    public static void main(String[] args, Class<?> applicationClass) {
        UUID uuid = UUID.randomUUID();
        System.out.println("uuid = " + uuid);
        SpringApplication app = new SpringApplication(applicationClass);
        Environment env = app.run(args).getEnvironment();
        logApplicationStartup(env);
    }

    private static void logApplicationStartup(Environment env) {
        String protocol = Optional.ofNullable(env.getProperty("server.ssl.key-store")).map(key -> "https").orElse("http");
        String serverPort = env.getProperty("server.port");
        String contextPath = Optional.ofNullable(env.getProperty("server.servlet.context-path")).filter(StringUtils::isNotBlank).orElse("/");
        String hostAddress = "localhost";

        try {
            hostAddress = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            log.error("The host name could not be determined, using `localhost` as fallback");
        }
        log.info("\n\033[1;36m----------------------------------------------------------\033[0m\n\t\033[1;33m🚀 Application '{}' is running! Access URLs:\033[0m\n\t" + "\033[1;32mLocal: \t\t{}://localhost:{}{}\033[0m\n\t" + "\033[1;32mExternal: \t{}://{}:{}{}\033[0m\n\t" + "\033[1;35mProfile(s): \t{}\033[0m\n\033[1;36m----------------------------------------------------------\033[0m",
                env.getProperty("spring.application.name"),
                protocol,
                serverPort,
                contextPath,
                protocol,
                hostAddress,
                serverPort,
                contextPath,
                env.getActiveProfiles().length == 0 ? env.getDefaultProfiles() : env.getActiveProfiles());
    }

}
