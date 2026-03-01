package com.legsoft.rabbitmq.producer;

import com.legsoft.rabbitmq.config.RabbitConfig;
import com.legsoft.rabbitmq.domain.NotificationMessage;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NotificationProducer {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void sendNotification(NotificationMessage message) {

        System.out.println("Sending: " + message);

        rabbitTemplate.convertAndSend(
                RabbitConfig.EXCHANGE_NAME,
                RabbitConfig.ROUTING_KEY,
                message
        );

        System.out.println("Message sent successfully!");
    }
}
