package com.garynation.stdiscm.problemset4.apigatewaybroker.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.garynation.stdiscm.problemset4.apigatewaybroker.config.RabbitMQConfig;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class MessageService {

    private final RabbitTemplate rabbitTemplate;
    private final ObjectMapper objectMapper;
    private final ConcurrentHashMap<String, CompletableFuture<Map<String, Object>>> pendingRequests;

    @Autowired
    public MessageService(RabbitTemplate rabbitTemplate, ObjectMapper objectMapper) {
        this.rabbitTemplate = rabbitTemplate;
        this.objectMapper = objectMapper;
        this.pendingRequests = new ConcurrentHashMap<>();
    }

    public void sendToAuthServiceWithCallback(Map<String, Object> message, 
                                            CompletableFuture<Map<String, Object>> future) {
        String correlationId = UUID.randomUUID().toString();
        message.put("correlationId", correlationId);
        pendingRequests.put(correlationId, future);

        rabbitTemplate.convertAndSend(
            RabbitMQConfig.EXCHANGE,
            "api.auth.validate",
            message
        );
    }
    
    @SuppressWarnings("unchecked")
    public void handleAuthResponse(Message message) {
        try {
            MessageProperties props = message.getMessageProperties();
            String correlationId = props.getCorrelationId();
            
            if (correlationId != null && pendingRequests.containsKey(correlationId)) {
                MessageConverter converter = rabbitTemplate.getMessageConverter();
                Map<String, Object> response = (Map<String, Object>) converter.fromMessage(message);
                
                CompletableFuture<Map<String, Object>> future = pendingRequests.remove(correlationId);
                if (future != null) {
                    future.complete(response);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Send a message to the auth service
     * @param message the message to send
     */
    public void sendToAuthService(Object message) {
        System.out.println("Sending message to auth service: " + message);
        rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE, "api.auth.message", message);
    }

    /**
     * Send a message to the course service
     * @param message the message to send
     */
    public void sendToCourseService(Object message) {
        rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE, "api.course.message", message);
    }

    /**
     * Send a message to the enrollment service
     * @param message the message to send
     */
    public void sendToEnrollmentService(Object message) {
        rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE, "api.enrollment.message", message);
    }

    /**
     * Send a message to the grade service
     * @param message the message to send
     */
    public void sendToGradeService(Object message) {
        rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE, "api.grade.message", message);
    }

}