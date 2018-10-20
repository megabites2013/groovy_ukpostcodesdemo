package com.efiab.springdockerjib

import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.Bean
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.scheduling.annotation.EnableAsync
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor

import java.util.concurrent.Executor

import static com.efiab.springdockerjib.utils.Constants.UKPCODE

@EnableJpaRepositories(basePackages = "com.efiab.springdockerjib.repository")
@SpringBootApplication
@EnableAsync
class SpringdockerjibApplication {

    static void main(String[] args) {
        final Logger logger = LogManager.getLogger(UKPCODE)
        SpringApplication.run(SpringdockerjibApplication.class, args)
        logger.info("SpringdockerjibApplication started ... ...")
    }

    /**
     * By defalut SimpleAsyncTaskExecutor is used by SpringBoot when use @EnableAsync
     *
     * @return Executor
     */
    @Bean
    Executor asyncExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor()
        executor.setCorePoolSize(2)
        executor.setMaxPoolSize(4)
        executor.setQueueCapacity(500)
        executor.setThreadNamePrefix("LongRunThread-")
        executor.initialize()
        return executor
    }
}
