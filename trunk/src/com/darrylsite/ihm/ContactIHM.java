/*
 * ContactIHM.java
 *
 * Created on __DATE__, __TIME__
 */
package com.darrylsite.ihm;

import java.awt.EventQueue;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.swing.ListModel;

import com.darrylsite.core.MyEntityManager;
import com.darrylsite.entite.Contact;
import com.darrylsite.entite.Groupe;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JFrame;
import javax.swing.event.ListDataListener;

/**
 *
 * @author nabster
 * @web http://www.darrylsite.com
 */
public class ContactIHM extends javax.swing.JPanel
{

    /**
     *
     */
    private static final long serialVersionUID = -4796213202875932197L;
    private long[] ids;
    private long[] contIds;
    long modifiedId = 0;
    int action = 0;
    private List<String> contacts = new ArrayList<String>();

    /** Creates new form ContactIHM */
    public ContactIHM()
    {
        initComponents();
        processSwingEvent();
    }

    private void loadGroupList()
    {
        EntityManager em = MyEntityManager.emf.createEntityManager();
        Query query = em.createQuery("select g from Groupe g order by g.id");
        List<com.darrylsite.entite.Groupe> liste = query.getResultList();
        
        if (liste.isEmpty())
        {
            return;
        }
        ids = new long[liste.size()];
        cbGroupe.removeAllItems();
        for (int i = 0; i < liste.size(); i++)
        {
            cbGroupe.addItem(liste.get(i).getName());
            ids[i] = liste.get(i).getId();
        }
        loadContactByGroup(ids[cbGroupe.getSelectedIndex()]);
    }

    private void updateContact()
    {
        EntityManager em = MyEntityManager.emf.createEntityManager();
        com.darrylsite.entite.Contact cont = new com.darrylsite.entite.Contact();
        cont.setNom(txtNom.getText());
        cont.setPrenom(txtPrenom.getText());
        cont.setId(modifiedId);
        cont.setNumero(txtPhone.getText());
        cont.setGroupe(em.find(Groupe.class, ids[cbGroupe.getSelectedIndex()]));
        em.getTransaction().begin();
        em.merge(cont);
        em.getTransaction().commit();
        loadContactByGroup(ids[cbGroupe.getSelectedIndex()]);
    }

    private void editContact()
    {
        int i = lstContact.getSelectedIndex();
        if (i < 0)
        {
            return;
        }
        EntityManager em = MyEntityManager.emf.createEntityManager();
        Query query = em.createQuery("select c from Contact c where c.id=" + contIds[i] + "");
        com.darrylsite.entite.Contact cont = (com.darrylsite.entite.Contact) query.getSingleResult();
        if (cont == null)
        {
            return;
        }
        modifiedId = cont.getId();

        txtNom.setText(cont.getNom());
        txtPrenom.setText(cont.getPrenom());
        txtPhone.setText(cont.getNumero());
        btCreer.setText("Modifier");
        action = 1;
    }

    private void loadContactByGroup(long grp)
    {
        EntityManager em = MyEntityManager.emf.createEntityManager();
        Query query = em.createQuery("select c from Contact c  where "
                + " c.groupe.id=" + grp + " order by c.nom");
        List<com.darrylsite.entite.Contact> liste = query.getResultList();

        contacts.clear();
        contIds = new long[liste.size()];
        for (int i = 0; i < liste.size(); i++)
        {
            contIds[i] = liste.get(i).getId();
            contacts.add(liste.get(i).getNom() + " - "
                    + liste.get(i).getNumero());
        }

        lstContact.setModel(new ListModel()
        {

            @Override
            public void addListDataListener(ListDataListener l)
            {
                // TODO Auto-generated method stub
            }

            @Override
            public Object getElementAt(int index)
            {
                return contacts.get(index);
            }

            @Override
            public int getSize()
            {
                return contacts.size();
            }

            @Override
            public void removeListDataListener(ListDataListener l)
            {
                // TODO Auto-generated method stub
            }
        });

    }

    private boolean verifForm()
    {
        if ((txtNom.getText().equals("") && txtPrenom.getText().equals("")) || txtPhone.getText().equals(""))
        {
            return false;
        }
        Pattern p = Pattern.compile("\\+?[0-9]+");
        Matcher m = p.matcher(txtPhone.getText());
        return m.matches();
    }

    private void createContact()
    {
        if (!verifForm())
        {
            return;
        }
        int ind = cbGroupe.getSelectedIndex();
        if (ind < 0)
        {
            return;
        }
        Contact cont = new Contact();
        cont.setNom(txtNom.getText());
        cont.setPrenom(txtPrenom.getText());
        cont.setNumero(txtPhone.getText());
        EntityManager em = MyEntityManager.emf.createEntityManager();
        Groupe grp = em.find(Groupe.class, ids[ind]);
        cont.setGroupe(grp);
        em.getTransaction().begin();
        em.persist(cont);
        em.getTransaction().commit();
        if (cbGroupe.getSelectedIndex() != -1)
        {
            loadContactByGroup(ids[cbGroupe.getSelectedIndex()]);
        }

    }

    private void clearForm()
    {
        txtNom.setText("");
        txtPhone.setText("");
        txtPrenom.setText("");
        btCreer.setText("creer");
        action = 0;
    }

    private void processSwingEvent()
    {
        EventQueue.invokeLater(new Runnable()
        {

            public void run()
            {
                loadGroupList();
            }
        });
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txtNom = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        txtPrenom = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txtPhone = new javax.swing.JTextField();
        btCreer = new javax.swing.JButton();
        btEffacer = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        lstContact = new javax.swing.JList();
        cbGroupe = new javax.swing.JComboBox();

        setLayout(new java.awt.BorderLayout());

        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setText("Nom : ");
        jPanel2.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 20, -1, -1));
        jPanel2.add(txtNom, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 10, 180, -1));

        jLabel2.setText("Prénom : ");
        jPanel2.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 60, -1, -1));
        jPanel2.add(txtPrenom, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 60, 178, -1));

        jLabel3.setText("Numero : ");
        jPanel2.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 120, -1, -1));
        jPanel2.add(txtPhone, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 120, 176, -1));

        btCreer.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/add.png"))); // NOI18N
        btCreer.setText("Ajouter");
        btCreer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btCreerActionPerformed(evt);
            }
        });
        jPanel2.add(btCreer, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 200, 93, -1));

        btEffacer.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/cross.png"))); // NOI18N
        btEffacer.setText("Effacer");
        btEffacer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btEffacerActionPerformed(evt);
            }
        });
        jPanel2.add(btEffacer, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 200, 103, -1));

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 270, 260));

        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel5.setText("Groupe");
        jPanel3.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 30, -1, -1));

        lstContact.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        lstContact.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                updateContactClicked(evt);
            }
        });
        lstContact.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                deleteContactTyped(evt);
            }
        });
        jScrollPane2.setViewportView(lstContact);

        jPanel3.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 70, 260, 290));

        cbGroupe.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbGroupeChanged(evt);
            }
        });
        jPanel3.add(cbGroupe, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 30, 181, -1));

        jPanel1.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 10, 330, 420));

        add(jPanel1, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    private void updateContactClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_updateContactClicked
        if (evt.getClickCount() > 1)
        {
            modifiedId = lstContact.getSelectedIndex();
            editContact();
        }
    }//GEN-LAST:event_updateContactClicked

    private void deleteContactTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_deleteContactTyped
        if (evt.getKeyChar() != KeyEvent.VK_DELETE)
        {
            return;
        }
        int[] id = lstContact.getSelectedIndices();
        if (id.length < 1)
        {
            return;
        }

        EntityManager em = MyEntityManager.emf.createEntityManager();
        for (int c : id)
        {
            com.darrylsite.entite.Contact cont = new com.darrylsite.entite.Contact();
            cont.setId((long) contIds[c]);
            em.getTransaction().begin();
            em.remove(em.merge(cont));
            em.getTransaction().commit();
        }
        loadContactByGroup(ids[cbGroupe.getSelectedIndex()]);
    }//GEN-LAST:event_deleteContactTyped

    private void cbGroupeChanged(java.awt.event.ItemEvent evt)
    {
        if (cbGroupe.getSelectedIndex() != -1)
        {
            loadContactByGroup(ids[cbGroupe.getSelectedIndex()]);
        }
    }

    private void btEffacerActionPerformed(java.awt.event.ActionEvent evt)
    {
        clearForm();
    }

    private void btCreerActionPerformed(java.awt.event.ActionEvent evt)
    {
        if (action == 0)
        {
            createContact();
        } else
        {
            updateContact();
        }
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btCreer;
    private javax.swing.JButton btEffacer;
    private javax.swing.JComboBox cbGroupe;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JList lstContact;
    private javax.swing.JTextField txtNom;
    private javax.swing.JTextField txtPhone;
    private javax.swing.JTextField txtPrenom;
    // End of variables declaration//GEN-END:variables

}
