package assignment.three;

/**
 * FruitMachine.java | Gareth Sears - 2493194
 * 
 * The main class for the fruit machine app. Sets up an MVC.
 */

public class FruitMachine {

    public static void main(final String[] args) {

        // MVC SETUP

        // The model takes advantage of polymorphism around an abstract class.
        // Can easily be swapped for a machine with different 'theme' / config.
        FruitMachineModel fruitMachineModel = 
                new CardFruitMachineModel(); // The machine with 'joker / ace / king / ...'

        FruitMachineController fruitMachineController =
                new FruitMachineController(fruitMachineModel);

        FruitMachineView fruitMachineView = 
                new FruitMachineView(fruitMachineController);

        fruitMachineController.addView(fruitMachineView);
    }
}
