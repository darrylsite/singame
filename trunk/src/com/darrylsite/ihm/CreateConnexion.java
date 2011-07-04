/*
 * CreateConnexion.java
 *
 * Created on __DATE__, __TIME__
 */
package com.darrylsite.ihm;

import com.darrylsite.listener.SaveModemSettingListener;
import java.awt.EventQueue;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import com.darrylsite.core.HardControler;
import com.darrylsite.core.MyEntityManager;
import com.darrylsite.entite.ModemInfo;
import com.darrylsite.ihm.tools.Utils;

import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 *
 * @author  __USER__
 */
public class CreateConnexion extends javax.swing.JPanel implements
        SaveModemSettingListener
{

    /**
     *
     */
    private JLabel clickLabel = null;
    private static final long serialVersionUID = -3062312448067284512L;
    private String port;
    private String freq;
    private String nomCon;
    private boolean doSet = false;
    List<SaveModemSettingListener> connexListener;
    List<ChangeModemListener> changeListener;

    /** Creates new form CreateConnexion */
    public CreateConnexion()
    {
        initComponents();
        createAction();
        processSwingEvent();
        connexListener = new ArrayList<SaveModemSettingListener>();
        connexListener.add(this);
        initConnexdisplayer();
        loadConnexion();
    }

    private void initConnexdisplayer()
    {
        Utils util = new Utils();
        connex1.setVisible(false);
        connex1.setIcon(new ImageIcon(util.loadImage("connexion.png")));
        connex2.setVisible(false);
        connex2.setIcon(new ImageIcon(util.loadImage("connexion.png")));
        connex3.setVisible(false);
        connex3.setIcon(new ImageIcon(util.loadImage("connexion.png")));
        connex4.setVisible(false);
        connex4.setIcon(new ImageIcon(util.loadImage("connexion.png")));

    }

    private List<ModemInfo> loadData()
    {
        EntityManager em = com.darrylsite.core.MyEntityManager.emf.createEntityManager();
        Query query = em.createQuery("select m from ModemInfo m order by m.id");
        return query.getResultList();
    }

    private void loadConnexion()
    {
        JLabel[] liste = new JLabel[4];
        liste[0] = connex1;
        liste[1] = connex2;
        liste[2] = connex3;
        liste[3] = connex4;

        initConnexdisplayer();
        List<ModemInfo> modems = loadData();
        for (int i = 0; i < modems.size() && i < 4; i++)
        {
            liste[i].setText(modems.get(i).getName());
            liste[i].setVisible(true);
        }

    }

    public void addChangeModemListener(ChangeModemListener l)
    {
        if (changeListener == null)
        {
            changeListener = new ArrayList<ChangeModemListener>();
        }
        changeListener.add(l);
    }

    public void removeChangeModemListener(ChangeModemListener l)
    {
        changeListener.remove(l);
    }

    private void fireChangeModemListener(String name, String port, int frequence)
    {
        for (ChangeModemListener l : changeListener)
        {
            l.newConfig(name, port, frequence);
        }
    }

    /**
     * @return the connexListener
     */
    public void addSaveModemSettingListener(SaveModemSettingListener change)
    {
        this.connexListener.add(change);
    }

    /**
     * @param connexListener the connexListener to set
     */
    public void removeSaveModemSettingListener(SaveModemSettingListener change)
    {
        this.connexListener.remove(change);
    }

    private void fireChangeModemListener()
    {
        for (SaveModemSettingListener c : connexListener)
        {
            c.saveModemConfig(nomCon, port, Integer.parseInt(freq));
        }
    }

    public boolean isConnexionSet()
    {
        return doSet ? doSet : false;
    }

    public String getPort()
    {
        return port;
    }

    public void setPort(String port)
    {
        this.port = port;
    }

    public String getFreq()
    {

        return freq;
    }

    public void setFreq(String freq)
    {
        this.freq = freq;
    }

    public String getNomCon()
    {
        return nomCon;
    }

    public void setNomCon(String nomCon)
    {
        this.nomCon = nomCon;
    }

    private void processSwingEvent()
    {
        loadSerial();
    }

    private void loadSerial()
    {
        EventQueue.invokeLater(new Runnable()
        {

            @Override
            public void run()
            {

                List<String> ports = HardControler.listAllSerialPort();

                ports.add("iphone");
                cbPortList.removeAllItems();
                for (String s : ports)
                {
                    cbPortList.addItem(s);
                }

            }
        });

    }

    private void loadFrequency()
    {
        EventQueue.invokeLater(new Runnable()
        {

            @Override
            public void run()
            {
                cbFreq.removeAllItems();
                if (cbPortList.getItemCount() == 0)
                {
                    return;
                }
                List<String> liste = HardControler.listFrequency((String) cbPortList.getItemAt(0));
                liste.add("911200");
                for (String s : liste)
                {
                    cbFreq.addItem(s);
                }
            }
        });


    }

    private void createAction()
    {
        cbPortList.addItemListener(new ItemListener()
        {

            @Override
            public void itemStateChanged(ItemEvent e)
            {
                loadFrequency();
            }
        });

    }

    private void defineSaveAction()
    {
        if (txtCon.getText().equals(""))
        {
            return;
        }
        if (cbFreq.getItemCount() == 0)
        {
            return;
        }
        if (cbPortList.getItemCount() == 0)
        {
            return;
        }
        List<ModemInfo> modems = loadData();
        for (ModemInfo m : modems)
        {
            if (m.getName().equalsIgnoreCase(this.txtCon.getText()))
            {
                JOptionPane.showMessageDialog(this,
                        "Une connexion avec ce nom existe deja !");
                return;
            }
        }

        this.nomCon = this.txtCon.getText();
        this.port = (String) cbPortList.getSelectedItem();
        this.freq = (String) cbFreq.getSelectedItem();
        doSet = true;
        fireChangeModemListener();
        JOptionPane.showMessageDialog(this,
                "La configuration a ete bien enregistree");
        this.loadConnexion();
    }

    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        popup = new javax.swing.JPopupMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JSeparator();
        jMenuItem2 = new javax.swing.JMenuItem();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        cbPortList = new javax.swing.JComboBox();
        jLabel3 = new javax.swing.JLabel();
        cbFreq = new javax.swing.JComboBox();
        jLabel4 = new javax.swing.JLabel();
        txtCon = new javax.swing.JTextField();
        btSave = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        connex1 = new javax.swing.JLabel();
        connex2 = new javax.swing.JLabel();
        connex3 = new javax.swing.JLabel();
        connex4 = new javax.swing.JLabel();

        popup.setInvoker(this);

        jMenuItem1.setText("Supprimer");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteConnex(evt);
            }
        });
        popup.add(jMenuItem1);
        popup.add(jSeparator1);

        jMenuItem2.setText("Connecter");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                doConnex(evt);
            }
        });
        popup.add(jMenuItem2);

        popup.getAccessibleContext().setAccessibleParent(this);

        setLayout(new java.awt.GridBagLayout());

        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setText("Creer une nouvelle connexion");
        jPanel2.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 10, -1, -1));

        jLabel2.setText("Port :");
        jPanel2.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 90, -1, -1));

        jPanel2.add(cbPortList, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 90, 150, -1));

        jLabel3.setText("Frequence :");
        jPanel2.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 90, -1, -1));

        jPanel2.add(cbFreq, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 90, 130, -1));

        jLabel4.setText("Nom de la connexion : ");
        jPanel2.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 40, -1, -1));
        jPanel2.add(txtCon, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 40, 210, -1));

        btSave.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/save_as.png"))); // NOI18N
        btSave.setText("Enregistrer");
        btSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btSaveActionPerformed(evt);
            }
        });
        jPanel2.add(btSave, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 150, -1, -1));

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/search.png"))); // NOI18N
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel2.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 90, 40, -1));

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipady = 5;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.PAGE_START;
        add(jPanel2, gridBagConstraints);

        jPanel1.setBackground(new java.awt.Color(224, 224, 245));
        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel1.setMaximumSize(new java.awt.Dimension(554, 114));
        jPanel1.setOpaque(false);
        jPanel1.setLayout(new java.awt.GridBagLayout());

        connex1.setText("Connex 1");
        connex1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                connexClicked(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(100, 20, 0, 0);
        jPanel1.add(connex1, gridBagConstraints);

        connex2.setText("Connex 2");
        connex2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                connexClicked(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(100, 101, 0, 0);
        jPanel1.add(connex2, gridBagConstraints);

        connex3.setText("Connex 3");
        connex3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                connexClicked(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(100, 121, 0, 0);
        jPanel1.add(connex3, gridBagConstraints);

        connex4.setText("Connex 4");
        connex4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                connexClicked(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(100, 91, 0, 37);
        jPanel1.add(connex4, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        add(jPanel1, gridBagConstraints);
    }// </editor-fold>//GEN-END:initComponents

    private void doConnex(java.awt.event.ActionEvent evt)//GEN-FIRST:event_doConnex
    {//GEN-HEADEREND:event_doConnex
        if (clickLabel == null)
        {
            return;
        }
        String name = clickLabel.getText();
        List<ModemInfo> modems = loadData();
        for (ModemInfo m : modems)
        {
            if (m.getName().equalsIgnoreCase(name))
            {
                fireChangeModemListener(m.getName(), m.getPort(), m.getFrequency());
                return;
            }
        }
    }//GEN-LAST:event_doConnex
    private void deleteConnex(java.awt.event.ActionEvent evt)
    {
        if (clickLabel == null)
        {
            return;
        }
        String name = clickLabel.getText();
        List<ModemInfo> modems = loadData();
        for (ModemInfo m : modems)
        {
            if (m.getName().equalsIgnoreCase(name))
            {
                EntityManager em = MyEntityManager.emf.createEntityManager();
                em.getTransaction().begin();
                em.remove(em.merge(m));
                em.getTransaction().commit();
                this.loadConnexion();
                return;
            }
        }
    }

    private void connexClicked(java.awt.event.MouseEvent evt)
    {
        popup.setLocation(evt.getLocationOnScreen());
        popup.setVisible(true);
        clickLabel = (JLabel) evt.getSource();
    }

    private void btSaveActionPerformed(java.awt.event.ActionEvent evt)
    {
        defineSaveAction();
    }

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt)
    {
        processSwingEvent();
    }

    @Override
    public void saveModemConfig(String name, String port, int frequence)
    {
        ModemInfo mInfo = new ModemInfo();
        mInfo.setFrequency(frequence);
        mInfo.setName(name);
        mInfo.setPort(port);
        EntityManager em = MyEntityManager.emf.createEntityManager();
        em.getTransaction().begin();
        em.persist(mInfo);
        em.getTransaction().commit();
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btSave;
    private javax.swing.JComboBox cbFreq;
    private javax.swing.JComboBox cbPortList;
    private javax.swing.JLabel connex1;
    private javax.swing.JLabel connex2;
    private javax.swing.JLabel connex3;
    private javax.swing.JLabel connex4;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JPopupMenu popup;
    private javax.swing.JTextField txtCon;
    // End of variables declaration//GEN-END:variables
}