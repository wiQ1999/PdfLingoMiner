package pl.wiktorszczeszek.ui.views;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
    private SearchPanel searchPanel;
    private ResultsPanel resultsPanel;
    private FileSelectionPanel fileSelectionPanel;

    public MainFrame() {
        setTitle("Aplikacja Wyszukiwania PDF");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        searchPanel = new SearchPanel();
        resultsPanel = new ResultsPanel();
        fileSelectionPanel = new FileSelectionPanel();

        add(fileSelectionPanel, BorderLayout.NORTH);
        add(searchPanel, BorderLayout.CENTER);
        add(resultsPanel, BorderLayout.SOUTH);

        pack();
        setLocationRelativeTo(null);
    }

    public SearchPanel getSearchPanel() {
        return searchPanel;
    }

    public ResultsPanel getResultsPanel() {
        return resultsPanel;
    }

    public FileSelectionPanel getFileSelectionPanel() {
        return fileSelectionPanel;
    }
}
