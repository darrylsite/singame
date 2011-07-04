

package com.darrylsite.entite;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 *
 * @author nabster
 */
@Entity
public class MsgStatus implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    public enum Status
    {
      unread, read, sent, unsent, deleted;
      
        private final String _read = "unread";
        private final String _unread = "read";
        private final String _sent = "sent";
        private final String _unsent = "unsent";
        private final String _deleted = "deleted";
      
        @Override
      public String toString()
      {
       if(this.equals(Status.read))
           {
             return _read;
           }
           else if(this.equals(Status.unread))
           {
             return _unread;
           }
           else if(this.equals(Status.sent))
           {
             return _sent;
           }
           else if(this.equals(Status.unsent))
           {
              return _unsent;
           }
           else
           {
               return _deleted;
           }
      }

    }

    private Status status;

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

   

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }



    @Override
    public int hashCode()
    {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MsgStatus)) {
            return false;
        }
        MsgStatus other = (MsgStatus) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.darrylsite.entite.MsgStatus[id=" + id + "]";
    }
}
