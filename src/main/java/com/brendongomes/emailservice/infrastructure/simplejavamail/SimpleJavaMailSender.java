package com.brendongomes.emailservice.infrastructure.simplejavamail;


import com.brendongomes.emailservice.adapters.EmailSenderGateway;
import com.brendongomes.emailservice.core.exceptions.EmailServiceException;
import org.simplejavamail.MailException;
import org.simplejavamail.api.email.Email;
import org.simplejavamail.api.mailer.Mailer;
import org.simplejavamail.email.EmailBuilder;
import org.simplejavamail.mailer.MailerBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;

@Service
public class SimpleJavaMailSender implements EmailSenderGateway {

    private final Mailer mailSender;

    @Autowired // injetado pelo spring boot
    public SimpleJavaMailSender(Mailer mailSender) {
        this.mailSender = mailSender;
    }

    @Override
    public void sendEmail(String to, String subject, String body) {
        Email email = EmailBuilder
                .startingBlank()
                .to(to)
                .from("spring.boot.api.example@mailtrap.com")
                .withSubject(subject)
                .withHTMLText(body)
                .buildEmail();
        try {
            this.mailSender.sendMail(email);
        } catch (MailException exception) {
            throw new EmailServiceException("Failure while sending email", exception);
        }
    }
}
