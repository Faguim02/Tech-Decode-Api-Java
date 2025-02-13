package com.techdecode.blog.kafka.producers;

import com.techdecode.blog.dto.EmailDto;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class EmailProducer {
    private KafkaTemplate <String, EmailDto> kafkaTemplate;

    public EmailProducer(KafkaTemplate<String, EmailDto> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessage(EmailDto msg) {
        kafkaTemplate.send("email-topic", msg);
    }
}
