package com.notification.notification_service.kafkaWebsocket;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class NotificationDeleteEventProducer {

    private static final String TOPIC = "notification-delete-events";

    @Autowired
    private KafkaTemplate<String, Long> kafkaTemplate;

    public void sendDeleteEvent(Long notificationId) {
        kafkaTemplate.send(TOPIC, notificationId);
    }
}
