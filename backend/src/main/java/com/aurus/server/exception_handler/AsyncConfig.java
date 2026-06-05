package com.aurus.server.exception_handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;

@Configuration
@EnableAsync
public class AsyncConfig implements AsyncConfigurer {

    private final Logger LOGGER = LoggerFactory.getLogger(AsyncConfig.class);

    @Override
    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
        return (throwable, method, obj) -> {
            if (throwable instanceof IllegalStateException && throwable.getMessage().contains("AsyncContext")) {
                LOGGER.info("Handled expected client async disconnect.");
            } else {
                LOGGER.error("Unexpected async error: " + throwable.getMessage());
            }
        };
    }
}
