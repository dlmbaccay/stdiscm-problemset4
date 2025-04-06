package com.garynation.api_gateway.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.garynation.api_gateway.service.MessageService;

@RestController
@RequestMapping("/api/messages")
public class MessageController {

    private final MessageService messageService;

    @Autowired
    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @PostMapping("/auth")
    public ResponseEntity<Map<String, String>> sendToAuthService(@RequestBody Map<String, Object> message) {
        messageService.sendToAuthService(message);
        
        Map<String, String> response = new HashMap<>();
        response.put("status", "Message sent to auth service");
        return ResponseEntity.ok(response);
    }

    @PostMapping("/courses")
    public ResponseEntity<Map<String, String>> sendToCourseService(@RequestBody Map<String, Object> message) {
        messageService.sendToCourseService(message);
        
        Map<String, String> response = new HashMap<>();
        response.put("status", "Message sent to course service");
        return ResponseEntity.ok(response);
    }

    @PostMapping("/enrollment")
    public ResponseEntity<Map<String, String>> sendToEnrollmentService(@RequestBody Map<String, Object> message) {
        messageService.sendToEnrollmentService(message);
        
        Map<String, String> response = new HashMap<>();
        response.put("status", "Message sent to enrollment service");
        return ResponseEntity.ok(response);
    }

    @PostMapping("/grades")
    public ResponseEntity<Map<String, String>> sendToGradeService(@RequestBody Map<String, Object> message) {
        messageService.sendToGradeService(message);
        
        Map<String, String> response = new HashMap<>();
        response.put("status", "Message sent to grade service");
        return ResponseEntity.ok(response);
    }
} 