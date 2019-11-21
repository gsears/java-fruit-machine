package assignment.three;

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

@SuppressWarnings("serial")
public class FruitMachineView extends JFrame {

    private static final int PANEL_PADDING = 10;

    FruitMachineController controller;

    PaddedJPanel buttonPanelWrapper;
    JPanel buttonPanel;
    JButton newGameButton;
    JButton spinButton;

    PaddedJPanel messagePanel;
    JLabel balanceDisplay;
    JLabel messageDisplay;
    JLabel victoryDisplay;

    SpinnerSetView spinnerPanel;

    public FruitMachineView(FruitMachineController controller) {
        this.controller = controller;

        // Layout
        setSize(600, 350);
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

    private void createMessagePanel() {
        // Initialise Panel with padding
        messagePanel = new PaddedJPanel(PANEL_PADDING);

        // Set Layout
        messagePanel.setLayout(new BoxLayout(messagePanel, BoxLayout.PAGE_AXIS));

        // Create instances
        balanceDisplay = new JLabel();
        messageDisplay = new JLabel();
        victoryDisplay = new JLabel();

        // Add to panel
        messagePanel.add(balanceDisplay);
        messagePanel.add(Box.createVerticalGlue()); // Adds spacer
        messagePanel.add(messageDisplay);
        messagePanel.add(Box.createVerticalGlue()); // Adds spacer
        messagePanel.add(victoryDisplay);
    }

    private void createSpinnerPanel() {
        // Initialise Panel
        spinnerPanel = new SpinnerSetView(controller.getSpinnerCount());
        // Set padding
        spinnerPanel.setPadding(PANEL_PADDING);
    }

    private void createButtonPanel() {
        // Initialise Panels
        buttonPanelWrapper = new PaddedJPanel(PANEL_PADDING);
        buttonPanel = new JPanel(new GridLayout(2, 1));

        // Set layout of wrapper to auto size border and centralise inner panel
        buttonPanelWrapper.setLayout(new GridBagLayout());

        // Create instances
        newGameButton = new JButton("New Game");
        spinButton = new JButton("Spin");

        // Add listeners

        // Uses anonymous classes as this is a concise convention
        // when listeners have a one-to-one relationship with components.

        newGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.reset();
            }
        });

        spinButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.spin();
            }
        });

        // Add components to button panel
        buttonPanel.add(newGameButton);
        buttonPanel.add(spinButton);

        // Add button panel to wrapper
        buttonPanelWrapper.add(buttonPanel);
    }

    // Methods for controller.
    public void setSpinners(Card[] cards) {
        spinnerPanel.setCards(cards);
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
