package org.example.lmsplatform;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class LmsPlatformApplication {

    public static void main(String[] args) {
        SpringApplication.run(LmsPlatformApplication.class, args);
    }
}
