package org.project.rabbitmqapp.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.project.rabbitmqapp.config.RabbitMQConfig;
import org.project.rabbitmqapp.entity.Message;
import org.project.rabbitmqapp.entity.Notification;
import org.project.rabbitmqapp.repository.NotificationRepository;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@Slf4j
@RequiredArgsConstructor
public class MessageConsumer {
    private final NotificationRepository notificationRepository;
    @RabbitListener(queues = RabbitMQConfig.EMAIL_QUEUE)
    public void handleEmail(Message message){
        log.info("Email-notification received: Id={}, Recipient={}, subject={}",message.getId(),message.getRecipient(),message.getSubject());
        try {
            Thread.sleep(2000);
            Notification notification = Notification.builder()
                    .messageId(message.getId())
                    .type(message.getType())
                    .recipient(message.getRecipient())
                    .subject(message.getSubject())
                    .content(message.getContent())
                    .status("SENT")
                    .processedAt(LocalDateTime.now())
                    .build();
            notificationRepository.save(notification);
            log.info("Email notification message processed and saved with ID: {}",message.getId());
        }catch (Exception e){
            log.error("Failed to processed the message: {}",e.getMessage(),e);
            Notification notification = Notification.builder()
                    .messageId(message.getId())
                    .type(message.getType())
                    .recipient(message.getRecipient())
                    .subject(message.getSubject())
                    .content(message.getContent())
                    .status("FAILED")
                    .processedAt(LocalDateTime.now())
                    .build();
            notificationRepository.save(notification);
        }
    }
}
