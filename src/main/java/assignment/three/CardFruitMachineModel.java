package assignment.three;

public class CardFruitMachineModel extends FruitMachineModel {

    private static final int NUMBER_OF_SPINNERS = 3;
    private static final int STARTING_BALANCE = 100;
    private static final int WINNING_BALANCE = 150;
    private static final int LOSING_BALANCE = 0;
    private static final int NEGATIVE_JOKER_MULTIPLIER = -25;

    private static final Payouts PAYOUTS = new Payouts()
        .addPayout(2, 20)
        .addPayout(3, 50);

    private static final Card[] CARDS = {
        new Card("Joker"), 
        new Card("Jack"), 
        new Card("Queen"),
        new Card("King"), 
        new Card("Ace")
    };

    // Keep track of winning / losing payouts and their card combos.
    private int lastPayout = 0;
    private CardCounts lastScoringCardCounts;

    CardFruitMachineModel() {
        // Call the abstract model class, which takes care of base functionality.
        super(NUMBER_OF_SPINNERS, CARDS, WINNING_BALANCE, LOSING_BALANCE, STARTING_BALANCE);
    }

    // This method is called after every spin in the abstract class, and uses the
    // return value to set the balance in the abstract model.
    @Override
    protected int calculatePayout(CardCounts cardCounts) {

        Card jokerCard = new Card("Joker"); // To check for losing conditions.
        
        // LOSING
        // Return a negative number for each joker in the spinners.
        if (cardCounts.contains(jokerCard)) {
            lastPayout = cardCounts.getCount(jokerCard) * NEGATIVE_JOKER_MULTIPLIER;
            // Get the 'scoring' combo e.g. {QUEEN:1, JOKER:2} -> {JOKER: 2}
            lastScoringCardCounts = cardCounts.filterByCard(x -> x.equals(jokerCard)); 
            return lastPayout;

        // WINNING / NO SCORE
        // Get payout for most reoccuring card in the spinners.
        } else {
            int highestCount = cardCounts.getMaxCardCount();
            lastPayout = PAYOUTS.getPayout(highestCount);
            // Get the 'scoring' combo e.g. {QUEEN:1, JACK:2} -> {JACK:2}
            lastScoringCardCounts = cardCounts.filterByCount(x -> x == highestCount);
            return lastPayout;
        }
    }

    // Methods that spinnerSetObervers use to pull spinner data after a spin.

    @Override
    public CardCounts getLastScoringCardCounts() {
        return lastScoringCardCounts;
    }

    @Override
    public int getLastPayout() {
        return lastPayout;
    }
}
