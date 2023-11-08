package com.brendongomes.emailservice.infrastructure.config;

import org.simplejavamail.api.mailer.Mailer;
import org.simplejavamail.api.mailer.config.TransportStrategy;
import org.simplejavamail.mailer.MailerBuilder;
import org.simplejavamail.mailer.internal.MailerRegularBuilderImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SimpleJavaMailConfig {

    @Value("${mailtrap.username}")
    private String username;

    @Value("${mailtrap.password}")
    private String password;

    @Bean
    public Mailer mailSender() {
        MailerRegularBuilderImpl mailer = MailerBuilder
                .withSMTPServer("sandbox.smtp.mailtrap.io", 2525, this.username, this.password)
                .withTransportStrategy(TransportStrategy.SMTP);

        return mailer.buildMailer();
    }
}
