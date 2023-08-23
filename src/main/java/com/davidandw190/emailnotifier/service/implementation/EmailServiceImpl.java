package com.davidandw190.emailnotifier.service.implementation;

import com.davidandw190.emailnotifier.service.EmailService;

import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.io.File;
import java.util.Map;
import java.util.Objects;

import static com.davidandw190.emailnotifier.utils.EmailUtils.getEmailMessage;
import static com.davidandw190.emailnotifier.utils.EmailUtils.getVerificationURL;

/**
 * Implementation of the {@link EmailService} interface for sending email notifications.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

    public static final String EMAILTEMPLATE_HTML = "emailtemplate.html";
    private final JavaMailSender emailSender;
    private final TemplateEngine templateEngine;

    @Value("${spring.mail.verify.host}")
    private String host;
    @Value("${spring.mail.username}")
    private String fromEmail;


    @Value("${spring.mail.resources.path}")
    private static String PATH;
    @Value("${spring.mail.resources.image_attachment}")
    private static String IMAGE_ATTACHMENT;
    @Value("${spring.mail.resources.document_attachment}")
    private static String DOCUMENT_ATTACHMENT;


    public static final String UTF_8_ENCODING = "UTF-8";
    public static final String NEW_USER_ACCOUNT_VERIFICATION = "New User Account Verification";


    /**
     * Sends a simple email message.
     *
     * @param name  The name of the recipient.
     * @param to    The email address of the recipient.
     * @param token The verification token.
     */
    @Async
    @Override
    public void sendSimpleEmailMessage(String name, String to, String token) {
        try {
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setSubject(NEW_USER_ACCOUNT_VERIFICATION);
            mailMessage.setFrom(fromEmail);
            mailMessage.setTo(to);
            mailMessage.setText(getEmailMessage(name, host, token));
            emailSender.send(mailMessage);

        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }


    /**
     * Sends an email message with attachments.
     *
     * @param name  The name of the recipient.
     * @param to    The email address of the recipient.
     * @param token The verification token.
     */
    @Async
    @Override
    public void sendMessageWithAttachment(String name, String to, String token) {
        try {
            MimeMessage mailMessage = getMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mailMessage, true, UTF_8_ENCODING);

            helper.setPriority(1);
            helper.setSubject(NEW_USER_ACCOUNT_VERIFICATION);
            helper.setFrom(fromEmail);
            helper.setTo(to);
            helper.setText(getEmailMessage(name, host, token));

            var image =     new FileSystemResource(new File(PATH + IMAGE_ATTACHMENT));
            var document =  new FileSystemResource(new File(PATH + DOCUMENT_ATTACHMENT));

            helper.addAttachment(Objects.requireNonNull(image.getFilename()), image);
            helper.addAttachment(Objects.requireNonNull(document.getFilename()), document);

            emailSender.send(mailMessage);

        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    @Async
    @Override
    public void sendMessageWithEmbeddedImages(String name, String to, String token) {
        try {
            MimeMessage mailMessage = getMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mailMessage, true, UTF_8_ENCODING);

            helper.setPriority(1);
            helper.setSubject(NEW_USER_ACCOUNT_VERIFICATION);
            helper.setFrom(fromEmail);
            helper.setTo(to);
            helper.setText(getEmailMessage(name, host, token));

            var image =     new FileSystemResource(new File(PATH + IMAGE_ATTACHMENT));
            var document =  new FileSystemResource(new File(PATH + DOCUMENT_ATTACHMENT));

            helper.addInline(getContentId(image.getFilename()), image);
            helper.addInline(getContentId(document.getFilename()), document);

            emailSender.send(mailMessage);

        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }


    @Async
    @Override
    public void sendHtmlEmail(String name, String to, String token) {
        try {
            Context context = new Context();
            context.setVariables(Map.of(
                    "name", name,
                    "url", getVerificationURL(host, token)
            ));
            String text = templateEngine.process(EMAILTEMPLATE_HTML, context);

            MimeMessage mailMessage = getMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mailMessage, true, UTF_8_ENCODING);

            helper.setPriority(1);
            helper.setSubject(NEW_USER_ACCOUNT_VERIFICATION);
            helper.setFrom(fromEmail);
            helper.setTo(to);
            helper.setText(text, true);
            emailSender.send(mailMessage);

        } catch (Exception e) {
            log.error(e.getMessage());
        }

    }

    @Async
    @Override
    public void sendHtmlEmailWithEmbeddedFiles(String name, String to, String token) {

    }

    private MimeMessage getMimeMessage() {
        return emailSender.createMimeMessage();
    }

    private String getContentId(String filename) {
        return "<" + filename + ">";
    }
}
