package pl.wiktorszczeszek.ui.views;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class SearchPanel extends JPanel {
    private JTextField searchField;
    private JButton searchButton;

    public SearchPanel() {
        setLayout(new FlowLayout());

        searchField = new JTextField(20);
        searchButton = new JButton("Szukaj");

        add(new JLabel("Fraza wyszukiwania:"));
        add(searchField);
        add(searchButton);
    }

    public String getSearchPhrase() {
        return searchField.getText();
    }

    public void addSearchButtonListener(ActionListener listener) {
        searchButton.addActionListener(listener);
    }
}
