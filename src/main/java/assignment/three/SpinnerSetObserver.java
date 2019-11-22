package assignment.three;

/**
 * SpinnerSetObserver.java | Gareth Sears - 2493194
 * 
 * This interface is used to update observers (in this case, any controllers) of any spinner set
 * state changes within the FruitMachine model. Useful for displaying payouts / card combinations.
 */
public interface SpinnerSetObserver {
    void updateSpinners();
}
