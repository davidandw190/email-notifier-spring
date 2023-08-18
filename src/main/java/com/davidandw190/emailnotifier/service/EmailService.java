package com.davidandw190.emailnotifier.service;

public interface EmailService {

    void sendSimpleEmailMessage(String name, String to, String token);

    void sendMessageWithAttachment(String name, String to, String token);

    void sendMessageWithEmbeddedImages(String name, String to, String token);

    void sendMessageWithEmbeddedFiles(String name, String to, String token);

    void sendHtmlEmail(String name, String to, String token);

    void sendHtmlEmailWithEmbeddedFiles(String name, String to, String token);
}
