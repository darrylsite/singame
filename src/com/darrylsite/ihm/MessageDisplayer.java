/*
 * MessageDisplayer.java
 *
 * Created on 16 nov. 2010, 14:46:44
 */

package com.darrylsite.ihm;

import com.darrylsite.ihm.tools.Utils;
import java.awt.Component;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;

/**
 *
 * @author nabster
 */
@SuppressWarnings("serial")
public class MessageDisplayer extends javax.swing.JPanel implements MouseListener
{
    List<MouseListener> mouseListener = new ArrayList<MouseListener>();
    int[] selectedRow;
    
     public MessageDisplayer(Object[][] data)
     {
	    TableReaderModel tblReader = new TableReaderModel(data);
	    this.jTable1.setModel(tblReader);
            initComponents();
            
            this.jTable1.addMouseListener(this);
     }

         /** Creates new form MessageDisplayer */
    public MessageDisplayer()
    {
        initComponents();
        this.jTable1.addMouseListener(this);
    }

    public int[] getSelectedRow()
    {
        return selectedRow;
    }

    public JTable getTable()
    {
        return this.jTable1;
    }

    @Override
    public void addMouseListener(MouseListener l)
    {
        mouseListener.add(l);
    }

    public void refreshData(Object[][] data)
    {
     TableReaderModel tblReader = (TableReaderModel) this.jTable1.getModel();
     tblReader.setData(data);
     TableColumnModel colModel = jTable1.getColumnModel();
     colModel.getColumn(3).setCellRenderer(new ImageRenderer());
     colModel.getColumn(0).setCellRenderer(new LabelRenderer("phone.png"));  
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();

        setLayout(new java.awt.BorderLayout());

        jTable1.setAutoCreateRowSorter(true);
        jTable1.setModel(new TableReaderModel());
        jTable1.setDoubleBuffered(true);
        jTable1.setRowSelectionAllowed(true);
        jTable1.setRowSorter(null);
        jTable1.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane1.setViewportView(jTable1);
        jTable1.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_INTERVAL_SELECTION);

        add(jScrollPane1, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    // End of variables declaration//GEN-END:variables


    class ImageRenderer extends JLabel implements TableCellRenderer
    {
        Image img;
        private final String _read = "unread";
        private final String _unread = "read";
        private final String _sent = "sent";
        private final String _unsent = "unsent";
        private final String _deleted = "deleted";

        public ImageRenderer() 
        {
            super();
        }

        @Override
        public Component getTableCellRendererComponent(JTable table,
            Object value, boolean isSelected, boolean hasFocus, int row,
            int column)
        {   
           Utils util = new Utils();
           String st = value.toString();
           if(st.equals(_read))
           {
             img = util.loadImage("smsread.png");  
           }
           else if(st.equals(_unread))
           {
             img = util.loadImage("smsunread.png");  
           }
           else if(st.equals(_sent))
           {
             img = util.loadImage("smssent.png");  
           }
           else if(st.equals(_unsent))
           {
              img = util.loadImage("tosend.png"); 
           }
           else
           {
               img = util.loadImage("smstrash.png");
           }

          setIcon(new ImageIcon(img));
          setHorizontalAlignment(SwingConstants.CENTER);
            
            return this;
        }
    }
    
     class LabelRenderer extends JLabel implements TableCellRenderer
    {
        Image img;

        public LabelRenderer(String imgStr) 
        {
            super();
          if(imgStr==null)
              return;
          Utils util = new Utils();
          img = util.loadImage(imgStr);
        }

        @Override
        public Component getTableCellRendererComponent(JTable table,
            Object value, boolean isSelected, boolean hasFocus, int row,
            int column)
        {   
          setText((String)value);
          setIcon(new ImageIcon(img));
          setHorizontalAlignment(SwingConstants.CENTER);
            
            return this;
        }
    }
  
	@Override
	public void mouseClicked(MouseEvent arg0) 
	{
            selectedRow = jTable1.getSelectedRows();
            for(MouseListener m : mouseListener)
                m.mouseClicked(arg0);
	}

	@Override
	public void mouseEntered(MouseEvent arg0) 
	{
	}

	@Override
	public void mouseExited(MouseEvent arg0) 
	{	
	}

	@Override
	public void mousePressed(MouseEvent arg0) 
	{		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) 
	{	
	} 
}
