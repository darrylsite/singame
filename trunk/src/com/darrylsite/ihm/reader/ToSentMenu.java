
package com.darrylsite.ihm.reader;

import com.darrylsite.entite.Message;
import com.darrylsite.ihm.reader.imp.ToSendMenuImp;
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
public class ToSentMenu  extends JPopupMenu
{
    private List<ToSendMessageListener> listener;
    private JMenuItem SentNowitem, deleteItem, modifyItem;
    JTable table;
    List<Message> messages;


    public ToSentMenu()
    {
        init();
        listener = new ArrayList<ToSendMessageListener>();
        initListener();
    }
     private void initListener()
    {
     this.listener.add(new ToSendMenuImp());
    }

    private void init()
    {
        Utils util = new Utils();
        setBackground(new Color(0x85, 0x95, 0xD3));
        
        SentNowitem = new JMenuItem("Envoyer maintenant");
        SentNowitem.setBackground(new Color(0x85, 0x95, 0xD3));
        SentNowitem.setForeground(new Color(0x00, 0x00, 0xC1));
        SentNowitem.addActionListener(afficherMenuListener);
        add(SentNowitem);

        deleteItem = new JMenuItem("Supprimer", new ImageIcon(util.loadImage("cross.png")));
        deleteItem.addActionListener(afficherMenuListener);
        deleteItem.setBackground(new Color(0x85, 0x95, 0xD3));
        deleteItem.setForeground(new Color(0x00, 0x00, 0xC1));
        add(deleteItem);

        modifyItem = new JMenuItem("Modifier", new ImageIcon(util.loadImage("edit.png")));
        modifyItem.addActionListener(afficherMenuListener);
        modifyItem.setBackground(new Color(0x85, 0x95, 0xD3));
        modifyItem.setForeground(new Color(0x00, 0x00, 0xC1));
        add(modifyItem);
    }

   public void addSentMessagerListener(ToSendMessageListener l)
   {
       listener.add(l);
   }

   ActionListener afficherMenuListener = new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent event)
        {
             int[] rows = table.getSelectedRows();
            if(rows ==null || rows.length<1)
                return;

            if(event.getSource()== SentNowitem)
            {
                for(ToSendMessageListener l : listener)
                    for(int i : rows)
                     l.sendNow(messages.get(i).getId());
            }
            else if(event.getSource()== modifyItem)
            {
               for(ToSendMessageListener l : listener)
                   for(int i : rows)
                    l.modifyMessage(messages.get(i).getId());
            }
            else if(event.getSource()== deleteItem)
            {
               for(ToSendMessageListener l : listener)
                   for(int i : rows)
                    l.cancelSending(messages.get(i).getId());
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
