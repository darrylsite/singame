
package com.darrylsite.ihm.reader;

import com.darrylsite.entite.Message;
import com.darrylsite.ihm.reader.imp.SentMessageImp;
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
 *
 * @author nabster
 */
public class sentMenu  extends JPopupMenu
{
    private List<SentMessagerListener> listener;
    private JMenuItem reSentitem, deleteItem, forwardItem;
    JTable table;
    List<Message> messages;


    public sentMenu()
    {
        listener = new ArrayList<SentMessagerListener>();
        init();     
        initListener();
    }
    
    private void initListener()
    {
     this.listener.add(new SentMessageImp());
    }

    private void init()
    {
        Utils util = new Utils();
        setBackground(new Color(0x85, 0x95, 0xD3));
        reSentitem = new JMenuItem("(Re)Envoyer");
        reSentitem.setBackground(new Color(0x85, 0x95, 0xD3));
        reSentitem.setForeground(new Color(0x00, 0x00, 0xC1));
        reSentitem.addActionListener(afficherMenuListener);
        add(reSentitem);

        deleteItem = new JMenuItem("Supprimer", new ImageIcon(util.loadImage("cross.png")));
        deleteItem.addActionListener(afficherMenuListener);
        deleteItem.setBackground(new Color(0x85, 0x95, 0xD3));
        deleteItem.setForeground(new Color(0x00, 0x00, 0xC1));
        add(deleteItem);

        forwardItem = new JMenuItem("Faire Suivre");
        forwardItem.addActionListener(afficherMenuListener);
        forwardItem.setBackground(new Color(0x85, 0x95, 0xD3));
        forwardItem.setForeground(new Color(0x00, 0x00, 0xC1));
        add(forwardItem);
    }

   public void addSentMessagerListener(SentMessagerListener l)
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

            if(event.getSource()== reSentitem)
            {
                for(SentMessagerListener l : listener)
                    for(int i : rows)
                      l.reSend(messages.get(i).getId());

            }
            else if(event.getSource()== forwardItem)
            {
               for(SentMessagerListener l : listener)
                   for(int i : rows)
                     l.forward(messages.get(i).getId());
            }
            else if(event.getSource()== deleteItem)
            {
               for(SentMessagerListener l : listener)
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
