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
 * Perhaps in the future this could be made even more generic, with more generic method names that
 * apply to all 'arcade games'.
 */

public interface FruitMachineInterface {

    // (TOTALLY IMPORTANT AND NECESSARY!) FRUIT MACHINE METHODS
    // GUARANTEED FOR ANY CONTROLLER NO MATTER WHAT THE MACHINE.

    public void spin();

    public void setPayouts(Payouts payouts);

    public Card[] getCards();

    public int getSpinnerCount();

    public GameState getGameState();

    public int getPlayerBalance();

    public CardCounts getLastScoringCounts();

    public int getLastPayout();

    public void reset();

    // OBSERVER METHODS

    public void registerObserver(GameStateObserver o);

    public void registerObserver(BalanceObserver o);

    public void registerObserver(SpinnerSetObserver o);

    public void removeObserver(GameStateObserver o);

    public void removeObserver(BalanceObserver o);

    public void removeObserver(SpinnerSetObserver o);
}
