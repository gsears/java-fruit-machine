/**
 * SpinnerSet.java | Gareth Sears - 2493194
 * 
 * Contains a set of spinners, representing the 'meat' of a fruit machine! Stores various objects
 * for outside classes to interact with.
 * 
 * The most important of these are the card array (an array of what is actually displayed) and a
 * CardCount object which stores the card counts in a cache to calculate payouts and for displays.
 */

public class SpinnerSet {

    private int size;
    private Spinner[] spinners;
    private Card[] cards; // Holds an array of cards.

    CardCounts cardCounts = new CardCounts(); // Card count cache

    public SpinnerSet(int size, Card[] cardSet) {
        this.size = size;

        // Initialise 'count' number of spinners.
        spinners = new Spinner[size];
        cards = new Card[size];

        // Create spinner instances.
        for (int i = 0; i < size; i++) {
            spinners[i] = new Spinner(cardSet);
        }

        spin(); // Initialise card array and counts.
    }

    public void spin() {

        cardCounts.clear(); // Reset card count cache

        for (int i = 0; i < size; i++) {
            Spinner spinner = spinners[i]; // Get each spinner
            Card newCard = spinner.spin(); // Spin and get the value
            cardCounts.add(newCard); // Add to the card count cache.
            cards[i] = newCard; // Set the internal card array
        }
    }

    public void setCards(Card[] cardArray) {
        if (cardArray.length != size) {
            throw new IllegalArgumentException("Card array length must match spinner set size.");
        }

        for (int i = 0; i < size; i++) {
            spinners[i].setCard(cardArray[i]);
            cards[i] = cardArray[i];
        }
    }

    // Get the cards shown on the spinners in order (e.g. for spinner views)
    public Card[] getCards() {
        return cards;
    }

    // Get the card count cache (e.g. for calculating payouts / text displays)
    public CardCounts getCardCounts() {
        return cardCounts;
    }
}
