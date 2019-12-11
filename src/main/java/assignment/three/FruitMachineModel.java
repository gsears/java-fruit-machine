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

public abstract class FruitMachineModel {

    // --------- ABSTRACT METHODS ---------

    // Abstract Spin method, which controls scoring etc.
    protected abstract int calculatePayout(CardCounts cardCounts);

    // Abstract methods that allow controllers to get scoring card combos and payouts.
    public abstract CardCounts getLastScoringCardCounts();

    public abstract int getLastPayout();

    // -------- OBSERVERS --------

    private final ArrayList<BalanceObserver> balanceObservers = 
            new ArrayList<BalanceObserver>();

    private final ArrayList<SpinnerSetObserver> spinnerSetObservers =
            new ArrayList<SpinnerSetObserver>();

    private final ArrayList<GameStateObserver> gameStateObservers =
            new ArrayList<GameStateObserver>();

    // -------- PROTECTED ----------

    // The card spinners. Protected so subclasses can directly access them, given their importance.
    protected SpinnerSet spinners;

    // -------- PRIVATE -----------

    // Keeps track of the balance
    private Balance playerBalance;
    private int startingBalance;
    private int winningBalance;
    private int losingBalance;

    // Keeps track of game states: PLAY, WON, LOST.
    private GameState gameState;


    // --------- CLASS CODE -------------

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
        setGameState(GameState.PLAY); // Reset game state and notify game state observers
        notifyBalanceObservers(); // Notify balance observers
    }

    // Ensure valid gameState, call subclass score calulator method, and update observers
    public void spin() {
        if (gameState == GameState.PLAY) {
            spinners.spin();  

            // This method is implemented by the subclass.
            int payout = calculatePayout(spinners.getCardCounts()); 

            setPlayerBalance(payout);
            notifySpinnerObservers();
        }
    }

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

    // ------- PRIVATE METHODS ----------

    // Set balance only if it changes. Update gameState and observers.
    // Can be used by subclasses.
    private void setPlayerBalance(int lastPayout) {

        if (lastPayout != 0) {
            int newBalance = playerBalance.change(lastPayout);
            notifyBalanceObservers();

            if (newBalance <= losingBalance) {
                setGameState(GameState.LOST);
            } else if (newBalance >= winningBalance) {
                setGameState(GameState.WON);
            }
        }
    }

    // Set gameState and update observers.
    private void setGameState(GameState gameState) {
        this.gameState = gameState;
        notifyGameStateObservers();
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
