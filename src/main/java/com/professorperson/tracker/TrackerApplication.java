package com.professorperson.tracker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication(scanBasePackages = { "com.professorperson" })
@ComponentScan(basePackageClasses = TrackerApplication.class)
public class TrackerApplication {

    public static void main(String[] args) {
        SpringApplication.run(TrackerApplication.class);
    }
}
