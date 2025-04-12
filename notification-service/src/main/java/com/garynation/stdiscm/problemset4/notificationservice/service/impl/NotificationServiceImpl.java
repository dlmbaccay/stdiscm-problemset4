package com.garynation.stdiscm.problemset4.notificationservice.service.impl;

import com.garynation.stdiscm.problemset4.notificationservice.dto.NotificationDto;
import com.garynation.stdiscm.problemset4.notificationservice.entity.Notification;
import com.garynation.stdiscm.problemset4.notificationservice.exception.ResourceNotFoundException;
import com.garynation.stdiscm.problemset4.notificationservice.mapper.NotificationMapper;
import com.garynation.stdiscm.problemset4.notificationservice.repository.NotificationRepository;
import com.garynation.stdiscm.problemset4.notificationservice.service.NotificationService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class NotificationServiceImpl
implements NotificationService {

    private NotificationRepository notificationRepository;

    @Override
    public NotificationDto createNotification(NotificationDto notificationDto) {
        Notification notif = NotificationMapper.mapToNotification(notificationDto);
        Notification savedNotification = notificationRepository.save(notif);


        return NotificationMapper.mapToNotificationDto(savedNotification);
    }

    @Override
    public NotificationDto getNotificationById(UUID id) {
        Notification notif = notificationRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Notification not found."));

        return NotificationMapper.mapToNotificationDto(notif);
    }

    @Override
    public List<NotificationDto> getAllNotifications() {
        List<Notification> notificationList = notificationRepository.findAll();
        return notificationList.stream().map(NotificationMapper::mapToNotificationDto).collect(Collectors.toList());
    }

    @Override
    public List<NotificationDto> getNotificationsByRecipientId(UUID id) {
        List<Notification> notifs = notificationRepository.findNotificationByRecipientId(id);
        return notifs.stream().map(NotificationMapper::mapToNotificationDto).collect(Collectors.toList());
    }

    @Override
    public List<NotificationDto> getNotificationsByActorId(UUID id) {
        List<Notification> notifs = notificationRepository.findNotificationByActorId(id);
        return notifs.stream().map(NotificationMapper::mapToNotificationDto).collect(Collectors.toList());
    }

    @Override
    public NotificationDto deleteNotification(UUID id) {
        Notification notif = notificationRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Grade not found."));
        notificationRepository.delete(notif);
        return null;
    }

    @Override
    public NotificationDto updateNotificationStatus(UUID id, NotificationDto notificationDto) {
        Notification notification = notificationRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Notification not found."));
        notification.setRead(notificationDto.isRead());
        Notification updatedNotification = notificationRepository.save(notification);
        return NotificationMapper.mapToNotificationDto(updatedNotification);
    }
}
