package pl.wiktorszczeszek.ui.models;

import pl.wiktorszczeszek.core.domain.SearchResult;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

public class SearchResultTableModel extends AbstractTableModel {
    private final String[] columnNames = {"Plik", "Liczba wystąpień"};
    private List<SearchResult> results = new ArrayList<>();

    public void setResults(List<SearchResult> results) {
        this.results = results;
        fireTableDataChanged();
    }

    @Override
    public int getRowCount() {
        return results != null ? results.size() : 0;
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public String getColumnName(int columnIndex) {
        if (columnIndex >= 0 && columnIndex < columnNames.length) {
            return columnNames[columnIndex];
        } else {
            return super.getColumnName(columnIndex);
        }
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        if (results == null || rowIndex < 0 || rowIndex >= results.size()) {
            return null;
        }

        SearchResult result = results.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return result.getFile().path();
            case 1:
                return result.getOccurrenceCount();
            default:
                return null;
        }
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        switch (columnIndex) {
            case 0:
                return String.class;
            case 1:
                return Integer.class;
            default:
                return Object.class;
        }
    }
}