import java.util.ArrayList;

/**
 * FruitMachineModel.java | Gareth Sears - 2493194
 * 
 * This is an abstract class which implements the bulk of fruit machine functionality. It uses the
 * 'template method' pattern, meaning it is a 'skeleton' which defers some of its functionality to
 * the subclasses.
 * 
 * In this case, it defers the calculation of payouts in its spin() method to the subclass. This is
 * because different variants of fruit machine may want different rewards / winning combinations.
 * 
 * It also requires the subclass to provide a constructor with the cardset and general
 * configuration.
 * 
 * Finally, it is worth noting that the model uses an observer pattern to notify controllers / views
 * of data changes. There are three different types of observer because each notification occurs at
 * different frequencies. This pattern is based on the 'pull' method in HeadFirst Java Design
 * Patterns (https://www.oreilly.com/library/view/head-first-design/0596007124/) and includes a
 * similar 'casting' style found in that book, thus the overloaded registerObserver() methods.
 * 
 * It is hoped that the patterns chosen demonstrate encapsulation, modularity and an architecture
 * that can easily be adapted.
 */

public abstract class FruitMachineModel {

    // --------- ABSTRACT METHODS / HOOKS ---------

    // An abstract method for subclasses so they can implement their own winning / losing payout
    // configurations. cardCounts is always provided by this class.
    protected abstract int getPayout(CardCounts cardCounts);

    // The subclass must provide this so controllers can display the scoring card combination.
    public abstract CardCounts getLastScoringCardCounts();

    // A hook for specifying the initial cards on reset.
    protected Card[] setInitialCards() {
        return null;
    }

    // -------- OBSERVERS --------
    private final ArrayList<SpinnerSetObserver> spinnerSetObservers =
            new ArrayList<SpinnerSetObserver>();

    private final ArrayList<BalanceObserver> balanceObservers = 
            new ArrayList<BalanceObserver>();

    private final ArrayList<GameStateObserver> gameStateObservers =
            new ArrayList<GameStateObserver>();

    // -------- ATTRIBUTES -----------

    // The card spinners.
    private SpinnerSet spinners;

    // Keeps track of the balance
    private Balance playerBalance;
    private int startingBalance;
    private int winningBalance;
    private int losingBalance;

    // Keeps track of game states: PLAY, WON, LOST.
    private GameState gameState;

    // The last 'score'
    private int lastPayout = 0;

    // --------------------------------

    // Constructor
    public FruitMachineModel(int spinnerCount, Card[] cardArray, int winningBalance,
            int losingBalance, int startingBalance) {

        this.winningBalance = winningBalance;
        this.losingBalance = losingBalance;
        this.startingBalance = startingBalance;

        spinners = new SpinnerSet(spinnerCount, cardArray);
        playerBalance = new Balance(); // Defaults to 0, but is set in 'reset'
        reset(); // Initialise all values
    }

    public void reset() {
        playerBalance.setBalance(startingBalance); // Reset balance
        notifyBalanceObservers(); // Notify balance observers

        // SUBCLASS HOOK
        // -------------
        // Gets value from 'setInitialCards' hook so subclasses can set initial cards. 
        // Otherwise, they're random.
        Card[] initialCards = setInitialCards();

        if(initialCards != null) {
            spinners.setCards(initialCards);
            notifySpinnerObservers();
        }

        // END HOOK
        
        setGameState(GameState.PLAY); // Ready play game state and notify state observers
    }

    // Set gameState and update observers.
    private void setGameState(GameState gameState) {
        this.gameState = gameState;
        notifyGameStateObservers();
    }

    // Ensure valid gameState, delegate payout calculation to subclasses, update spinner
    // observers when done. Final, because don't want subclasses overriding it.
    final public void spin() {
        if (gameState == GameState.PLAY) {
            spinners.spin();

            // SUBCLASS METHOD
            // The getPayout() below is delegated to subclasses as per 'template method' pattern.
            // Allows different machines to set their own payouts.
            // See CardFruitMachineModel.java for the concrete implementation.
            lastPayout = getPayout(spinners.getCardCounts());
            // END SUBCLASS METHOD

            updatePlayerBalance(lastPayout);
            notifySpinnerObservers();
        }
    }

    // Set balance and update game state and observers, only if there has been a change.
    private void updatePlayerBalance(int changeAmount) {

        if (changeAmount != 0) {
            int newBalance = playerBalance.change(changeAmount);
            notifyBalanceObservers();

            if (newBalance < losingBalance) { // "has less than 0 points"
                setGameState(GameState.LOST);
            } else if (newBalance >= winningBalance) { // "At least 150 points"
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

    // OBSERVER METHODS

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
        balanceObservers.forEach(o -> o.updateBalance());
    }

    private void notifySpinnerObservers() {
        spinnerSetObservers.forEach(o -> o.updateSpinners());
    }

    private void notifyGameStateObservers() {
        gameStateObservers.forEach(o -> o.updateGameState());
    }
}
