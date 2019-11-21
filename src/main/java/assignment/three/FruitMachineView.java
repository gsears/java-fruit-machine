package assignment.three;

import javax.swing.*;
import java.awt.event.*;

public class FruitMachineView extends JFrame {

    FruitMachineController controller;

    JButton newGameButton;
    JButton spinButton;

    JLabel balanceDisplay;
    JLabel messageDisplay;
    JLabel victoryDisplay;

    JPanel dirtyPanel;

    SpinnerSetView spinners;

    public FruitMachineView(FruitMachineController controller) {
        this.controller = controller;
        setSize(600, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        initComponents();

        this.setVisible(true);
    }

    private void initComponents() {
        dirtyPanel = new JPanel();

        balanceDisplay = new JLabel();
        messageDisplay = new JLabel();
        victoryDisplay = new JLabel();

        spinners = new SpinnerSetView(controller.getSpinnerCount());

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

        dirtyPanel.add(balanceDisplay);
        dirtyPanel.add(messageDisplay);
        dirtyPanel.add(victoryDisplay);
        dirtyPanel.add(newGameButton);
        dirtyPanel.add(spinButton);
        dirtyPanel.add(spinners);

        add(dirtyPanel);
    }

    public void setSpinners(Card[] cards) {
        spinners.setCards(cards);
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
