package pl.wiktorszczeszek.ui.views;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class FileSelectionPanel extends JPanel {
    private JTextField selectedFilesField;
    private JButton browseButton;

    public FileSelectionPanel() {
        setLayout(new FlowLayout(FlowLayout.LEFT));

        selectedFilesField = new JTextField(30);
        selectedFilesField.setEditable(false);

        browseButton = new JButton("Przeglądaj");

        add(new JLabel("Pliki do przeszukania:"));
        add(selectedFilesField);
        add(browseButton);
    }

    public void setSelectedFilesText(String text) {
        selectedFilesField.setText(text);
    }

    public void addBrowseButtonListener(ActionListener listener) {
        browseButton.addActionListener(listener);
    }
}
