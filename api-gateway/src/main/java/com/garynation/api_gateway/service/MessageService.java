package com.garynation.api_gateway.service;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.garynation.api_gateway.config.RabbitMQConfig;

@Service
public class MessageService {

    private final RabbitTemplate rabbitTemplate;

    @Autowired
    public MessageService(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    /**
     * Send a message to the auth service
     * @param message the message to send
     */
    public void sendToAuthService(Object message) {
        System.out.println("Sending message to auth service: " + message);
        rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE, "auth.message", message);
    }

    /**
     * Send a message to the course service
     * @param message the message to send
     */
    public void sendToCourseService(Object message) {
        rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE, "course.message", message);
    }

    /**
     * Send a message to the enrollment service
     * @param message the message to send
     */
    public void sendToEnrollmentService(Object message) {
        rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE, "enrollment.message", message);
    }

    /**
     * Send a message to the grade service
     * @param message the message to send
     */
    public void sendToGradeService(Object message) {
        rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE, "grade.message", message);
    }
} 