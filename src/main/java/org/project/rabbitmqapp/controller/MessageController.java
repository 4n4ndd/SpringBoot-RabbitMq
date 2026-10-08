package org.project.rabbitmqapp.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.project.rabbitmqapp.service.MessageProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/messages")
@Slf4j
@RequiredArgsConstructor
public class MessageController {
    //private static final Logger log = LoggerFactory.getLogger(MessageController.class);
    private final MessageProducer messageProducer;
    @PostMapping("/email-notification")
    public ResponseEntity<Map<String,String>> sendEmailNotifications(@RequestParam String recipient, @RequestParam String subject, @RequestParam String content,@RequestParam(defaultValue = "MEDIUM") String priority){
        log.info("Request to send Email notifications to:{}",recipient);
        try{
            String messageId = messageProducer.sendEmailNotification(recipient,subject,content,priority);
            return ResponseEntity.ok(Map.of("status","success",
                    "messageId",messageId,
                    "message","Email-Notification sent to RabbitMQ successfully"));
        }
        catch (Exception e){
            return ResponseEntity.badRequest().body(Map.of(
                    "status","error",
                    "message",e.getMessage()
            ));
        }
    }
}
