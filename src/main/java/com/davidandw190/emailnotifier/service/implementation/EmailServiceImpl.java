package com.davidandw190.emailnotifier.service.implementation;

import com.davidandw190.emailnotifier.service.EmailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class EmailServiceImpl implements EmailService {

    @Value("${spring.mail.verify.host}")
    private String host;
    @Value("${spring.mail.username}")
    private String fromEmail;
    private final JavaMailSender emailSender;

    public static final String NEW_USER_ACCOUNT_VERIFICATION = "New User Account Verification";

    public EmailServiceImpl(JavaMailSender emailSender) {
        this.emailSender = emailSender;
    }

    @Override
    public void sendSimpleEmailMessage(String name, String to, String token) {
        try {
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setSubject(NEW_USER_ACCOUNT_VERIFICATION);
            mailMessage.setFrom(fromEmail);
            mailMessage.setTo(to);
            mailMessage.setText("Hello,  this is a simple email message!");
            emailSender.send(mailMessage);

        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    @Override
    public void sendMessageWithAttachment(String name, String to, String token) {

    }

    @Override
    public void sendMessageWithEmbeddedImages(String name, String to, String token) {

    }

    @Override
    public void sendMessageWithEmbeddedFiles(String name, String to, String token) {

    }

    @Override
    public void sendHtmlEmail(String name, String to, String token) {

    }

    @Override
    public void sendHtmlEmailWithEmbeddedFiles(String name, String to, String token) {

    }
}
