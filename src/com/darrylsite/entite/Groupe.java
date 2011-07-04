/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.darrylsite.entite;

import java.io.Serializable;
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
public class Groupe implements Serializable
{
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;


    @OneToMany(mappedBy = "groupe")
    private List<Contact> contacts;

    private String comment;




    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public String getComment()
    {
        return comment;
    }

    public void setComment(String comment)
    {
        this.comment = comment;
    }

    public List<Contact> getContacts()
    {
        return contacts;
    }

    public void setContacts(List<Contact> contacts)
    {
        this.contacts = contacts;
    }

    public String getName()
    {
        return name;
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
        if (!(object instanceof Groupe))
        {
            return false;
        }
        Groupe other = (Groupe) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString()
    {
        return "com.darrylsite.entite.Groupe[id=" + id + "]";
    }

}
