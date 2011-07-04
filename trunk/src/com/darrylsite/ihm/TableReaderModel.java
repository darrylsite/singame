
package com.darrylsite.ihm;

import com.darrylsite.entite.MsgStatus.Status;


/**
 *
 * @author nabster
 */
public class TableReaderModel extends javax.swing.table.DefaultTableModel
{
    Object[][] data;
    String[] header = new String[] { "Numero", "Message", "Date", "Status" };
    Class[] types;

    public TableReaderModel()
    {
        super(new Object[][] {  },
            new String[] { "Numero", "Message", "Date", "Status" });
        defineHeader();
    }

    public TableReaderModel(Object[][] data)
    {
        defineHeader();
        this.data = data;
    }

    public void setData(Object[][] data)
    {
        this.data = data;
        super.setDataVector(data, header);
        this.fireTableDataChanged();
    }

    private void defineHeader()
    {
        types = new Class[]
            {
                java.lang.String.class, 
                java.lang.String.class,
                java.util.Date.class,
                Status.class
            };
    }

    @Override
    public Class getColumnClass(int columnIndex)
    {
        return types[columnIndex];
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex)
    {
        return false;
    }

    @Override
    public int getColumnCount() {
        return header.length;
    }


}
