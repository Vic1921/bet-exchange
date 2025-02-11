package ro.vic.bet_exchange.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class SmtpConfigurationTest {

    @Autowired
    private JavaMailSender mailSender;

    @Test
    public void testSmtpConfiguration() {
        // Arrange
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo("victor.binzar61@gmail.com");
        message.setSubject("Test Email");
        message.setText("This is a test email to verify SMTP configuration.");

        // Act
        mailSender.send(message);

        // Assert
        assertNotNull(mailSender);
    }
}