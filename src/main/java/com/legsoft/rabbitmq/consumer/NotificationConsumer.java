package com.legsoft.rabbitmq.consumer;

import com.legsoft.rabbitmq.config.RabbitConfig;
import com.legsoft.rabbitmq.domain.NotificationMessage;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class NotificationConsumer {

    @RabbitListener(queues = RabbitConfig.QUEUE_NAME)
    public void handleNotification(NotificationMessage message) {
        System.out.println("Received: " + message);

        // Simulate processing
        try {
            Thread.sleep(1000); // 1 second processing time
            System.out.println("Notification sent to: " + message.to());
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

}
