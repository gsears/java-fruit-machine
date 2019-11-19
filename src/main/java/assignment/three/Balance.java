package assignment.three;

public class Balance {
    private int currentBalance;

    public Balance() {
        this(0);
    }

    public Balance(int initialBalance) {
        currentBalance = initialBalance;
    }

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
