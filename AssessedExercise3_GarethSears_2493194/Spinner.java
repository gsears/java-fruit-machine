import java.util.Arrays;
import java.util.Random;

/**
 * Spinner.java | Gareth Sears - 2493194
 * 
 * A spinner returns a random card from the card set passed to the constructor.
 */

public class Spinner {

    // Static to avoid duplicating across spinners.
    private static Random randomNumberGenerator = new Random();
    private Card[] cardSet;
    private Card card; // The current card.

    // Java passes arrays by reference (I hope!) so this isn't too wasteful.
    // https://www.tutorialspoint.com/How-to-pass-Arrays-to-Methods-in-Java
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

    // Set the card. Useful for initial values.
    public void setCard(Card card) {
        // Check to see if the card is in the provided card set, otherwise throw.
        if (Arrays.stream(cardSet).anyMatch(card::equals)) {
            this.card = card;
        } else {
            throw new IllegalArgumentException(
                    String.format("%s is not part of this spinner's Card Set", card));
        }
    }
}
