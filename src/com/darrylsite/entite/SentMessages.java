
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
 * @author nabster
 */
@Entity
public class SentMessages implements Serializable
{
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    @OneToMany
    private List<Message> messages;

    public SentMessages()
    {
        messages = new ArrayList<Message>();
    }

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public List<Message> getMessages()
    {
        return messages;
    }

    public void setMessages(List<Message> messages)
    {
        this.messages = messages;
    }

    public String getName()
    {
        return name;
    }

    public void addMessage(Message message)
    {
        this.messages.add(message);
    }

    public void setName(String name)
    {
        this.name = name;
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
        if (!(object instanceof SentMessages)) {
            return false;
        }
        SentMessages other = (SentMessages) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString()
    {
        return "com.darrylsite.entite.SentMessages[id=" + id + "]";
    }

}
