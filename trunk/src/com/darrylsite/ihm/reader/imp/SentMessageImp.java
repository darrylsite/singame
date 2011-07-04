
package com.darrylsite.ihm.reader.imp;

import com.darrylsite.core.MyEntityManager;
import com.darrylsite.entite.Message;
import com.darrylsite.entite.MsgStatus.Status;
import com.darrylsite.entite.TimedMessage;
import com.darrylsite.entite.TimedMessages;
import com.darrylsite.entite.TrashMessages;
import com.darrylsite.ihm.Singame;
import com.darrylsite.ihm.reader.SentMessagerListener;
import java.util.Calendar;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 *
 * @author nabster
 */
public class SentMessageImp implements SentMessagerListener
{

    @Override
    public void reSend(long id)
    {
       EntityManager em = MyEntityManager.emf.createEntityManager();
       Message msg = em.find(Message.class, id);
       delete(id);
       TimedMessage tmdMesg = new TimedMessage();
       tmdMesg.setDates(Calendar.getInstance().getTime());
       tmdMesg.setMessage(msg.getMessage());
       tmdMesg.setNumero(msg.getNumero());
       tmdMesg.setTimedDate(tmdMesg.getDates());
       tmdMesg.setStatus(Status.unsent);
       em.getTransaction().begin();
       em.persist(tmdMesg);
       //Ajout du messages dans le dossier dossier des messages envoyes
       Query query = em.createQuery("select m from TimedMessages m");
       List<TimedMessages> liste = query.getResultList();     
       TimedMessages tm = (TimedMessages) liste.get(0);
       tm.addMessage(tmdMesg);
       
       em.getTransaction().commit();
       //Affichage de la liste des messages envoyes
       Singame.Application.showToSentIHM();
    }

    @Override
    public void delete(long id)
    {
        EntityManager em = MyEntityManager.emf.createEntityManager();
        em.getTransaction().begin();
        Query query = em.createQuery("select m from Message m where m.id= :id");       
        query.setParameter("id", id);
        Message msg = (Message)query.getSingleResult();
        em.remove(msg);
        
        query = em.createQuery("select m from TrashMessages m");
        List liste = query.getResultList();
        if(liste.isEmpty())
          return;
        TrashMessages ms = (TrashMessages)liste.get(0);
        ms.getMessages().add(msg);   
        msg.setId(0l);
        em.persist(msg);
        em.getTransaction().commit(); 
    }

    @Override
    public void forward(long id)
    {
        EntityManager em = MyEntityManager.emf.createEntityManager();
        Query query = em.createQuery("select m from Message m where m.id= :id");       
        query.setParameter("id", id);
        Message msg = (Message)query.getSingleResult();
        Singame.Application.addSenderIHM("", msg);
    }
    
}
