
package com.darrylsite.ihm.reader;

import com.darrylsite.entite.Message;
import com.darrylsite.ihm.reader.imp.TrashMenuImp;
import com.darrylsite.ihm.tools.Utils;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JTable;

/**
 * @author nabster
 */
public class TrashMenu  extends JPopupMenu
{
    private List<TrashMessageListener> listener;
    private JMenuItem restoreItem, deleteItem, deleteAllItem;
    JTable table;
    List<Message> messages;


    public TrashMenu()
    {
        init();
        listener = new ArrayList<TrashMessageListener>();
        initListener();
    }
    
    private void initListener()
    {
     this.listener.add(new TrashMenuImp());
    }

    private void init()
    {
        setBackground(new Color(0x85, 0x95, 0xD3));
        Utils util = new Utils();
        restoreItem = new JMenuItem("Restaurer",new ImageIcon(util.loadImage("refresh.png")));
        restoreItem.setBackground(new Color(0x85, 0x95, 0xD3));
        restoreItem.setForeground(new Color(0x00, 0x00, 0xC1));
        restoreItem.addActionListener(afficherMenuListener);
        add(restoreItem);

        deleteItem = new JMenuItem("Supprimer", new ImageIcon(util.loadImage("cross.png")));
        deleteItem.addActionListener(afficherMenuListener);
        deleteItem.setBackground(new Color(0x85, 0x95, 0xD3));
        deleteItem.setForeground(new Color(0x00, 0x00, 0xC1));
        add(deleteItem);

        deleteAllItem = new JMenuItem("Vider la corbeille", new ImageIcon(util.loadImage("smstrash.png")));
        deleteAllItem.addActionListener(afficherMenuListener);
        deleteAllItem.setBackground(new Color(0x85, 0x95, 0xD3));
        deleteAllItem.setForeground(new Color(0x00, 0x00, 0xC1));
        add(deleteAllItem);
    }

   public void addTrashMessageListener(TrashMessageListener l)
   {
       listener.add(l);
   }

   ActionListener afficherMenuListener = new ActionListener()
   {
        @Override
        public void actionPerformed(ActionEvent event)
        {
             int[] rows = table.getSelectedRows();
             if(rows ==null || rows.length<1)
                return;

            if(event.getSource()== restoreItem)
            {
                for(TrashMessageListener l : listener)
                     for(int i : rows)
                       l.resume(messages.get(i).getId());

            }
             
            else if(event.getSource()== deleteAllItem )
            {
               for(TrashMessageListener l : listener)
                    l.emptyFolder();
            }
            else if(event.getSource()== deleteItem)
            {
               for(TrashMessageListener l : listener)
                    for(int i : rows)
                      l.delete(messages.get(i).getId());
            }
        }
    };

    public void setTable(JTable table)
    {
        this.table = table;
    }

    public List<Message> getMessages()
    {
        return messages;
    }

    public void setMessages(List<Message> messages)
    {
        this.messages = messages;
    }
    
    
}
