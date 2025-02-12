package ro.vic.bet_exchange.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.vic.bet_exchange.model.Bet;
import ro.vic.bet_exchange.model.Match;
import ro.vic.bet_exchange.repository.BankrollRepository;
import ro.vic.bet_exchange.repository.BetRepository;
import ro.vic.bet_exchange.repository.MatchRepository;
import ro.vic.bet_exchange.service.BettingService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/betting")
public class BettingController {

    @Autowired
    private BettingService bettingService;

    @Autowired
    private MatchRepository matchRepository;

    @Autowired
    private BetRepository betRepository;

    @Autowired
    private BankrollRepository bankrollRepository;

    // Endpoint to start a betting round for a given match ID
    @PostMapping("/start/{matchId}")
    public ResponseEntity<String> startBetting(@PathVariable Long matchId) {
        Optional<Match> matchOpt = matchRepository.findById(matchId);
        if (matchOpt.isPresent()) {
            try {
                bettingService.startBettingRound(matchOpt.get());
                return ResponseEntity.ok("Bet placed successfully.");
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body("Error: " + e.getMessage());
            }
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Match not found.");
        }
    }

    // Endpoint to update the result of a bet (simulate callback)
    @PostMapping("/result/{betId}")
    public ResponseEntity<String> processResult(@PathVariable Long betId,
                                                @RequestParam boolean win) {
        Optional<Bet> betOpt = Optional.of(betRepository.getById(betId));
        bettingService.processBetResult(betOpt.get(), win);
        return ResponseEntity.ok("Bet result processed.");
    }

    // Additional endpoints expose current bankroll, bet history for visualization in FE.

    // Endpoint to get the current bankroll balance
    @GetMapping("/bankroll")
    public double getBankrollBalance() {
        return bankrollRepository.findById(1L).orElseThrow().getBalance();
    }


    // Endpoint to get the bet history
    @GetMapping("/history")
    public ResponseEntity<List<Bet>> getBetHistory() {
        List<Bet> betHistory = betRepository.findAll();;
        return ResponseEntity.ok(betHistory);
    }

}

