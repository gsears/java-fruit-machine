/**
 * CardFruitMachineModel.java | Gareth Sears - 2493194
 * 
 * A subclass from the abstract FruitMachineModel class. These classes together implement the
 * 'template method' pattern, deferring the configuration, card set and calculation of 'payouts' to
 * the 'concrete' class (this one). Payout calculation is done inside the getPayout() method.
 * 
 * This pattern is similar to that found in JPanel's paint() method, in that it provides an object
 * (cardCounts) that is otherwise inaccessible in order to complete the abstract class's algorithm.
 * 
 * Hopefully you'll see that other fruit machines with different card sets, configurations, payouts,
 * etc. can be made very easily using this architecture.
 */

public class CardFruitMachineModel extends FruitMachineModel {

    private static final int NUMBER_OF_SPINNERS = 3;
    private static final int STARTING_BALANCE = 100;
    private static final int WINNING_BALANCE = 150;
    private static final int LOSING_BALANCE = 0;

    // For losing 'payouts'
    private static final Card JOKER_CARD = new Card("Joker");
    private static final Card JACK_CARD = new Card("Jack");
    private static final Card QUEEN_CARD =  new Card("Queen");
    private static final Card KING_CARD =  new Card("King");
    private static final Card ACE_CARD =  new Card("Ace");

    private static final int NEGATIVE_JOKER_MULTIPLIER = -25;

    private static final Card[] CARDS = {
        JOKER_CARD,
        JACK_CARD,
        QUEEN_CARD,
        KING_CARD,
        ACE_CARD
    };

    private static final Card[] INITIAL_CARDS = {
        KING_CARD,
        QUEEN_CARD,
        JACK_CARD
    };

    private CardCounts lastScoringCardCounts;

    CardFruitMachineModel() {
        // Call the abstract model class, which takes care of base functionality.
        super(NUMBER_OF_SPINNERS, CARDS, WINNING_BALANCE, LOSING_BALANCE, STARTING_BALANCE);
    }

    @Override
    protected Card[] setInitialCards() {
        return INITIAL_CARDS;
    }

    // This method is called after every spin in the abstract parent class, and uses the
    // return value to set its balance. This was extracted from the 'template method' because
    // it is the most likely to change between machines.
    @Override
    protected int getPayout(CardCounts cardCounts) {

        // LOSING
        // Return a negative payout which varies according to the # jokers in the spinners.
        if (cardCounts.contains(JOKER_CARD)) {
            // Set the 'scoring' combo e.g. {QUEEN:1, JOKER:2} -> {JOKER: 2}
            lastScoringCardCounts = cardCounts.filterByCard(x -> x.equals(JOKER_CARD));
            return cardCounts.getCount(JOKER_CARD) * NEGATIVE_JOKER_MULTIPLIER;

            // WINNING / NO SCORE
            // Return payout depending on the max card count in the spinners.
        } else {
            int highestCount = cardCounts.getMaxCardCount();
            // Set the 'scoring' combo e.g. {QUEEN:1, JACK:2} -> {JACK:2}
            lastScoringCardCounts = cardCounts.filterByCount(x -> x == highestCount);

            if (highestCount > 2) {
                return 50;
            } else if (highestCount == 2) {
                return 20;
            } else {
                return 0;
            }
        }
    }

    // SpinnerSetObervers use this to pull the 'scoring combo' after a spin for display.
    @Override
    public CardCounts getLastScoringCardCounts() {
        return lastScoringCardCounts;
    }

}
