
package com.darrylsite.entite;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

/**
 *
 * @author nabster
 */
@Entity
public class TimedMessages implements Serializable
{
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToMany
    private List<TimedMessage> messages;

    private String name;

    public TimedMessages()
    {
        messages = new ArrayList<TimedMessage>();
    }



    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public List<TimedMessage> getMessages()
    {
        return messages;
    }

    public void setMessages(List<TimedMessage> messages)
    {
        this.messages = messages;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public void addMessage(TimedMessage message)
    {
        this.messages.add(message);
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
        if (!(object instanceof TimedMessages))
        {
            return false;
        }
        TimedMessages other = (TimedMessages) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id)))
        {
            return false;
        }
        return true;
    }

    @Override
    public String toString()
    {
        return "com.darrylsite.entite.TimedMessages[id=" + id + "]";
    }

}
