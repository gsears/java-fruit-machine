/**
 * BalanceObserver.java | Gareth Sears - 2493194
 * 
 * This interface is used with observers monitoring any balance changes in a FruitMachineModel (in
 * this case, controllers). Useful for updating balance displays.
 * 
 * Based on the observer MVC pattern in Head First Design Patterns.
 * (https://www.oreilly.com/library/view/head-first-design/0596007124/)
 */

public interface BalanceObserver {
    void updateBalance();
}
