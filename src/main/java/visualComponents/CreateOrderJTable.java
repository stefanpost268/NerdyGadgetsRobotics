package visualComponents;

import javax.accessibility.Accessible;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.DefaultTableModel;

public class CreateOrderJTable extends javax.swing.JTable implements TableModelListener, Scrollable,
        TableColumnModelListener, ListSelectionListener, CellEditorListener,
        Accessible, RowSorterListener {
    public CreateOrderJTable(DefaultTableModel tableModel) {
        super(tableModel);
        tableModel.addTableModelListener(this);
    }

    @Override
    public boolean isCellEditable(int row, int column) {
        return column == 2;
    }
}
