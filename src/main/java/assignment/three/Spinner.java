package assignment.three;

import java.util.Random;

public class Spinner {

    // Static to avoid duplicating across spinners...
    private static Random randomNumberGenerator = new Random();
    private Card card;

    public Spinner() {

        spin(); // Initialise with a random card.
    }


    public Card spin() {
        // Get an array of cards from the enum
        Card[] cardArray = Card.values();
        // Generate a random index for the array
        int randomIndex = randomNumberGenerator.nextInt(cardArray.length);
        // Set the card to whatever the random index returns
        card = cardArray[randomIndex];

        return card; // Return the card for convenience (can be ignored)
    }

    // Just in case we missed it...
    public Card getCard() {
        return card;
    }
}
