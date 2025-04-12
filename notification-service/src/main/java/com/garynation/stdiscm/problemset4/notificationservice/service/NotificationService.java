package com.garynation.stdiscm.problemset4.notificationservice.service;

import java.util.List;
import java.util.UUID;

import com.garynation.stdiscm.problemset4.notificationservice.dto.NotificationDto;

public interface NotificationService {
    NotificationDto createNotification(NotificationDto notification);
    NotificationDto getNotificationById(UUID id);
    List<NotificationDto> getAllNotifications();
    List<NotificationDto> getNotificationsByRecipientId(UUID id);
    List<NotificationDto> getNotificationsByActorId(UUID id);
    NotificationDto deleteNotification(UUID id);
    NotificationDto updateNotificationStatus(UUID id, NotificationDto notificationDto);
}
