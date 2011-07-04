package com.darrylsite.entite;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.Temporal;

/**
 *
 * @author nabster
 */
@Entity
public class TimedMessage  extends Message implements Serializable
{
    private static final long serialVersionUID = 1L;
   
    // Time to send the message
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date tDate;

    public Date getTimedDate()
    {
        return tDate;
    }

    public void setTimedDate(Date tDate)
    {
        this.tDate = tDate;
    }


    public Date gettDate() {
        return tDate;
    }

    public void settDate(Date tDate) {
        this.tDate = tDate;
    } 

    @Override
    public int hashCode()
    {
        int hash = 0;
        hash += (getId() != null ? getId().hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object)
    {
        if (!(object instanceof TimedMessage))
        {
            return false;
        }
        TimedMessage other = (TimedMessage) object;
        if ((this.getId() == null && other.getId() != null) || (this.getId() != null && !this.getId().equals(other.getId()))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString()
    {
        return "com.darrylsite.entite.TimedMessage[id=" + getId() + "]";
    }

}
