package backend2.config;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.util.Collections;

@Configuration
public class RabbitMQConfig {
    
    public static final String BORROWING_CREATED_QUEUE = "borrowing.created";
    public static final String BOOK_DELETED_QUEUE = "book.deleted";

    @Bean
    public Queue borrowingCreatedQueue() {
        return new Queue(BORROWING_CREATED_QUEUE);
    }

    @Bean
    public Queue bookDeletedQueue() {
        return new Queue(BOOK_DELETED_QUEUE);
    }

    @Bean
    public Jackson2JsonMessageConverter jackson2JsonMessageConverter() {
        return new Jackson2JsonMessageConverter("*");
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(jackson2JsonMessageConverter());
        return rabbitTemplate;
    }
} 