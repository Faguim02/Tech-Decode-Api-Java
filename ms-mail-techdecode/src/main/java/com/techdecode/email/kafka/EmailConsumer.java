package com.techdecode.email.kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.techdecode.email.dto.EmailDto;
import com.techdecode.email.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class EmailConsumer {

    @Autowired
    private EmailService emailService;

    @KafkaListener(topics = "email-topic" ,groupId = "group-mail-consumer")
    public void receiveMessage(String email) {
        try{
            ObjectMapper objectMapper = new ObjectMapper();
            EmailDto emailDto = objectMapper.readValue(email, EmailDto.class);
            this.emailService.sendToEmail(emailDto);
        }catch (Exception e) {
            System.out.println("Erro ao receber dados do producer");
        }
    }
}
