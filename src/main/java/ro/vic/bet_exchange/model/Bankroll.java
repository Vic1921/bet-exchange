package ro.vic.bet_exchange.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "bankroll")
public class Bankroll {
    // Tracks current bankroll and history.

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private double balance;
    private LocalDateTime updatedOn;

}

