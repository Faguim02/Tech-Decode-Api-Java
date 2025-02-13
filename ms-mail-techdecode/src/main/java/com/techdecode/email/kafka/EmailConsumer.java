package com.techdecode.email.kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.techdecode.email.dto.EmailDto;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class EmailConsumer {
    @KafkaListener(topics = "email-topic" ,groupId = "group-mail-consumer")
    public void receiveMessage(String email) {
        try{
            ObjectMapper objectMapper = new ObjectMapper();
            EmailDto emailDto = objectMapper.readValue(email, EmailDto.class);
            System.out.println(emailDto.emailTo());
        }catch (Exception e) {
            System.out.println("ops");
        }
    }
}
