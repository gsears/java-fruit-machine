package assignment.three;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

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
        setSize(600, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(2, 2));

        // Add Panels
        createMessagePanel();
        createSpinnerPanel();
        createButtonPanel();

        add(messagePanel);
        add(new JPanel()); // Blank panel for 2nd grid position
        add(spinnerPanel);
        add(buttonPanelWrapper);

        this.setVisible(true);
    }

    private void createMessagePanel() {
        // Initialise Panel
        messagePanel = new PaddedJPanel(PANEL_PADDING);

        // Set layout
        messagePanel.setLayout(new BoxLayout(messagePanel, BoxLayout.PAGE_AXIS));

        balanceDisplay = new JLabel();
        messageDisplay = new JLabel();
        victoryDisplay = new JLabel();

        messagePanel.add(balanceDisplay);
        messagePanel.add(Box.createVerticalGlue());
        messagePanel.add(messageDisplay);
        messagePanel.add(Box.createVerticalGlue());
        messagePanel.add(victoryDisplay);
    }

    private void createSpinnerPanel() {

        spinnerPanel = new SpinnerSetView(controller.getSpinnerCount());
        spinnerPanel.setPadding(PANEL_PADDING);
    }

    private void createButtonPanel() {

        buttonPanelWrapper = new PaddedJPanel(PANEL_PADDING);
        buttonPanelWrapper.setLayout(new GridBagLayout());
        buttonPanel = new JPanel(new GridLayout(2, 1));

        newGameButton = new JButton("New Game");

        newGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.reset();
            }
        });

        spinButton = new JButton("Spin");

        spinButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.spin();
            }
        });

        buttonPanel.add(newGameButton);
        buttonPanel.add(spinButton);

        buttonPanelWrapper.add(buttonPanel);
    }



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
