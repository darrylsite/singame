package com.darrylsite.ihm.reader;

/**
 *
 * @author nabster
 */
public interface ReceiptMessageListener
{
   public void answer(long id);
   public void delete(long id);
   public void forward(long id);
}
