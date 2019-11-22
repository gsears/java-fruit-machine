package assignment.three;

/**
 * GameStateObserver.java | Gareth Sears - 2493194
 * 
 * This interface is used to update observers (in this case, any controllers) of any game state
 * changes within the FruitMachine model. Useful for disabling play buttons and such.
 */

public interface GameStateObserver {
    void updateGameState();
}
