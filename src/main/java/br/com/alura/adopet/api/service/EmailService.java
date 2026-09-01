package br.com.alura.adopet.api.service;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.beans.JavaBean;
import java.time.format.DateTimeFormatter;

@Service
public class EmailService {

    private final JavaMailSender emailSender;
    public EmailService(JavaMailSender emailSender){
        this.emailSender = emailSender;
    }
public void disparar(String to, String subject, String text) {
    SimpleMailMessage email = new SimpleMailMessage();
    email.setFrom("adopet@email.com.br");
    email.setTo(to);
    email.setSubject(subject);
    email.setText(text);
    emailSender.send(email);
}
}
