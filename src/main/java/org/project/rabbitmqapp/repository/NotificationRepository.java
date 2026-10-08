package org.project.rabbitmqapp.repository;

import org.project.rabbitmqapp.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification,Long> {

    List<Notification> findByRecipientOrderByCreatedAtDesc(String recipient);
}
