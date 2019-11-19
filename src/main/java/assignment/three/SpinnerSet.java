package assignment.three;

import java.util.HashMap;
import java.util.Map;

public class SpinnerSet {

    private int count;
    private Spinner[] spinners;
    private Card[] cards;

    private Map<Card, Integer> currentCardCounts = new HashMap<Card, Integer>();
    private int currentMaxCardCount;

    // Three spinners is traditional
    public SpinnerSet() {
        this(3);
    }

    public SpinnerSet(int count) {
        this.count = count;
        // Allocate spinner array
        spinners = new Spinner[count];
        // Allocate card array
        cards = new Card[count];

        // Create spinner instances
        for (int i = 0; i < count; i++) {
            spinners[i] = new Spinner();
        }

        // initialise the spinners
        spin();
    }

    public void spin() {
        // Reset the card counts and maximum count.
        currentCardCounts.clear();
        currentMaxCardCount = 0;

        // For each spinner in the set.
        for (int i = 0; i < spinners.length; i++) {
            // Spin the spinner and get the new value
            Card card = spinners[i].spin();
            // Keep track of values for outputing later
            cards[i] = card;

            // If the new card already exists in our count cache.
            if (currentCardCounts.containsKey(card)) {
                // Increment the count by one
                int cardCount = currentCardCounts.get(card);
                cardCount++;
                // Set the new maximum card count if it's the new highest count
                if (cardCount > currentMaxCardCount) {
                    currentMaxCardCount = cardCount;
                }
                // Put the new count in our count cache.
                currentCardCounts.put(card, cardCount);
            } else {
                // Add the card to the cache and set its count to one.
                currentCardCounts.put(card, 1);
            }
        }

    }

    public Card[] getCards() {
        return cards;
    }

    public Map<Card, Integer> getCardCounts() {
        return currentCardCounts;
    }

    public int getMaxCardCount() {
        return currentMaxCardCount;
    }

    // Returns the number of spinners
    public int getSpinnerCount() {
        return count;
    }
}
