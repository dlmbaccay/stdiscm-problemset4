package com.garynation.stdiscm.problemset4.apigatewaybroker.config;

import com.garynation.stdiscm.problemset4.apigatewaybroker.service.MessageService;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    // Define queue names as constants
    public static final String AUTH_QUEUE = "auth.queue";
    public static final String COURSE_QUEUE = "course.queue";
    public static final String ENROLLMENT_QUEUE = "enrollment.queue";
    public static final String GRADE_QUEUE = "grade.queue";
    
    // Define exchange name
    public static final String EXCHANGE = "microservice.exchange";
    
    // Define routing keys
    public static final String AUTH_ROUTING_KEY = "api.auth.#";
    public static final String COURSE_ROUTING_KEY = "api.course.#";
    public static final String ENROLLMENT_ROUTING_KEY = "api.enrollment.#";
    public static final String GRADE_ROUTING_KEY = "api.grade.#";

    public static final String AUTH_RESPONSE_QUEUE = "auth.response.queue";
    private MessageService messageService;

    @Bean
    public Queue authResponseQueue() {
        return new Queue(AUTH_RESPONSE_QUEUE, true);
    }

    @Bean
    public Binding authResponseBinding(Queue authResponseQueue, TopicExchange exchange) {
        return BindingBuilder.bind(authResponseQueue)
            .to(exchange)
            .with("api.auth.response");
    }

    @Bean
    public SimpleMessageListenerContainer authResponseListener(
            ConnectionFactory connectionFactory) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setQueueNames(AUTH_RESPONSE_QUEUE);
        container.setMessageListener(message -> messageService.handleAuthResponse(message));
        return container;
    }
    
    // Configure message converter to use JSON
    @Bean
    public MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    // Configure RabbitTemplate with our message converter
    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(messageConverter());
        return template;
    }

    // Define the exchange
    @Bean
    public TopicExchange exchange() {
        return new TopicExchange(EXCHANGE);
    }

    // Define queues
    @Bean
    public Queue authQueue() {
        return new Queue(AUTH_QUEUE, true);
    }

    @Bean
    public Queue courseQueue() {
        return new Queue(COURSE_QUEUE, true);
    }

    @Bean
    public Queue enrollmentQueue() {
        return new Queue(ENROLLMENT_QUEUE, true);
    }

    @Bean
    public Queue gradeQueue() {
        return new Queue(GRADE_QUEUE, true);
    }

    // Define bindings between queues and exchange
    @Bean
    public Binding authBinding(Queue authQueue, TopicExchange exchange) {
        return BindingBuilder.bind(authQueue).to(exchange).with(AUTH_ROUTING_KEY);
    }

    @Bean
    public Binding courseBinding(Queue courseQueue, TopicExchange exchange) {
        return BindingBuilder.bind(courseQueue).to(exchange).with(COURSE_ROUTING_KEY);
    }

    @Bean
    public Binding enrollmentBinding(Queue enrollmentQueue, TopicExchange exchange) {
        return BindingBuilder.bind(enrollmentQueue).to(exchange).with(ENROLLMENT_ROUTING_KEY);
    }

    @Bean
    public Binding gradeBinding(Queue gradeQueue, TopicExchange exchange) {
        return BindingBuilder.bind(gradeQueue).to(exchange).with(GRADE_ROUTING_KEY);
    }
} 