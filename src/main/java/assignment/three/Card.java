package assignment.three;

import java.util.Random;

// The card combinations as enums
public enum Card {
    JOKER, JACK, QUEEN, KING, ACE;

    // Make this a constant to avoid values() call overhead.
    public static final int length = values().length;

    // Add this for a static helper function to return a random card.
    private static Random randomNumberGenerator = new Random();

    // Return a random card from the enum.
    // This could have been put in the 'spinner' class. Wasn't sure how to
    // separate concerns here, but I imagined 'drawing' a card from the deck.
    public static Card getRandomCard() {
        int randomIndex = randomNumberGenerator.nextInt(length);
        return values()[randomIndex];
    }

    // Returns the name in a nicely formatted way, with a capital letter.
    @Override
    public String toString() {
        String name = name();
        return name.substring(0, 1).toUpperCase() + name.substring(1).toLowerCase();
    }
}
