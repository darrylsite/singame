package com.darrylsite.entite;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class ModemInfo implements Serializable
{
	private String name;

	private String port;

	private int frequency;
        
        private boolean inuse;

	@Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        private Long id;

	/**
	 * @return the id
	 */
	public Long getId()
	{
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id)
	{
		this.id = id;
	}

	/**
	 * @return the name
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name)
	{
		this.name = name;
	}

	/**
	 * @return the port
	 */
	public String getPort()
	{
		return port;
	}

	/**
	 * @param port the port to set
	 */
	public void setPort(String port)
	{
		this.port = port;
	}

	/**
	 * @return the frequency
	 */
	public int getFrequency()
	{
		return frequency;
	}

	/**
	 * @param frequency the frequency to set
	 */
	public void setFrequency(int frequency)
	{
		this.frequency = frequency;
	}

    public boolean isInuse()
    {
        return inuse;
    }

    public void setInuse(boolean inuse)
    {
        this.inuse = inuse;
    }
        
}
