package assignment.three;

import java.util.ArrayList;

/**
 * FruitMachine.java | Gareth Sears - 2493194
 * 
 * A model for a fruit machine.
 * 
 * The design was as modular as possible so different types of machine could easily be made by
 * changing a few variables. For example, different machines could have different payouts, numbers
 * of spinners, winning / losing points.
 */

public class FruitMachine implements FruitMachineInterface {

    public static void main(final String[] args) {

        Payouts payouts = new Payouts()
            .addPayout(2, 20) // TWO OF A KIND PAYOUT
            .addPayout(3, 50); // THREE OF A KIND PAYOUT

        Card[] cards = {
            new Card("Joker"), 
            new Card("Jack"), 
            new Card("Queen"), 
            new Card("King"),
            new Card("Ace")
        };

        // MVC Setup
        FruitMachine fruitMachineModel = 
            new FruitMachine(payouts, cards);

        FruitMachineController fruitMachineController =
            new FruitMachineController(fruitMachineModel);

        FruitMachineView fruitMachineView = 
            new FruitMachineView(fruitMachineController);

        fruitMachineController.addView(fruitMachineView);
    }

    // Class Body

    // Defaults for all machines
    private static final int DEFAULT_NUMBER_OF_SPINNERS = 3;
    private static final int DEFAULT_STARTING_BALANCE = 100;
    private static final int DEFAULT_WINNING_BALANCE = 150;
    private static final int DEFAULT_LOSING_BALANCE = 0;
    private static final int JOKER_MULTIPLIER = -25;

    // Use an observer pattern for MVC implementation. The observers are either views / controllers
    // depending on the architecture used. This allows views and controllers to respond to state
    // changes without the model having knowledge of them. There are 3 state changes to respond to.
    private final ArrayList<BalanceObserver> balanceObservers = 
        new ArrayList<BalanceObserver>();

    private final ArrayList<SpinnerSetObserver> spinnerSetObservers =
        new ArrayList<SpinnerSetObserver>();

    private final ArrayList<GameStateObserver> gameStateObservers =
        new ArrayList<GameStateObserver>();

    private int spinnerCount; // How many spinners a machine has
    private SpinnerSet spinners; // The card spinners
    private CardCounts lastScoringCounts; // A filtered card combination showing what 'scored'.

    private Payouts payouts; // An object for looking up winning payouts for this machine
    private int lastPayout; // The most recent payout

    private Balance playerBalance; // Keeps track of the balance
    private int startingBalance = DEFAULT_STARTING_BALANCE;
    private int winningBalance = DEFAULT_WINNING_BALANCE;
    private int losingBalance = DEFAULT_LOSING_BALANCE;

    private GameState gameState; // The game state

    // Constructor with default number of spinners
    public FruitMachine(Payouts payouts, Card[] cardArray) {
        this(DEFAULT_NUMBER_OF_SPINNERS, payouts, cardArray);
    }

    // Constructor
    public FruitMachine(int spinnerCount, Payouts payouts, Card[] cardArray) {

        this.spinnerCount = spinnerCount;
        this.payouts = payouts;

        // Instantiate attributes
        spinners = new SpinnerSet(spinnerCount, cardArray); // X number of spinners
        playerBalance = new Balance(); // Defaults to 0, but is set in 'reset'

        reset(); // Initialise all values
    }

    public void reset() {
        setGameState(GameState.PLAY); // Reset game state and notify game state observers
        playerBalance.setBalance(startingBalance); // Reset balance
        notifyBalanceObservers(); // Notify balance observers
    }

    // Game Logic
    public void spin() {
        if (gameState == GameState.PLAY) {

            spinners.spin();
            CardCounts cardCounts = spinners.getCardCounts(); // e.g. {QUEEN:1, JOKER:2}
            Card jokerCard = new Card("Joker"); // Create a joker card to do comparisons.

            if (cardCounts.contains(jokerCard)) {
                lastPayout = cardCounts.getCount(jokerCard) * JOKER_MULTIPLIER; // will be < 0

                // Filter the results to only include 'scoring' combinations.
                // e.g. {QUEEN:1, JOKER:2} -> {JOKER: 2}
                lastScoringCounts = cardCounts.filterByCard(x -> x.equals(jokerCard));

            } else {
                int highestCount = cardCounts.getMaxCardCount();
                lastPayout = payouts.getPayout(highestCount); // Returns 0 if not a scoring count.

                // Filter the results to only include 'scoring' combinations.
                // e.g. {QUEEN:1, JACK:2} -> {JACK:2}
                lastScoringCounts = cardCounts.filterByCount(x -> x == highestCount);
            }

            notifySpinnerObservers(); // Update any models / controllers with new spinner state
            updatePlayerBalance(); // Update the player balance
        }
    }

    private void setGameState(final GameState gameState) {
        this.gameState = gameState;
        notifyGameStateObservers(); // Update any models / controllers with new game state
    }

    private void updatePlayerBalance() {
         // Only do something if there's a change.
        if (lastPayout != 0) {
            int newBalance = playerBalance.change(lastPayout);
            notifyBalanceObservers(); // Update any models / controllers with new balance state

            // Change the game state if a victory / loss condition is met
            if (newBalance <= losingBalance) {
                setGameState(GameState.LOST);
            } else if (newBalance >= winningBalance) {
                setGameState(GameState.WON);
            }
        }
    }

    // These allow different values for different machines / modifying difficulty.

    @Override
    public void setStartingBalance(int startingBalance) {
        this.startingBalance = startingBalance;
    }

    @Override
    public void setLosingBalance(int losingBalance) {
        this.losingBalance = startingBalance;
    }

    @Override
    public void setWinningBalance(int winningBalance) {
        this.winningBalance = winningBalance;
    }

    @Override
    public void setPayouts(final Payouts payouts) {
        this.payouts = payouts;
    }

    // These are used to pull state to controllers.

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

    // These are used to register / notify / remove observers

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
