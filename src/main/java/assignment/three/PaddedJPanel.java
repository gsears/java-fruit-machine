package assignment.three;

import javax.swing.*;

class PaddedJPanel extends JPanel {

    public PaddedJPanel() {

    }

    public PaddedJPanel(int padding) {
        setPadding(padding);
    }

    public void setPadding(int padding) {
        setBorder(BorderFactory.createEmptyBorder(padding, padding, padding, padding));
    }
}
