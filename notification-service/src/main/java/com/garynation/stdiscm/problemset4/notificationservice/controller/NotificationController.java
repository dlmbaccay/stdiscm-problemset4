package com.garynation.stdiscm.problemset4.notificationservice.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.garynation.stdiscm.problemset4.notificationservice.dto.NotificationDto;
import com.garynation.stdiscm.problemset4.notificationservice.service.NotificationService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/api/notifications")
public class NotificationController {
    private NotificationService notificationService;

    @PostMapping("/create")
    public ResponseEntity<NotificationDto> addNotification(@RequestBody NotificationDto notificationDto){
        NotificationDto notification = notificationService.createNotification(notificationDto);
        return new ResponseEntity<>(notification, HttpStatus.CREATED);
    }

    @GetMapping("{id}")
    public ResponseEntity<NotificationDto> getNotificationById(@PathVariable("id") UUID id){
        NotificationDto notification = notificationService.getNotificationById(id);
        return new ResponseEntity<>(notification, HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<NotificationDto>> getAllNotifications(){
        List<NotificationDto> notificationDtoList = notificationService.getAllNotifications();
        return new ResponseEntity<>(notificationDtoList, HttpStatus.OK);
    }

    @GetMapping("/recipient/{id}")
    public ResponseEntity<List<NotificationDto>> getNotificationByRecipient(@PathVariable("id") UUID id){
        List<NotificationDto> notificationDtoList = notificationService.getNotificationsByRecipientId(id);
        return new ResponseEntity<>(notificationDtoList, HttpStatus.OK);
    }

    @GetMapping("/actor/{id}")
    public ResponseEntity<List<NotificationDto>> getNotificationByActor(@PathVariable("id") UUID id){
        List<NotificationDto> notificationDtoList = notificationService.getNotificationsByActorId(id);
        return new ResponseEntity<>(notificationDtoList, HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteNotification(@PathVariable("id") UUID id){
        notificationService.deleteNotification(id);
        return ResponseEntity.ok("Grade deleted successfully.");
    }

    @PutMapping("{id}")
    public ResponseEntity<NotificationDto> updateNotificationStatus(@PathVariable("id") UUID id, @RequestBody NotificationDto notificationDto) {
        NotificationDto notification = notificationService.updateNotificationStatus(id, notificationDto);
        return new ResponseEntity<>(notification, HttpStatus.OK);
    }
}
