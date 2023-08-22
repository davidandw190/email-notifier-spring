package com.davidandw190.emailnotifier.service;

import jakarta.mail.MessagingException;

public interface EmailService {

    void sendSimpleEmailMessage(String name, String to, String token);

    void sendMessageWithAttachment(String name, String to, String token) throws MessagingException;

    void sendMessageWithEmbeddedImages(String name, String to, String token);

    void sendMessageWithEmbeddedFiles(String name, String to, String token);

    void sendHtmlEmail(String name, String to, String token);

    void sendHtmlEmailWithEmbeddedFiles(String name, String to, String token);
}
