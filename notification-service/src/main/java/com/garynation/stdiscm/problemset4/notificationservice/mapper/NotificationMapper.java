package com.garynation.stdiscm.problemset4.notificationservice.mapper;

import com.garynation.stdiscm.problemset4.notificationservice.dto.NotificationDto;
import com.garynation.stdiscm.problemset4.notificationservice.entity.Notification;

public class NotificationMapper {

    public static NotificationDto mapToNotificationDto(Notification notif) {
        return new NotificationDto(
                notif.getId(),
                notif.getRecipientId(),
                notif.getType(),
                notif.getMessage(),
                notif.getCourseId(),
                notif.getActorId(),
                notif.isRead()
        );
    }

    public static Notification mapToNotification(NotificationDto notifDto) {
        return new Notification(
                notifDto.getId(),
                notifDto.getRecipientId(),
                notifDto.getType(),
                notifDto.getMessage(),
                notifDto.getCourseId(),
                notifDto.getActorId(),
                notifDto.isRead()
        );
    }
}
