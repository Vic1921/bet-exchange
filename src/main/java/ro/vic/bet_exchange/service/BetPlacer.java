package ro.vic.bet_exchange.service;

import ro.vic.bet_exchange.model.Match;

public interface BetPlacer {
    /**
     * Place a bet for the given match and stake.
     * @return true if successful, false otherwise.
     */
    boolean placeBet(Match match, double stake);
}
