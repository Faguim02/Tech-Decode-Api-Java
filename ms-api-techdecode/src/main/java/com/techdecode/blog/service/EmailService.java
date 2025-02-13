package com.techdecode.blog.service;

import com.techdecode.blog.dto.EmailDto;
import com.techdecode.blog.dto.InfoNewLoginDto;
import com.techdecode.blog.kafka.producers.EmailProducer;
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
    private EmailProducer emailProducer;

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
        this.emailProducer.sendMessage(emailDto);
    }

    public void sendInfoNewLoginDetected(UUID id, String emailTo, String name, InfoNewLoginDto newLoginDto) {
        String subject = "Novo Login Detectado no TechDecode \uD83D\uDCBB";
        String text = String.format("""
                Olá %s,
                
                Detectamos um novo login na sua conta do TechDecode. Aqui estão os detalhes:
                
                Data e Hora: %s
                Dispositivo: %s
                Localização Aproximada: %s
                Se este login foi feito por você, não é necessário tomar nenhuma ação. Caso contrário, recomendamos que você:
                
                Altere sua senha imediatamente.
                Verifique suas configurações de segurança no painel do TechDecode.
                \uD83C\uDF10 Acesse sua conta: [Link para o painel de login]
                
                Estamos aqui para ajudar! Se tiver qualquer dúvida ou preocupação, entre em contato este email.
                
                Obrigado por fazer parte do TechDecode!
                Equipe TechDecode \uD83D\uDE80""", name, newLoginDto.date(), newLoginDto.dispositive(), newLoginDto.location());

        EmailDto emailDto = new EmailDto(id,emailTo, subject, text);
        this.emailProducer.sendMessage(emailDto);
    }
}
