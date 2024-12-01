package pl.wiktorszczeszek.ui.models;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

public class SelectedFilesTableModel extends AbstractTableModel {
    private final String[] columnNames = {"Plik"};
    private List<String> files = new ArrayList<>();

    public void setRows(List<String> files) {
        this.files = files;
        fireTableDataChanged();
    }

    @Override
    public int getRowCount() {
        return files.size();
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
        if (rowIndex < 0 || rowIndex >= files.size()) {
            return null;
        }

        String file = files.get(rowIndex);
        if (columnIndex == 0) return file;
        else return  null;
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        if (columnIndex == 0) return String.class;
        else return Object.class;
    }

    public void removeRows(int[] indexes) {
        for (int i : indexes) {
            this.files.remove(i);
        }
        fireTableDataChanged();
    }
}
