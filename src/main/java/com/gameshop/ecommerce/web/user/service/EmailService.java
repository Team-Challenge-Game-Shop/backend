package com.gameshop.ecommerce.web.user.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailSendException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;
    @Value("${spring.mail.username}")
    private String username;
    @Value("${spring.mail.from.name}")
    private String fromName;
    @Value("${app.frontend.url}")
    private String appUrl;

    public void sendConfirmationEmail(String recipientAddress, String confirmationCode) {
        String subject = "Confirm your email address";
        String confirmationUrl = appUrl + "/verify?token=" + confirmationCode;
        try {
            sendHtmlMessage(recipientAddress, subject, confirmationUrl);
        } catch (MessagingException | UnsupportedEncodingException e) {
            throw new MailSendException("Could not send email", e);
        }
    }

    private void sendHtmlMessage(String to, String subject, String htmlBody) throws MessagingException, UnsupportedEncodingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
        helper.setFrom(username, fromName);
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(htmlBody, true);
        mailSender.send(message);
    }
}
