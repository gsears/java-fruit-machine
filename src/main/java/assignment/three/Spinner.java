package assignment.three;

import java.util.Random;

/**
 * Spinner.java | Gareth Sears - 2493194
 * 
 * A spinner returns a random card from the set passed to the constructor.
 */

public class Spinner {

    // Static to avoid duplicating across spinners.
    private static Random randomNumberGenerator = new Random();
    private Card[] cardSet; // The card set from which cards are selected.
    private Card card; // The current card.

    public Spinner(Card[] cardSet) {
        this.cardSet = cardSet;
        spin(); // Initialise with a random card.
    }

    public Card spin() {
        int randomIndex = randomNumberGenerator.nextInt(cardSet.length); // Get random index
        card = cardSet[randomIndex]; // Pick the card from the card set.
        return card; // For convenience, can be ignored.
    }

    public Card getCard() {
        return card;
    }
}
