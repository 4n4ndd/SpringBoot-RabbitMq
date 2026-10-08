package org.project.rabbitmqapp.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.project.rabbitmqapp.config.RabbitMQConfig;
import org.project.rabbitmqapp.entity.Message;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class MessageProducer {
   // private static final Logger log = LoggerFactory.getLogger(MessageProducer.class);
    private final RabbitTemplate rabbitTemplate;
    public String sendEmailNotification(String recipient, String subject, String content, String priority) {
        String messageId = UUID.randomUUID().toString();
        Message message = Message.builder()
                .id(messageId)
                .type("EMAIL")
                .recipient(recipient)
                .subject(subject)
                .content(content)
                .priority("HIGH")
                .timeStamp(LocalDateTime.now())
                .build();
        try{
            log.info("Sending email-notification with the ID: {} to RabbitMQ",messageId);
            rabbitTemplate.convertAndSend(
                    RabbitMQConfig.APP_EXCHANGE,
                    RabbitMQConfig.EMAIL_NOTIFICATION_ROUTING_KEY,
                    message
            );
            log.info("Email-notification sent to the queue");
            return messageId;
        }
        catch (Exception e){
                log.error("Failed to send email message: {}", e.getMessage(), e);
                throw new RuntimeException("Failed to send email");
        }
    }
    public String sendSmsNotification(String recipient, String subject, String content, String priority) {
        String messageId = UUID.randomUUID().toString();
        Message message = Message.builder()
                .id(messageId)
                .type("SMS")
                .recipient(recipient)
                .subject("SMS Notification")
                .content(content)
                .priority("HIGH")
                .timeStamp(LocalDateTime.now())
                .build();
        try{
            log.info("Sending sms-notification with the ID: {} to RabbitMQ",messageId);
            rabbitTemplate.convertAndSend(
                    RabbitMQConfig.APP_EXCHANGE,
                    RabbitMQConfig.SMS_NOTIFICATION_ROUTING_KEY,
                    message
            );
            log.info("Sms-notification sent to the queue");
            return messageId;
        }
        catch (Exception e){
            log.error("Failed to send sms message: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to send sms");
        }
    }
}
