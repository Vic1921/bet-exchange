package ro.vic.bet_exchange.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "bets")
@Data
public class Bet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Match match;

    private double stake;
    private String status; // e.g., PENDING, WIN, LOSS
    private LocalDateTime betTime;

}
