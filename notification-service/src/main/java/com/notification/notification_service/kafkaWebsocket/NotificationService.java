package com.notification.notification_service.kafkaWebsocket;




import com.notification.notification_service.NotificationRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {

    @Autowired
    private NotificationRepository repository;

    @Autowired
    private NotificationDeleteEventProducer deleteProducer;

    @Autowired
    private NotificationWebSocketService webSocketService;

    public void deleteNotification(Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);

            // Broadcast via WebSocket
            webSocketService.sendDeleteNotification(id);

            // Publish Kafka delete event
            deleteProducer.sendDeleteEvent(id);
        }
    }
}
