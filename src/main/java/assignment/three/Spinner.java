package assignment.three;

import java.util.Random;

/**
 * Spinner.java | Gareth Sears - 2493194
 * 
 * A spinner returns a random enum from the Card class.
 */

public class Spinner {

    // Static to avoid duplicating across spinners...
    private static Random randomNumberGenerator = new Random();
    private Card[] cardArray;
    private Card card;

    public Spinner(Card[] cardArray) {
        this.cardArray = cardArray; // pass by ref.
        spin(); // Initialise with a random card.
    }

    public Card spin() {

        int randomIndex = randomNumberGenerator.nextInt(cardArray.length); // Get random index
        card = cardArray[randomIndex]; // Get random card and set the attribute

        return card; // Return the card for convenience (can be ignored)
    }

    public Card getCard() {
        return card;
    }
}
