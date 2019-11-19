package assignment.three;

import java.util.HashMap;
import java.util.Map;

// A class to make payouts future proof (in case of adding more spinners).
public class Payouts {

    // Initialised to Integer, not int, so it can be null if it doesn't exist.
    private Integer maxCardCount;
    private Map<Integer, Integer> payouts = new HashMap<Integer, Integer>();

    // Add a payout to the hashmap.
    public void addPayout(int cardCount, int payout) {
        // Store the highest card count possible
        if (maxCardCount == null || cardCount > maxCardCount) {
            maxCardCount = cardCount;
        }
        // Add the card count and payout to a map
        payouts.put(cardCount, payout);
    }

    public int getPayout(int cardCount) {
        // If we have a payout for that count, return it.
        if (payouts.keySet().contains(cardCount)) {
            return payouts.get(cardCount);
        }
        // If the card count is greater than the maximum card
        // count, then pay out the current highest prize.
        if (cardCount > maxCardCount) {
            cardCount = maxCardCount;
            return payouts.get(cardCount);
        } else {
            // Otherwise, we haven't got a payout for that
            // number of cards.
            return 0;
        }
    }
}
