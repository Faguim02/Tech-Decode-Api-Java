package com.techdecode.blog.service;

import com.techdecode.blog.dto.EmailDto;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class EmailService {
    @Autowired
    private JavaMailSender javaMailSender;
    @Value("spring.mail.username")
    private String mailFrom;

    public void sendWelcomeMessage(UUID id, String emailTo, String name) {
        String subject = "Bem-vindo(a) ao TechDecode! \uD83D\uDE80";
        String text = String.format("""
                Olá %s,
                
                É um prazer ter você aqui no TechDecode, o seu novo ponto de encontro para tudo relacionado ao mundo da tecnologia! \uD83D\uDCBB✨
                
                No nosso blog, você encontrará:
                
                Dicas e tutoriais sobre as últimas tendências tecnológicas;
                Guias práticos para desenvolvedores e entusiastas;
                Análises aprofundadas de ferramentas e inovações;
                E muito mais!
                Nosso objetivo é decodificar a tecnologia e torná-la acessível a todos, ajudando você a explorar, aprender e crescer neste universo fascinante.
                
                \uD83D\uDCA1 O que você pode fazer agora?
                
                Responda a este e-mail com sugestões ou dúvidas. Adoraríamos ouvir você!
                Prepare-se para mergulhar em um conteúdo incrível e fazer parte da nossa comunidade apaixonada por tecnologia.
                
                Abraços,
                Fagner Muniz de Sá
                Fundador do TechDecode""", name);

        EmailDto emailDto = new EmailDto(id,emailTo, subject, text);
        this.sendToEmail(emailDto);
    }

    private void sendToEmail(EmailDto emailDto) {
        try {

            MimeMessage mimeMessage = this.javaMailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true, "UTF-8");

            mimeMessageHelper.setText(emailDto.text());
            mimeMessageHelper.setTo(emailDto.emailTo());
            mimeMessageHelper.setSubject(emailDto.subject());
            mimeMessageHelper.setFrom(mailFrom);

            this.javaMailSender.send(mimeMessage);

        } catch (MailException e) {
            throw new RuntimeException("error send message");
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}
