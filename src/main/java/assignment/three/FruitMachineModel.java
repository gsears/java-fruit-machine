package assignment.three;

import java.util.ArrayList;

/**
 * FruitMachineModel.java | Gareth Sears - 2493194
 * 
 * This is an abstract class which implements functionality for all fruit machines, regardless
 * of card sets / payout rules. It requires an implementation of the 'calculatePayout' hook,
 * which subclasses use to determine the rules for 'paying out' based on the cardCounts passed
 * to it.
 * 
 * It was created to make the fruit machine design as modular as possible. Different fruit machines
 * with different themes and configurations can be built on top of it, such as in CardFruitMachineModel.
 */

public abstract class FruitMachineModel {

    // --------- ABSTRACT METHODS / INTERFACES ---------
    // See CardFruitMachineModel.java

    // A hook for concrete classes so they can implement their own winning / losing payout configurations 
    // based on the CardCounts object provided. Means different machines can have different 'rules'.
    protected abstract int getPayout(CardCounts cardCounts);

    // The subclass must provide this so controllers know which card combination 'scored' for displays.
    public abstract CardCounts getLastScoringCardCounts();

    // -------- OBSERVERS --------

    private final ArrayList<BalanceObserver> balanceObservers = 
            new ArrayList<BalanceObserver>();

    private final ArrayList<SpinnerSetObserver> spinnerSetObservers =
            new ArrayList<SpinnerSetObserver>();

    private final ArrayList<GameStateObserver> gameStateObservers =
            new ArrayList<GameStateObserver>();

    // -------- PRIVATE -----------

     // The card spinners.
    private SpinnerSet spinners;

    // Keeps track of the balance
    private Balance playerBalance;
    private int startingBalance;
    private int winningBalance;
    private int losingBalance;

    // Keeps track of game states: PLAY, WON, LOST.
    private GameState gameState;

    // The last scoring
    private int lastPayout = 0;

    // CONSTRUCTOR
    public FruitMachineModel(int spinnerCount, Card[] cardArray, int winningBalance,
            int losingBalance, int startingBalance) {

        this.winningBalance = winningBalance;
        this.losingBalance = losingBalance;
        this.startingBalance = startingBalance;

        spinners = new SpinnerSet(spinnerCount, cardArray);
        playerBalance = new Balance(); // Defaults to 0, but is set in 'reset'
        reset(); // Initialise all values
    }

    // PUBLIC METHODS

    public void reset() {
        playerBalance.setBalance(startingBalance); // Reset balance
        notifyBalanceObservers(); // Notify balance observers
        setGameState(GameState.PLAY); // Ready play game state and notify state observers
    }

    // Set gameState and update observers.
    private void setGameState(GameState gameState) {
        this.gameState = gameState;
        notifyGameStateObservers();
    }

    // Ensure valid gameState, call subclass hook, and update spin observers.
    public void spin() {
        if (gameState == GameState.PLAY) {
            spinners.spin();
            // calculatePayout is a hook implemented by subclasses, which can choose their
            // own payout configurations. See CardFruitMachineModel.java.
            lastPayout = getPayout(spinners.getCardCounts());
            updatePlayerBalance(lastPayout);
            notifySpinnerObservers();
        }
    }

    // Set balance and update game state and observers, only if it changes. 
    private void updatePlayerBalance(int changeAmount) {

        if (changeAmount != 0) {
            int newBalance = playerBalance.change(changeAmount);
            notifyBalanceObservers();

            if (newBalance <= losingBalance) {
                setGameState(GameState.LOST);
            } else if (newBalance >= winningBalance) {
                setGameState(GameState.WON);
            }
        }
    }

    // METHODS FOR CONTROLLERS TO 'PULL' DATA ON NOTIFICATION

    public Card[] getCards() {
        return spinners.getCards();
    }

    public int getSpinnerCount() {
        return spinners.getCards().length;
    }

    public GameState getGameState() {
        return gameState;
    }

    public int getPlayerBalance() {
        return playerBalance.getBalance();
    }

    public int getLastPayout() {
        return lastPayout;
    }

    // MODEL OBSERVER METHODS

    public void registerObserver(final GameStateObserver o) {
        gameStateObservers.add(o);

    }

    public void registerObserver(final BalanceObserver o) {
        balanceObservers.add(o);

    }

    public void registerObserver(final SpinnerSetObserver o) {
        spinnerSetObservers.add(o);

    }

    public void removeObserver(final GameStateObserver o) {
        gameStateObservers.remove(o);

    }

    public void removeObserver(final BalanceObserver o) {
        balanceObservers.remove(o);

    }

    public void removeObserver(final SpinnerSetObserver o) {
        spinnerSetObservers.remove(o);

    }

    private void notifyBalanceObservers() {
        System.out.println(playerBalance.getBalance());
        balanceObservers.forEach(o -> o.updateBalance());
    }

    private void notifySpinnerObservers() {
        spinnerSetObservers.forEach(o -> o.updateSpinners());
    }

    private void notifyGameStateObservers() {
        gameStateObservers.forEach(o -> o.updateGameState());
    }
}
