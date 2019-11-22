package assignment.three;

/**
 * BalanceObserver.java | Gareth Sears - 2493194
 * 
 * This interface is used to update observers (in this case, any controllers) of any balance changes
 * within the FruitMachine model. Useful for updating balance displays.
 */

public interface BalanceObserver {
    void updateBalance();
}
