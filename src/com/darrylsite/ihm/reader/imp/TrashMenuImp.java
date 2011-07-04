
package com.darrylsite.ihm.reader.imp;

import com.darrylsite.core.MyEntityManager;
import com.darrylsite.entite.Message;
import com.darrylsite.entite.ReceiveMessages;
import com.darrylsite.entite.SentMessages;
import com.darrylsite.entite.TimedMessage;
import com.darrylsite.entite.TimedMessages;
import com.darrylsite.entite.TrashMessages;
import com.darrylsite.ihm.reader.TrashMessageListener;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 *
 * @author nabster
 */
public class TrashMenuImp implements TrashMessageListener
{

    @Override
    public void delete(long id)
    {
        EntityManager em = MyEntityManager.emf.createEntityManager();
        em.getTransaction().begin();
        Message msg = em.find(Message.class, id);

        Query query = em.createQuery("select m from TrashMessages m");
        TrashMessages thMsgs = (TrashMessages) query.getSingleResult();
        thMsgs.getMessages().remove(msg);
        em.remove(msg);
        
        em.getTransaction().commit();
    }

    @Override
    public void resume(long id)
    {
        EntityManager em = MyEntityManager.emf.createEntityManager();
        em.getTransaction().begin();
        Message msg = em.find(Message.class, id);
        em.getTransaction().commit();
        
        delete(msg.getId());
       
        switch(msg.getStatus().getStatus())
        {
            case read : case unread:
                   restaureReceiveMsg(msg);
                   break;
                
            case sent :
                   restaureSendMsg(msg);
                   break;
            
            case unsent :
                   restaureUnSentMsg(msg);
                   break;               
        }
    }
    
    private void restaureReceiveMsg(Message msg)
    {
        EntityManager em = MyEntityManager.emf.createEntityManager();
        em.getTransaction().begin();
        Query query = em.createQuery("select m from ReceiveMessages m");
        ReceiveMessages ms = (ReceiveMessages)query.getSingleResult();
        msg.setId(null);
        em.persist(msg);
        ms.addMessages(msg);
        em.getTransaction().commit();

    }
    
    private void restaureSendMsg(Message msg)
    {
        EntityManager em = MyEntityManager.emf.createEntityManager();
        em.getTransaction().begin();
        Query query = em.createQuery("select m from SentMessages m");
        SentMessages ms = (SentMessages)query.getSingleResult();
         msg.setId(null);
        em.persist(msg);
        ms.addMessage(msg);
        em.getTransaction().commit();
    }
    
    private void restaureUnSentMsg(Message msg)
    {
        EntityManager em = MyEntityManager.emf.createEntityManager();
        em.getTransaction().begin();
        Query query = em.createQuery("select m from TimedMessages m");
        TimedMessages ms = (TimedMessages)query.getSingleResult();
        msg.setId(null);
        em.persist(msg);
        ms.addMessage((TimedMessage)msg);
        em.getTransaction().commit();
    }

    @Override
    public void emptyFolder()
    {
        EntityManager em = MyEntityManager.emf.createEntityManager();
        em.getTransaction().begin();
        em.flush();
        em.getTransaction().commit();

        Query query = em.createQuery("select m from TrashMessages m");
        TrashMessages tmMsgs = (TrashMessages)query.getSingleResult();
        List<Message> liste = tmMsgs.getMessages();
        for(Message msg : liste)
        {
         delete(msg.getId());
        }
    }  
}
