package com.professorperson.tracker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = { "com.professorperson" })
@EnableJpaRepositories("com.professorperson.tracker.models.repos")
public class TrackerApplication {

    public static void main(String[] args) {
        SpringApplication.run(TrackerApplication.class);
    }
}
