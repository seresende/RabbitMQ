package com.legsoft.rabbitmq.domain;

import java.io.Serializable;

public record NotificationMessage(

        String to,
        String subject,
        String body
) implements Serializable {
}
