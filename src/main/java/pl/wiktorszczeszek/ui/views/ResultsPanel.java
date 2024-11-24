package pl.wiktorszczeszek.ui.views;

import pl.wiktorszczeszek.core.domain.SearchResult;
import pl.wiktorszczeszek.ui.models.SearchResultTableModel;

import javax.swing.*;
import java.awt.*;

public class ResultsPanel extends JPanel {
    private JTable resultsTable;
    private SearchResultTableModel tableModel;

    public ResultsPanel() {
        setLayout(new BorderLayout());

        tableModel = new SearchResultTableModel();
        resultsTable = new JTable(tableModel);

        add(new JScrollPane(resultsTable), BorderLayout.CENTER);
    }

    public void updateResults(java.util.List<SearchResult> results) {
        tableModel.setResults(results);
    }
}