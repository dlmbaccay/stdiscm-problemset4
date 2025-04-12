package com.garynation.stdiscm.problemset4.notificationservice.repository;

import com.garynation.stdiscm.problemset4.notificationservice.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface NotificationRepository extends JpaRepository<Notification, UUID> {
    List<Notification> findNotificationByActorId(UUID actorId);

    List<Notification> findNotificationByRecipientId(UUID recipientId);
}
