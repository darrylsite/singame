
package com.darrylsite.ihm.reader;

import com.darrylsite.entite.Message;
import com.darrylsite.ihm.reader.imp.ReceiptMenuImp;
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
public class ReceiptMenu extends JPopupMenu
{
    private List<ReceiptMessageListener> listener;
    private JMenuItem answerItem, deleteItem, forwardItem;
    JTable table;
    List<Message> messages;

    public ReceiptMenu()
    {
        listener = new ArrayList<ReceiptMessageListener>();
        init();
    }

    private void init()
    {
        setBackground(new Color(0x85, 0x95, 0xD3));
        Utils util = new Utils();

        answerItem = new JMenuItem("Repondre");
        answerItem.setBackground(new Color(0x85, 0x95, 0xD3));
        answerItem.setForeground(new Color(0x00, 0x00, 0xC1));
        answerItem.addActionListener(afficherMenuListener);
        add(answerItem);

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
        
        initListener();
    }
    
    private void initListener()
    {
     this.addReceiptListener(new ReceiptMenuImp());
    }

   public void addReceiptListener(ReceiptMessageListener l)
   {
       listener.add(l);
   }

    public JTable getTable()
    {
        return table;
    }

    public void setTable(JTable table)
    {
        this.table = table;
    }

   ActionListener afficherMenuListener = new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent event)
        {
            int[] rows = table.getSelectedRows();
            if(rows ==null || rows.length<1)
                return;

            if(event.getSource()== answerItem)
            {
                for(ReceiptMessageListener l : listener)
                {
                    for(int i : rows)
                      l.answer(messages.get(i).getId());
                }
            }
            else if(event.getSource()== forwardItem)
            {
               for(ReceiptMessageListener l : listener)
                    for(int i : rows)
                    l.forward(messages.get(i).getId());
            }
            else if(event.getSource()== deleteItem)
            {
               for(ReceiptMessageListener l : listener)
                    for(int i : rows)
                      l.delete(messages.get(i).getId());
            }
        }
    };

    public List<Message> getMessages()
    {
        return messages;
    }

    public void setMessages(List<Message> messages)
    {
        this.messages = messages;
    }

   
   
    public ActionListener getAfficherMenuListener() {
        return afficherMenuListener;
    }

    public void setAfficherMenuListener(ActionListener afficherMenuListener) {
        this.afficherMenuListener = afficherMenuListener;
    }

    public JMenuItem getAnswerItem()
    {
        return answerItem;
    }

    public void setAnswerItem(JMenuItem answerItem)
    {
        this.answerItem = answerItem;
    }
}
