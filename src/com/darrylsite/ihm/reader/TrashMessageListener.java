package com.darrylsite.ihm.reader;

/**
 *
 * @author nabster
 */
public interface TrashMessageListener
{
    public void delete(long id);
    public void resume(long id);
    public void emptyFolder();
}
