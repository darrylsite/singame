package com.darrylsite.entite;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;

/**
 *
 * @author nabster
 */
@Entity
public class Message implements Serializable
{
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String numero;

    @Temporal(javax.persistence.TemporalType.DATE)
    private Date dates;

    private String message;
    @OneToOne(cascade=CascadeType.ALL)
    private MsgStatus status;


    public Long getId()
    {
        return id;
    }

    public MsgStatus getStatus()
    {
        return status;
    }

    public void setStatus(MsgStatus.Status status)
    {
        this.status = new MsgStatus();
        this.status.setStatus(status);
    }

    public Date getDates()
    {
        return dates;
    }

    public void setDates(Date date)
    {
        this.dates = date;
    }

    public String getMessage()
    {
        return message;
    }

    public void setMessage(String message)
    {
        this.message = message;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero)
    {
        this.numero = numero;
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
    public boolean equals(Object object)
    {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Message))
        {
            return false;
        }
        Message other = (Message) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString()
    {
        return "com.darrylsite.entite.Message[id=" + id + "]";
    }

}
