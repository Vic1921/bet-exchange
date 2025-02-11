package ro.vic.bet_exchange.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.vic.bet_exchange.model.Bankroll;
import ro.vic.bet_exchange.model.Bet;
import ro.vic.bet_exchange.model.Match;
import ro.vic.bet_exchange.repository.BankrollRepository;
import ro.vic.bet_exchange.repository.BetRepository;
import ro.vic.bet_exchange.util.MartingaleCalculator;

import java.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

@Service
public class BettingService {

    @Autowired
    private BetRepository betRepository;

    @Autowired
    private BankrollRepository bankrollRepository;

    @Autowired
    private BetPlacer betPlacer;

    @Autowired
    private JavaMailSender mailSender;

    @Value("${notification.email}")
    private String notificationEmail;

    private int currentLossStreak = 0;
    private final double initialStake = 10.0;

    public void startBettingRound(Match match) {
        double stake = MartingaleCalculator.calculateNextStake(initialStake, currentLossStreak);

        Bankroll bankroll = bankrollRepository.findById(1L).orElse(new Bankroll());
        if (bankroll.getBalance() < stake) {
            sendInsufficientBankrollNotification();
            throw new IllegalStateException("Insufficient bankroll!");
        }

        boolean betPlaced = betPlacer.placeBet(match, stake);

        Bet bet = new Bet();
        bet.setMatch(match);
        bet.setStake(stake);
        bet.setBetTime(LocalDateTime.now());
        bet.setStatus("PENDING");
        betRepository.save(bet);

        bankroll.setBalance(bankroll.getBalance() - stake);
        bankroll.setUpdatedOn(LocalDateTime.now());
        bankrollRepository.save(bankroll);
    }

    void sendInsufficientBankrollNotification() {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(notificationEmail);
        message.setSubject("Insufficient Bankroll Notification");
        message.setText("Your bankroll is insufficient to place the next bet.");
        mailSender.send(message);
    }

    public void processBetResult(Bet bet, boolean isWin) {
        if (isWin) {
            double winnings = bet.getStake() * 3.0;
            Bankroll bankroll = bankrollRepository.findById(1L).get();
            bankroll.setBalance(bankroll.getBalance() + winnings);
            bankroll.setUpdatedOn(LocalDateTime.now());
            bankrollRepository.save(bankroll);
            bet.setStatus("WIN");
            currentLossStreak = 0;
        } else {
            bet.setStatus("LOSS");
            currentLossStreak++;
        }
        betRepository.save(bet);
    }
}
    // You can add additional methods to decide when to stop the round,
    // manage max bet limits, or adjust the strategy dynamically.

