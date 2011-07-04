package com.darrylsite.core;

import com.darrylsite.entite.Message;
import com.darrylsite.entite.MsgStatus.Status;
import com.darrylsite.entite.SentMessages;
import com.darrylsite.entite.TimedMessage;
import com.darrylsite.entite.TimedMessages;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import javax.swing.Timer;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 * Assure le traitement des messages différés
 * 
 * @author Kpizingui Darryl
 * @web http://www.darrylsite.com
 */
public class TimedMessageProcessor
{
    private Modem modem;
    private boolean running = false;
    private Timer timer;
    private int sleepTime = 10000; //10sec

    public TimedMessageProcessor(Modem modem)
    {
        this.modem = modem;
    }

    public void process()
    {
        EntityManager em = MyEntityManager.emf.createEntityManager();
        List<TimedMessage> liste = getMessages();
        for (TimedMessage t : liste)
        {
            if (t.gettDate().after(Calendar.getInstance().getTime()))
            {
                boolean b = modem.getMsgSender().sendMessage(t.getMessage(), t.getNumero());
                if (b)
                {
                    removeTimedMesg(t);
                }
            }
        }
        em.close();
    }

    private List<TimedMessage> getMessages()
    {
        EntityManager em = MyEntityManager.emf.createEntityManager();
        List<TimedMessage> liste = new ArrayList<TimedMessage>();
        try
        {
            Query query = em.createQuery("select t from TimedMessages t");
            TimedMessages t = (TimedMessages) query.getResultList().get(0);
            liste = t.getMessages();
        } catch (Exception e)
        {
        }

        return liste;
    }

    private void removeTimedMesg(TimedMessage msg)
    {
        EntityManager em = MyEntityManager.emf.createEntityManager();
        em.getTransaction().begin();

        msg = em.find(TimedMessage.class, msg.getId());

        Query query = em.createQuery("select m from TimedMessages m");
        TimedMessages md = (TimedMessages) query.getSingleResult();
        md.getMessages().remove(msg);
        em.remove(msg);
        em.flush();

        //ajout du message dans le dossier message envoye
        Message message = new Message();
        message.setDates(Calendar.getInstance().getTime());
        message.setMessage(msg.getMessage());
        message.setNumero(msg.getNumero());
        message.setStatus(Status.sent);
        em.persist(message);
        query = em.createQuery("select m from SentMessages m");
        SentMessages tMsgs = (SentMessages) query.getSingleResult();
        tMsgs.getMessages().add(msg);
        em.getTransaction().commit();
    }

    public void start()
    {
        if (running)
        {
            return;
        }
        this.timer = new Timer(sleepTime, new ActionListener()
        {

            @Override
            public void actionPerformed(ActionEvent evt)
            {
                TimedMessageProcessor.this.process();
            }
        });

        timer.setRepeats(true);
        timer.start();
        running = true;
    }

    public void stop()
    {
        if(!running)
            return;
        timer.stop();
        running = false;
    }
}
