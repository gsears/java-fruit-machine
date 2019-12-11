package assignment.three;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

/**
 * Payouts.java | Gareth Sears - 2493194
 * 
 * An object for encapsulating different payouts for different machines.
 */

public class Payouts {

    // ASSIGNMENT FUNCTIONALITY
    // Fixed combo count has fixed payout, regardless of card type.

    // Store payouts by count in a hashmap.
    private Map<Integer, Integer> payoutsByCount = new HashMap<Integer, Integer>();

    // Add a card count to the map, with its payout.
    public Payouts addPayout(int cardCount, int payout) {
        payoutsByCount.put(cardCount, payout);
        return this; // For method chaining addPayouts for convenience
    }

    public int getPayout(int cardCount) {
        // If the card count has a payout, get it.
        if (payoutsByCount.keySet().contains(cardCount)) {
            return payoutsByCount.get(cardCount);
        } else {
            return 0;
        }
    }

    // NOT NECESSARY FOR ASSIGNMENT - BUT FOR STANDARD FRUIT MACHINE FUNCTIONALTIY.
    // Different card combinations can have different payouts.
    // E.g. '{ace: 1} = 5', '{ace: 2} = 10', '{ace: 2, king: 1} = 30'

    // Store payouts by card combination in a hashmap.
    private static Map<CardCounts, Integer> payoutsByCombo = new HashMap<CardCounts, Integer>();

    // Add a card combination to the map, with its payout.
    public Payouts addPayout(CardCounts cardCountCombo, int payout) {
        payoutsByCombo.put(cardCountCombo, payout);
        return this;
    }

    // If the card combination passed to this method has a payout, get it.
    public int getPayout(CardCounts cardCountCombo) {

        int payout = 0;

        // Loop through each of the possible winning combinations.
        for (Entry<CardCounts, Integer> entry : payoutsByCombo.entrySet()) {

            CardCounts winningCountCombo = entry.getKey();

            // Check if all the relevant winning combo cards are in the combination,
            // and their counts match.
            Iterator<Card> cards = winningCountCombo.getCardSet().iterator();
            boolean matchesCombo = true;

            while (matchesCombo && cards.hasNext()) {
                Card card = cards.next();

                if (!cardCountCombo.contains(card)
                        || winningCountCombo.getCount(card) != cardCountCombo.getCount(card)) {

                    // Otherwise break out of the loop, no point in continuing.
                    matchesCombo = false;
                }
            }

            // If the card combo under consideration matches a winning combo
            if (matchesCombo) {
                // Get the payout.
                int payoutForCombo = payoutsByCombo.get(winningCountCombo);
                // If the payout is greater than the current highest (for example, a two cherry
                // combo is better than a previously found one cherry combo), set the highest
                // payout.
                if (payoutForCombo > payout) {
                    payout = payoutForCombo;
                }
            }
        }

        return payout;
    }

    // TESTS FOR ABOVE

    // public static void main(String[] args) {
    // Payouts payouts = new Payouts();

    // CardCounts winningComboOne = new CardCounts();
    // winningComboOne.add(Card.JACK, 2);

    // CardCounts winningComboTwo = new CardCounts();
    // winningComboTwo.add(Card.JACK, 1);

    // CardCounts winningComboThree = new CardCounts();
    // winningComboThree.add(Card.KING, 1);

    // payouts.addPayout(winningComboThree, 100);
    // payouts.addPayout(winningComboOne, 70);
    // payouts.addPayout(winningComboTwo, 40);

    // CardCounts testComboOne = new CardCounts();
    // testComboOne.add(Card.JACK, 2);
    // testComboOne.add(Card.QUEEN, 1);

    // CardCounts testComboTwo = new CardCounts();
    // testComboTwo.add(Card.JACK, 1);
    // testComboTwo.add(Card.QUEEN, 1);
    // testComboTwo.add(Card.KING, 1);

    // CardCounts testComboThree = new CardCounts();
    // testComboThree.add(Card.QUEEN, 1);

    // System.out.println(payouts.getPayout(testComboOne));
    // System.out.println(payouts.getPayout(testComboTwo));
    // System.out.println(payouts.getPayout(testComboThree));

    // }
}
