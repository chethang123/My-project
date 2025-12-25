package com.notification.notification_service.kafkaWebsocket;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class NotificationWebSocketService {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    public void sendDeleteNotification(Long notificationId) {
        messagingTemplate.convertAndSend("/topic/notifications/delete", notificationId);
    }
}
