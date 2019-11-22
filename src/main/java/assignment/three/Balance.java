package assignment.three;

/**
 * Balance.java | Gareth Sears - 2493194
 * 
 * This class manages the player balance.
 */

public class Balance {
    private int currentBalance;

    // Initialise to 0 by default
    public Balance() {
        this(0);
    }

    public Balance(int initialBalance) {
        currentBalance = initialBalance;
    }

    // Expects negative numbers for losses.
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
