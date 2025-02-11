package ro.vic.bet_exchange.service;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
public class BettingServiceTest {

    @Mock
    private JavaMailSender mailSender;

    @InjectMocks
    private BettingService bettingService;

    @Value("${notification.email}")
    private String notificationEmail;

    public void EmailNotificationTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testSendEmailNotification() {
        // Act
        bettingService.sendInsufficientBankrollNotification();

        // Assert
        ArgumentCaptor<SimpleMailMessage> messageCaptor = ArgumentCaptor.forClass(SimpleMailMessage.class);
        verify(mailSender, times(1)).send(messageCaptor.capture());
        SimpleMailMessage sentMessage = messageCaptor.getValue();
        assertEquals(notificationEmail, sentMessage.getTo()[0]);
        assertEquals("Insufficient Bankroll Notification", sentMessage.getSubject());
        assertEquals("Your bankroll is insufficient to place the next bet.", sentMessage.getText());
    }
}