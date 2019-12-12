package assignment.three;

import java.util.ArrayList;

/**
 * FruitMachineModel.java | Gareth Sears - 2493194
 * 
 * This is an abstract class which implements functionality for all fruit machines, regardless
 * of card sets / payout rules. It requires a concrete subclass to implement the 'getPayout' 
 * method, which acts as a 'hook' into the spin() method. Inside 'getPayout', the subclass must
 * outline the rules for 'payouts' and use the provided CardCounts object to return these payouts
 * as an int. This method is called every spin.
 * 
 * The model also uses an observer pattern to notify controllers / views of data changes. There are 
 * three different types of observer because each notification occurs at different frequencies. 
 * This pattern was read about in HeadFirst Java Design Patterns and is implemented in a similar manner 
 * here, including the 'casting' style.
 * 
 * It is hoped that the patterns chosen demonstrate encapsulation, modularity and an architecture that
 * can easily be adapted (for when we need to roll out that full casino, fast.)
 */

public abstract class FruitMachineModel {

    // --------- ABSTRACT METHODS / INTERFACES ---------

    // A hook for concrete classes so they can implement their own winning / losing payout configurations 
    // based on the CardCounts object provided. Means different machines can have different 'rules'.
    protected abstract int getPayout(CardCounts cardCounts);

    // The subclass must provide this so controllers know which card combination 'scored' for displays.
    public abstract CardCounts getLastScoringCardCounts();

    // -------- OBSERVERS --------
    // Uses the observer pattern to 

    private final ArrayList<SpinnerSetObserver> spinnerSetObservers =
            new ArrayList<SpinnerSetObserver>();

    private final ArrayList<BalanceObserver> balanceObservers = 
            new ArrayList<BalanceObserver>();

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
