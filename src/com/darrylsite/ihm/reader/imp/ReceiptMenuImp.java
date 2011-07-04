
package com.darrylsite.ihm.reader.imp;

import com.darrylsite.core.MyEntityManager;
import com.darrylsite.entite.Message;
import com.darrylsite.entite.MsgStatus.Status;
import com.darrylsite.entite.TrashMessages;
import com.darrylsite.ihm.Singame;
import com.darrylsite.ihm.reader.ReceiptMessageListener;
import java.util.Calendar;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 *
 * @author nabster
 */
public class ReceiptMenuImp implements ReceiptMessageListener
{

    @Override
    public void answer(long id)
    {
        EntityManager em = MyEntityManager.emf.createEntityManager();
        Query query = em.createQuery("select m from Message m where m.id= :id");       
        query.setParameter("id", id);
        Message msg = (Message)query.getSingleResult();
        msg.setDates(Calendar.getInstance().getTime());
        msg.setMessage("");
        Singame.Application.addSenderIHM(msg.getNumero(), msg);
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
