package pl.wiktorszczeszek.ui.views;

import pl.wiktorszczeszek.core.domain.results.TextContentSearch;
import pl.wiktorszczeszek.ui.buttons.ActionCancelButton;
import pl.wiktorszczeszek.ui.models.SearchResultTableModel;
import pl.wiktorszczeszek.ui.models.SelectedFilesTableModel;
import pl.wiktorszczeszek.ui.tables.ResizableTable;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.text.MessageFormat;

public class MainFrame extends JFrame {
    private final Dimension largeButtonDimension = new Dimension(120, 30);
    private final Dimension mediumButtonDimension = new Dimension(80, 30);
    private final Font title = new Font("Arial", Font.BOLD, 20);
    private final Font info = new Font("Arial", Font.ITALIC, 14);
    private ResizableTable filesTable, resultsTable;
    private JButton browseButton, removeButton;
    private ActionCancelButton searchButton;
    private JTextField phraseField;
    private SelectedFilesTableModel filesModel;
    private SearchResultTableModel resultsModel;
    private JLabel searchProcessLabel;
    private int searchCapacity, searchedCount;

    public MainFrame() {
        setTitle("PDF Lingo Miner");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.BOTH;

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.4;
        gbc.weighty = 0.0;
        JLabel browseLabel = new JLabel("Lista plików do wyszukiwania");
        browseLabel.setFont(title);
        add(browseLabel, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0.4;
        gbc.weighty = 0.0;
        JPanel searchPanel = new JPanel();
        searchPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
        browseButton = new JButton("Dodaj do listy");
        browseButton.setPreferredSize(largeButtonDimension);
        searchPanel.add(browseButton);
        removeButton = new JButton("Usuń z listy");
        removeButton.setPreferredSize(largeButtonDimension);
        searchPanel.add(removeButton);
        add(searchPanel, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 0.4;
        gbc.weighty = 1.0;
        filesModel = new SelectedFilesTableModel();
        filesTable = new ResizableTable(filesModel);
        filesTable.adjustColumnsWidthToHeaders();
        JScrollPane filesPane = new JScrollPane(filesTable, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        add(filesPane, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 0.6;
        gbc.weighty = 0.0;
        JPanel searchLabelPanel = new JPanel();
        searchLabelPanel.setLayout(new BorderLayout());
        JLabel searchLabel = new JLabel("Wskaż frazę wyszukiwania");
        searchLabel.setFont(title);
        searchLabelPanel.add(searchLabel, BorderLayout.CENTER);
        searchProcessLabel = new JLabel("0/0");
        searchProcessLabel.setFont(info);
        searchLabelPanel.add(searchProcessLabel, BorderLayout.EAST);
        add(searchLabelPanel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.weightx = 0.6;
        gbc.weighty = 0.0;
        JPanel resultPanel = new JPanel();
        resultPanel.setLayout(new BorderLayout(5, 5));
        phraseField = new JTextField();
        resultPanel.add(phraseField, BorderLayout.CENTER);
        searchButton = new ActionCancelButton("Szukaj");
        searchButton.setPreferredSize(mediumButtonDimension);
        resultPanel.add(searchButton, BorderLayout.EAST);
        add(resultPanel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.weightx = 0.6;
        gbc.weighty = 1.0;
        resultsModel = new SearchResultTableModel();
        resultsTable = new ResizableTable(resultsModel);
        resultsTable.adjustColumnsWidthToHeaders();
        JScrollPane resultsPane = new JScrollPane(resultsTable, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        add(resultsPane, gbc);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public void addBrowseButtonListener(ActionListener listener) {
        browseButton.addActionListener(listener);
    }

    public void addRemoveButtonListener(ActionListener listener) {
        removeButton.addActionListener(listener);
    }

    public void addPerformSearchButtonListener(ActionListener listener) {
        searchButton.addPerformActionListener(listener);
    }

    public void addCancelSearchButtonListener(ActionListener listener) {
        searchButton.addCancelActionListener(listener);
    }

    public int[] getSelectedFileIndexes() {
        return filesTable.getSelectedRows();
    }

    public void setFiles(java.util.List<String> files) {
        filesModel.setRows(files);
        filesTable.adjustColumnWidth(0);
    }

    public String getPhraseFieldText() {
        return phraseField.getText();
    }

    public void setResults(java.util.List<TextContentSearch> results) {
        resultsModel.setResults(results);
        resultsTable.adjustColumnWidth(1);
    }

    public void setSearchCapacity(int value) {
        searchCapacity = value;
        updateProcessLabel();
    }

    public void setSearchedCount(int value) {
        this.searchedCount = value;
        updateProcessLabel();
    }

    public void setSearchButtonToSearch() {
        searchButton.setSelected(false);
    }

    private void updateProcessLabel() {
        String info = MessageFormat.format("{0}/{1}", searchedCount, searchCapacity);
        searchProcessLabel.setText(info);
    }
}
