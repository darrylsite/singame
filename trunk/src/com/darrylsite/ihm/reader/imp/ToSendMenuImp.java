package com.darrylsite.ihm.reader.imp;

import com.darrylsite.core.MyEntityManager;
import com.darrylsite.entite.MsgStatus.Status;
import com.darrylsite.entite.TimedMessage;
import com.darrylsite.entite.TimedMessages;
import com.darrylsite.entite.TrashMessages;
import com.darrylsite.ihm.Singame;
import com.darrylsite.ihm.reader.ToSendMessageListener;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 *
 * @author nabster
 */
public class ToSendMenuImp implements ToSendMessageListener
{
    @Override
    public void modifyMessage(long id)
    {
        EntityManager em = MyEntityManager.emf.createEntityManager();
        em.getTransaction().begin();
        TimedMessage tdMessage = em.find(TimedMessage.class, id);
        em.remove(tdMessage);

        Singame.Application.addSenderIHM(tdMessage.getNumero(), tdMessage);
    }

    @Override
    public void sendNow(long id)
    {
        EntityManager em = MyEntityManager.emf.createEntityManager();
        em.getTransaction().begin();

    }

    @Override
    public void cancelSending(long id)
    {
        EntityManager em = MyEntityManager.emf.createEntityManager();
        em.getTransaction().begin();

        Query query = em.createQuery("select m from TimedMessage m where m.id= :id");
        query.setParameter("id", id);
        TimedMessage msg = (TimedMessage) query.getSingleResult();

        query = em.createQuery("select m from TimedMessages m");
        TimedMessages md = (TimedMessages) query.getSingleResult();
        md.getMessages().remove(msg);
        em.remove(msg);
        em.flush();

        //ajout du message dans la corbeille
        
        em.persist(msg);
        query = em.createQuery("select m from TrashMessages m");
        TrashMessages thMsgs = (TrashMessages) query.getSingleResult();
        thMsgs.getMessages().add(msg);

        em.getTransaction().commit();
    }

    @Override
    public void emptyFolder()
    {
        EntityManager em = MyEntityManager.emf.createEntityManager();
        em.getTransaction().begin();
        em.flush();
        em.getTransaction().commit();

        Query query = em.createQuery("select m from TimedMessages m");
        TimedMessages tmMsgs = (TimedMessages)query.getSingleResult();
        List<TimedMessage> liste = tmMsgs.getMessages();
        for(TimedMessage msg : liste)
        {
         cancelSending(msg.getId());
        }
    }
}
