package assignment.three;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

/**
 * Payouts.java | Gareth Sears - 2493194
 * 
 * An object for encapsulating payouts for different machines.
 * 
 */
public class Payouts {

    private Integer maxCardCount;
    private Map<Integer, Integer> payoutsByCount = new HashMap<Integer, Integer>();

    // Add a payout to the hashmap.
    public void addPayout(int cardCount, int payout) {
        // Store the highest card count possible
        if (maxCardCount == null || cardCount > maxCardCount) {
            maxCardCount = cardCount;
        }
        // Add the card count and payout to a map
        payoutsByCount.put(cardCount, payout);
    }

    public int getPayout(int cardCount) {
        // If we have a payout for that count, return it.
        if (payoutsByCount.keySet().contains(cardCount)) {
            return payoutsByCount.get(cardCount);
        }
        // If the card count is greater than max payout count, return the highest payout we can.
        if (cardCount > maxCardCount) {
            return payoutsByCount.get(maxCardCount);
        } else {
            return 0; // No payout.
        }
    }

    // ADDED FOR COMPLETENESS / PORTFOLIO, NOT FOR ASSIGNMENT
    // "predict what might change and encapsulate it"

    // Used to return payouts by a combination of cards.
    // E.g. 'one cherry = 2', 'two cherries and 1 bar = 10'
    private static Map<CardCounts, Integer> payoutsByCombo = new HashMap<CardCounts, Integer>();

    // Add a combination of card counts that pays out.
    public void addPayout(CardCounts cardCountCombo, int payout) {
        payoutsByCombo.put(cardCountCombo, payout);
    }

    // Get the payout from a combination of card counts.
    public int getPayout(CardCounts cardCountCombo) {

        int payout = 0;

        // Loop through each of the possible payout combinations.
        for (Entry<CardCounts, Integer> entry : payoutsByCombo.entrySet()) {
            CardCounts winningCountCombo = entry.getKey();

            // Check if the cards exist in a winning payout combo.
            Iterator<Card> cards = winningCountCombo.getCardSet().iterator();
            boolean matchesCombo = true;

            while (matchesCombo && cards.hasNext()) {
                Card card = cards.next();

                System.out.println(card);

                // If the card exists, and it matches the count in the winning combo, continue.
                if (!cardCountCombo.contains(card)
                        || winningCountCombo.getCount(card) != cardCountCombo.getCount(card)) {

                    // Otherwise break out of the loop.
                    matchesCombo = false;
                }
            }

            // If the card combo indeed matches a winning combo
            if (matchesCombo) {
                System.out.println("Winner: " + cardCountCombo);
                // Get the payout.
                int payoutForCombo = payoutsByCombo.get(winningCountCombo);
                // If the payout is greater than the current highest (for example, a two cherry
                // combo is better than a previously found one cherry combo) set the highest payout.
                if (payoutForCombo > payout) {
                    payout = payoutForCombo;
                }
            }
        }

        return payout;
    }

    // TESTS FOR EXTREME FUNCTIONALITY

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
