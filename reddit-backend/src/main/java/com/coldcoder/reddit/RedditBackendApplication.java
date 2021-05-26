package com.coldcoder.reddit;

import com.coldcoder.reddit.config.SwaggerConfig;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
@EnableSwagger2
@Import(SwaggerConfig.class)
public class RedditBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(RedditBackendApplication.class, args);
    }

}
