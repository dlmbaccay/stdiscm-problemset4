package com.garynation.stdiscm.problemset4.notificationservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NotificationDto {
    private UUID id;
    private UUID recipientId;
    private String type;
    private String message;
    private UUID courseId;
    private UUID actorId;
    private boolean isRead;
    private Date createdAt;
}
