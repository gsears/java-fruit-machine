package assignment.three;

/**
 * FruitMachineInterface.java | Gareth Sears - 2493194
 * 
 * This was created as the fruit machine model is using an observer pattern to update controllers,
 * so the register / remove methods need to be guaranteed.
 * 
 * Also, by creating a model which is an interface, it can be replaced without breaking any
 * Controllers / Views, as long as the new class guarantees the methods below.
 * 
 */

public interface FruitMachineInterface {

    // This is pretty essential for a fruit machine...

    public void spin();

    // These are used to customise machines / change difficulty.

    public void setStartingBalance(int startingBalance);

    public void setLosingBalance(int losingBalance);

    public void setWinningBalance(int winningBalance);

    public void setPayouts(Payouts payouts);

    // These are used for observer / model requests

    public Card[] getCards();

    public int getSpinnerCount();

    public GameState getGameState();

    public int getPlayerBalance();

    public CardCounts getLastScoringCounts();

    public int getLastPayout();

    public void reset();

    // These are used to register / remove observers

    public void registerObserver(GameStateObserver o);

    public void registerObserver(BalanceObserver o);

    public void registerObserver(SpinnerSetObserver o);

    public void removeObserver(GameStateObserver o);

    public void removeObserver(BalanceObserver o);

    public void removeObserver(SpinnerSetObserver o);
}
