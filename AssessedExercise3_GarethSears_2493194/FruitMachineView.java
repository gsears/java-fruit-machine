import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * FruitMachineView.java | Gareth Sears - 2493194
 * 
 * The view layer of the app.
 * 
 * This consists of a JFrame main window and several panels. Given the relative simplicity, and to
 * avoid passing around events, only the Spinner panel was encapsulated into another class (as it
 * depends on the number of spinners and the other panels are 'fixed').
 * 
 * Encapsulation is done locally using private methods for panel creation.
 */

@SuppressWarnings("serial")
public class FruitMachineView extends JFrame {

    private static final int PANEL_PADDING = 10;

    FruitMachineController controller;

    // Components for Button panel.
    PaddedJPanel buttonPanelWrapper;
    JPanel buttonPanel;
    JButton spinButton;
    JButton newGameButton;

    // Components for Message panel.
    PaddedJPanel messagePanel;
    JLabel balanceDisplay;
    JLabel messageDisplay;
    JLabel victoryDisplay;

    // An encapsulated Spinner panel.
    SpinnerSetView spinnerPanel;

    // Constructor
    public FruitMachineView(FruitMachineController controller) {
        this.controller = controller; // Hook up the controller.

        // Main Window Layout
        setSize(600, 350);
        setLocation(10, 10);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(2, 2));

        // Initialise main panels
        createMessagePanel();
        createSpinnerPanel();
        createButtonPanel();

        // Add main panels to display
        add(messagePanel);
        add(new JPanel()); // Blank panel for 2nd grid position
        add(spinnerPanel);
        add(buttonPanelWrapper);

        this.setVisible(true);
    }

    // Message Panel
    private void createMessagePanel() {

        // New instance of padded panel.
        messagePanel = new PaddedJPanel(PANEL_PADDING);

        // Set layout. Uses box layout for flexi-vertical alignment with 'glue'.
        messagePanel.setLayout(new BoxLayout(messagePanel, BoxLayout.PAGE_AXIS));

        // Create instances of components
        balanceDisplay = new JLabel();
        messageDisplay = new JLabel();
        victoryDisplay = new JLabel();

        // Add to components panel
        messagePanel.add(balanceDisplay);
        messagePanel.add(Box.createVerticalGlue()); // Adds spacer
        messagePanel.add(messageDisplay);
        messagePanel.add(Box.createVerticalGlue()); // Adds spacer
        messagePanel.add(victoryDisplay);
    }

    // Spinner Panel
    private void createSpinnerPanel() {

        // New instance of spinner panel
        spinnerPanel = new SpinnerSetView(controller.getSpinnerCount());
        // Set padding
        spinnerPanel.setPadding(PANEL_PADDING);
    }

    // Button Panel
    private void createButtonPanel() {

        // New instance of padded panel as a wrapper.
        // Uses GridBagLayout to auto size border and centralise buttons.
        buttonPanelWrapper = new PaddedJPanel(PANEL_PADDING);
        buttonPanelWrapper.setLayout(new GridBagLayout());

        // New instance of panel for buttons. Grid layout 'stretches' them.
        buttonPanel = new JPanel(new GridLayout(2, 1));

        // Create instances of components
        spinButton = new JButton("Spin");
        newGameButton = new JButton("New Game");

        // Add button action event listeners
        // Uses anonymous classes (concise convention when listeners have a one-to-one
        // relationship with components, using the interface with the class would be overkill)

        spinButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.spin();
            }
        });

        newGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.reset();
            }
        });

        // Add components to button panel
        buttonPanel.add(spinButton);
        buttonPanel.add(newGameButton);
        

        // Add button panel to wrapper
        buttonPanelWrapper.add(buttonPanel);
    }

    // Set methods for controllers.

    public void setSpinners(Card[] cards) {
        spinnerPanel.setSpinners(cards);
    }

    public void setBalanceDisplay(String text) {
        balanceDisplay.setText(text);
    }

    public void setMessageDisplay(String text) {
        messageDisplay.setText(text);
    }

    public void setVictoryDisplay(String text) {
        victoryDisplay.setText(text);
    }

    public void setEnabledNewGameButton(boolean isEnabled) {
        newGameButton.setEnabled(isEnabled);
    }

    public void setEnabledSpinButton(boolean isEnabled) {
        spinButton.setEnabled(isEnabled);
    }
}
