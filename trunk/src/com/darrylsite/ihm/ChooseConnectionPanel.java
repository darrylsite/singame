package com.darrylsite.ihm;

import com.darrylsite.core.MyEntityManager;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;

import com.darrylsite.entite.ModemInfo;
import java.awt.event.KeyAdapter;
import java.util.ArrayList;

public class ChooseConnectionPanel extends JPanel
{
	private static final long serialVersionUID = 1L;

	private JTable tblConnex;
	private List<ModemInfo> myData;
	List<ChangeModemListener> myListener;

	/**
	 * Create the panel.
	 */
	public ChooseConnectionPanel()
	{
		LoadTable();
                myListener = new ArrayList<ChangeModemListener>();
	}

        private void loadData()
        {
         EntityManager em = com.darrylsite.core.MyEntityManager.emf.createEntityManager();
         Query query = em.createQuery("select m from ModemInfo m order by m.id");
         myData = query.getResultList();
        }

	private void LoadTable()
	{
		loadData();
		TableModel dataModel = new AbstractTableModel()
		 {
			 String[] headers = {"Nom", "Port", "Frequence"};
	          public int getColumnCount()
	          {
	        	  return 3;
	          }
	          public int getRowCount()
	          {
	        	  return myData.size();
	          }
	          public Object getValueAt(int row, int col)
	          {
	        	  ModemInfo info = myData.get(row);
	        	  switch(col)
	        	  {
	        	   case 0 : return info.getName();
	        	   case 1 : return info.getPort();
	        	   case 2 : return info.getFrequency();
	        	  }
	        	  return "";
	          }
			@Override
			public String getColumnName(int column)
			{
				return headers[column];
			}
			@Override
			public Class<?> getColumnClass(int columnIndex)
			{
				switch(columnIndex)
	        	  {
	        	   case 0 : return String.class;
	        	   case 1 : return String.class;
	        	   case 2 : return String.class;
	        	  }
				return String.class;
			}


	      };

	      tblConnex = new JTable(dataModel);
	      JScrollPane scrollpane = new JScrollPane(tblConnex);
          add(scrollpane);
          tblConnex.addMouseListener(new MouseAdapter()
          {

			@Override
			public void mouseClicked(MouseEvent e)
			{
			  if(e.getClickCount()<2)
				  return;
              int row = tblConnex.getSelectedRow();
              if(row==-1)
            	  return;
              String name = (String) tblConnex.getModel().getValueAt(row, 0);
              String port = (String) tblConnex.getModel().getValueAt(row, 1);
              int frequency = (Integer) tblConnex.getModel().getValueAt(row, 2);
              fireChangeModemListener(name, port, frequency);
			}

		  });
          tblConnex.addKeyListener(new KeyAdapter()
          {

            @Override
            public void keyTyped(KeyEvent e)
            {
              if(e.getKeyChar()== KeyEvent.VK_DELETE)
              {
               int r= tblConnex.getSelectedRow();
               if(r==-1)
                  return;
               EntityManager em = MyEntityManager.emf.createEntityManager();
               em.getTransaction().begin();
               ModemInfo inf = em.merge((myData.get(r)));
               em.remove(inf);
               em.getTransaction().commit();
               loadData();
               tblConnex.tableChanged(null);
              }
            }

          });
	}

	/**
	 * @return
	 */
	public void addChangeModemListener(ChangeModemListener lsn)
	{
	  myListener.add(lsn);
	}


	/**
	 * @param
	 */
	public void removeMyListener(ChangeModemListener lsn)
	{
		this.myListener.remove(lsn);
	}

	private void fireChangeModemListener(String name, String port, int frequence)
	{
		for(ChangeModemListener c : myListener)
			c.newConfig(name, port, frequence);
	}

}


