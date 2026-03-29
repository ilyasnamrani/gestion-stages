package org.example.notificationservice.repository;

import org.example.notificationservice.entitie.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepository extends JpaRepository<Long, Notification> {
}
