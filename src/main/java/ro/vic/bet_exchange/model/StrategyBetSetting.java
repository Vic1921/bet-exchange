package ro.vic.bet_exchange.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "strategy_bet_settings")
public class StrategyBetSetting {
    @Id
    private String strategyKey;
    private String strategyValue;
    // For instance: ("maxConsecutiveBets", "4")
}

