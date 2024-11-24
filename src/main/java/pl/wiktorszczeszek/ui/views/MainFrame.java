package pl.wiktorszczeszek.ui.views;

import pl.wiktorszczeszek.core.domain.SearchResult;
import pl.wiktorszczeszek.ui.models.SearchResultTableModel;
import pl.wiktorszczeszek.ui.models.SelectedFilesTableModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class MainFrame extends JFrame {
    private JPanel mainPanel;
    private JPanel filesPanel;
    private JPanel browsePanel;
    private JPanel centerGapPanel;
    private JPanel resultPanel;
    private JPanel searchPanel;

    private SelectedFilesTableModel filesModel;
    private JTable filesTable;

    private JButton browseButton;
    private JTextField phraseField;
    private JButton searchButton;

    private SearchResultTableModel resultsModel;
    private JTable resultsTable;

    public MainFrame() {
        setTitle("PDF Lingo Miner");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.X_AXIS));
        add(mainPanel);

        filesPanel = (JPanel) mainPanel.add(new JPanel());
        filesPanel.setLayout(new BoxLayout(filesPanel, BoxLayout.Y_AXIS));

        browsePanel = (JPanel) filesPanel.add(new JPanel());
        browsePanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        browsePanel.add(new JLabel("Wskaż lokalizację wyszukiwania"));
        browseButton = (JButton) browsePanel.add(new JButton("Przeglądaj"));

        filesModel = new SelectedFilesTableModel();
        filesTable = new JTable(filesModel);
        filesPanel.add(new JScrollPane(filesTable));

        centerGapPanel = (JPanel) mainPanel.add(new JPanel());
        centerGapPanel.setPreferredSize(new Dimension(50, 540));

        resultPanel = (JPanel) mainPanel.add(new JPanel());
        resultPanel.setLayout(new BoxLayout(resultPanel, BoxLayout.Y_AXIS));

        searchPanel = (JPanel) resultPanel.add(new JPanel());
        searchPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        searchPanel.add(new JLabel("Wskaż frazę wyszukiwania"));
        searchButton = (JButton) searchPanel.add(new JButton("Szukaj"));

        phraseField = (JTextField) resultPanel.add(new JTextField());
        resultsModel = new SearchResultTableModel();
        resultsTable = new JTable(resultsModel);
        resultPanel.add(new JScrollPane(resultsTable));

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public void addBrowseButtonListener(ActionListener listener) {
        browseButton.addActionListener(listener);
    }

    public void setFiles(java.util.List<String> files) {
        filesModel.setRows(files);
    }

    public void addSearchButtonListener(ActionListener listener) {
        searchButton.addActionListener(listener);
    }

    public String getPhraseFieldText() {
        return phraseField.getText();
    }

    public void setResults(java.util.List<SearchResult> results) {
        resultsModel.setResults(results);
    }
}
