package com.techdecode.email.service;

import com.techdecode.email.dto.EmailDto;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
    @Autowired
    private JavaMailSender javaMailSender;
    @Value("spring.mail.username")
    private String mailFrom;

    public void sendToEmail(EmailDto emailDto) {
        try {

            MimeMessage mimeMessage = this.javaMailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true, "UTF-8");

            mimeMessageHelper.setText(emailDto.text());
            mimeMessageHelper.setTo(emailDto.emailTo());
            mimeMessageHelper.setSubject(emailDto.subject());
            mimeMessageHelper.setFrom(mailFrom);

            javaMailSender.send(mimeMessage);

        } catch (MailException mailException) {
            throw new RuntimeException("error send message");
        } catch (MessagingException messagingException) {
            throw new RuntimeException(messagingException);
        }
    }
}
