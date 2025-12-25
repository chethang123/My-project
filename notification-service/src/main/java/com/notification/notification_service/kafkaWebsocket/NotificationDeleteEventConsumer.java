package com.notification.notification_service.kafkaWebsocket;



import com.notification.notification_service.NotificationRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class NotificationDeleteEventConsumer {

    @Autowired
    private NotificationRepository repository;

    @Autowired
    private NotificationWebSocketService webSocketService;

    @KafkaListener(topics = "notification-delete-events", groupId = "notification-group")
    public void consumeDeleteEvent(Long notificationId) {
        if (repository.existsById(notificationId)) {
            repository.deleteById(notificationId);

            // Broadcast to connected clients
            webSocketService.sendDeleteNotification(notificationId);
        }
    }
}
