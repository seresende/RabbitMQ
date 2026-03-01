package com.legsoft.rabbitmq.controller;

import com.legsoft.rabbitmq.domain.NotificationMessage;
import com.legsoft.rabbitmq.producer.NotificationProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/notifications")
public class NotificationController {

    @Autowired
    private NotificationProducer producer;

    @PostMapping("/send")
    public String sendNotification(@RequestBody NotificationMessage message) {

        producer.sendNotification(message);
        return "Notification queued successfully!";
    }
}
