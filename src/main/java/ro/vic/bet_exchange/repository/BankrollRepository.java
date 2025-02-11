package ro.vic.bet_exchange.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ro.vic.bet_exchange.model.Bankroll;

@Repository
public interface BankrollRepository extends JpaRepository<Bankroll, Long> {
}
