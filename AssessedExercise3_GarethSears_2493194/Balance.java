/**
 * Balance.java | Gareth Sears - 2493194
 * 
 * This class manages the player balance.
 */

public class Balance {

    private int currentBalance;

    // Constructor
    public Balance() {
        this(0); // Initialise to 0 by default
    }

    public Balance(int initialBalance) {
        currentBalance = initialBalance;
    }

    // Expects negative numbers for fruit machine 'losses'.
    public int change(int amount) {
        currentBalance += amount;
        return currentBalance;
    }

    public int getBalance() {
        return currentBalance;
    }

    public void setBalance(int amount) {
        currentBalance = amount;
    }
}
