/**
 * FruitMachine.java | Gareth Sears - 2493194
 * 
 * The main class for the fruit machine app. Sets up an MVC.
 */

public class FruitMachine {

    public static void main(final String[] args) {

        // The model takes advantage of polymorphism around a 'template method' class.
        // CardFruitMachineModel can easily be swapped for a machine with different config.
        FruitMachineModel fruitMachineModel = 
            new CardFruitMachineModel(); // The machine with 'joker / ace / king / ...'

        FruitMachineController fruitMachineController =
            new FruitMachineController(fruitMachineModel);

        FruitMachineView fruitMachineView = 
            new FruitMachineView(fruitMachineController);

        fruitMachineController.addView(fruitMachineView);
    }
}
