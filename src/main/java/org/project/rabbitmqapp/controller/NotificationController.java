package org.project.rabbitmqapp.controller;

import lombok.RequiredArgsConstructor;
import org.project.rabbitmqapp.entity.Notification;
import org.project.rabbitmqapp.repository.NotificationRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/notifications")
@RequiredArgsConstructor
public class NotificationController {
    private final NotificationRepository notificationRepository;
    @GetMapping("/getAllNotifications")
    public ResponseEntity<List<Notification>> getAllNotifications(){
        List<Notification> listofnotifications = notificationRepository.findAll();
        return ResponseEntity.ok(listofnotifications);
    }
    @GetMapping("/getAllNotificationsbyrecipient")
    public ResponseEntity<List<Notification>> getAllNotificationByRecipient(String recipient){
        List<Notification> notificationsbyrecipient = notificationRepository.findByRecipientOrderByCreatedAtDesc(recipient);
        return ResponseEntity.ok(notificationsbyrecipient);
    }
}
