package ru.geekbrains.springmarket;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

@PropertySource("classpath:private.properties")
@SpringBootApplication
public class SpringMarketApplication {
    public static void main(String[] args) {
        SpringApplication.run(SpringMarketApplication.class, args);
    }
}
