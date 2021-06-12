package ru.geekbrains.springmarket.configs;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class RabbitConfig {
    private static final boolean NON_DURABLE = false;
    private static final String EXCHANGER_NAME = "ordersExchanger";
    public static final String REQUEST_QUEUE_NAME = "requestQueue";
    private static final String RESPONSE_QUEUE_NAME = "responseQueue";

    @Bean
    public TopicExchange exchanger() {
        return new TopicExchange(EXCHANGER_NAME);
    }

    @Bean
    public Queue requestQueue() {
        return new Queue(REQUEST_QUEUE_NAME, NON_DURABLE);
    }

    @Bean
    public Queue responseQueue() {
        return new Queue(RESPONSE_QUEUE_NAME, NON_DURABLE);
    }

    @Bean
    public RabbitTemplate rabbitTemplate(final ConnectionFactory connectionFactory) {
        final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(producerJackson2MessageConverter());
        return rabbitTemplate;
    }

    @Bean
    public Jackson2JsonMessageConverter producerJackson2MessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @RabbitListener(queues = RESPONSE_QUEUE_NAME)
    public void listen(String str) {
        log.info("Response from Order Processing Service: " + str);
    }
}
