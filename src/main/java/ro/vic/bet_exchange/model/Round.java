package ro.vic.bet_exchange.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "rounds")
@NoArgsConstructor
@Getter
@Setter
public class Round {
    public final int MAX_SEASON_ROUND = 30;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    // Holds each 8 matches in a round
    private List<Match> matches;

    // Counts to 30 TODO: split in 2 groups
    private int SeasonRound;

}
