package ro.vic.bet_exchange.util;

public class MartingaleCalculator {
    // Returns next stake if last bet failed, given initial stake and number of losses
    // Geometry progression: nextStake = initialStake * 2^(lossCount)
    public static double calculateNextStake(double initialStake, int lossCount) {
        // With doubling: nextStake = initialStake * 2^(lossCount)
        return initialStake * Math.pow(2, lossCount);
    }
}

