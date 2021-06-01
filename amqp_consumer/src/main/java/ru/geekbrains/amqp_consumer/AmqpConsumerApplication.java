package ru.geekbrains.amqp_consumer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AmqpConsumerApplication {
    public static void main(String[] args) {
        SpringApplication.run(AmqpConsumerApplication.class, args);
    }
}
