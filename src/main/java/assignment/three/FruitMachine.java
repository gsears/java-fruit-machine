package assignment.three;

import java.util.ArrayList;

// Implements an interface to ensure compatability with controllers
public class FruitMachine implements FruitMachineInterface {

    // Balance constants
    private static final int STARTING_BALANCE = 100;
    private static final int WINNING_PLAYER_BALANCE = 150;
    private static final int LOSING_PLAYER_BALANCE = 0;
    // Scoring constants
    private static final int JOKER_MULTIPLIER = -25;
    private static final int TWO_OF_A_KIND_PAYOUT = 20;
    private static final int THREE_OF_A_KIND_PAYOUT = 50;
    // Machine constants
    private static final int NUMBER_OF_SPINNERS = 3;

    // Add observers to listen to different types of state change within the model
    // These will normally be controllers (or views, depending on MVC pattern used)
    private final ArrayList<BalanceObserver> balanceObservers = new ArrayList<BalanceObserver>();

    private final ArrayList<SpinnerSetObserver> spinnerSetObservers =
            new ArrayList<SpinnerSetObserver>();

    private final ArrayList<GameStateObserver> gameStateObservers =
            new ArrayList<GameStateObserver>();


    private Payouts payouts; // An object for looking up winning payouts
    private final Balance playerBalance; // Keeps track of the balance
    private final SpinnerSet spinners; // The card spinners
    private GameState gameState; // The game state

    private int spinnerCount;
    private int lastPayout; // The most recent payout, to send to controller
    private CardCounts lastScoringCounts; // The last card combination to send to controller

    public static void main(final String[] args) {

        // Create an object to get the winning payouts
        Payouts payouts = new Payouts();
        payouts.addPayout(2, TWO_OF_A_KIND_PAYOUT);
        payouts.addPayout(3, THREE_OF_A_KIND_PAYOUT);

        // Create the fruit machine model (this)
        FruitMachine fruitMachineModel = new FruitMachine(NUMBER_OF_SPINNERS);
        fruitMachineModel.setPayouts(payouts);

        // Create the fruit machine controller
        FruitMachineController fruitMachineController =
                new FruitMachineController(fruitMachineModel);

        // Create the fruit machine view
        FruitMachineView fruitMachineView = new FruitMachineView(fruitMachineController);

        // And hook it up to the controller
        fruitMachineController.addView(fruitMachineView);
    }

    // Constructor
    // TODO: Add variables to set the various components.
    public FruitMachine(int spinnerCount) {
        this.spinnerCount = spinnerCount;
        // Create spinners
        spinners = new SpinnerSet(spinnerCount);
        // Create player balance (initially 0)
        playerBalance = new Balance();
        // Initialise all values
        reset();
    }

    public void reset() {
        // Reset game state (observers notified in this function)
        setGameState(GameState.PLAY);
        // Reset balance and notify observers
        playerBalance.setBalance(STARTING_BALANCE);
        notifyBalanceObservers();
    }

    // Game Logic
    public void spin() {
        if (gameState == GameState.PLAY) {

            spinners.spin();

            // get current card counts
            CardCounts cardCounts = spinners.getCardCounts();

            // If there's a JOKER in the current card counts
            if (cardCounts.contains(Card.JOKER)) {
                // Get its value and set the payout accordingly
                int jokerCount = cardCounts.getCount(Card.JOKER);
                // Note: JOKER_MULTIPLIER is negative in this case.
                lastPayout = jokerCount * JOKER_MULTIPLIER;
                // Just add the joker counts to the scoring combo.
                lastScoringCounts = cardCounts.filterByCard(x -> x == Card.JOKER);

            } else {
                int highestCount = cardCounts.getMaxCardCount();
                // Set the payout (could be 0)
                lastPayout = payouts.getPayout(highestCount);
                // Add card counts to the scoring combo.
                lastScoringCounts = cardCounts.filterByCount(x -> x == highestCount);
            }
            // Update anything observing the spinners (in the controller)
            notifySpinnerObservers();
            // Update the player balance
            updatePlayerBalance();
        }
    }

    private void setGameState(final GameState gameState) {
        this.gameState = gameState;
        notifyGameStateObservers();
    }

    private void updatePlayerBalance() {
        // Only update if the last payout is not 0
        if (lastPayout != 0) {
            final int newBalance = playerBalance.change(lastPayout);
            notifyBalanceObservers();

            if (newBalance <= LOSING_PLAYER_BALANCE) {
                setGameState(GameState.LOST);
            } else if (newBalance >= WINNING_PLAYER_BALANCE) {
                setGameState(GameState.WON);
            }
        }
    }

    @Override
    public void setPayouts(final Payouts payouts) {
        this.payouts = payouts;
    }

    @Override
    public Card[] getCards() {
        return spinners.getCards();
    }

    @Override
    public int getSpinnerCount() {
        return spinnerCount;
    }

    @Override
    public GameState getGameState() {
        return gameState;
    }

    @Override
    public CardCounts getLastScoringCounts() {
        return lastScoringCounts;
    }

    @Override
    public int getLastPayout() {
        return lastPayout;
    }

    @Override
    public int getPlayerBalance() {
        return playerBalance.getBalance();
    }

    @Override
    public void registerObserver(final GameStateObserver o) {
        gameStateObservers.add(o);

    }

    @Override
    public void registerObserver(final BalanceObserver o) {
        balanceObservers.add(o);

    }

    @Override
    public void registerObserver(final SpinnerSetObserver o) {
        spinnerSetObservers.add(o);

    }

    @Override
    public void removeObserver(final GameStateObserver o) {
        gameStateObservers.remove(o);

    }

    @Override
    public void removeObserver(final BalanceObserver o) {
        balanceObservers.remove(o);

    }

    @Override
    public void removeObserver(final SpinnerSetObserver o) {
        spinnerSetObservers.remove(o);

    }

    private void notifyBalanceObservers() {
        balanceObservers.forEach(o -> o.updateBalance());
    }

    private void notifySpinnerObservers() {
        spinnerSetObservers.forEach(o -> o.updateSpinners());
    }

    private void notifyGameStateObservers() {
        gameStateObservers.forEach(o -> o.updateGameState());
    }

}
