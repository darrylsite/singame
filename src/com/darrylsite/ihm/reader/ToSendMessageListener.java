package com.darrylsite.ihm.reader;

/**
 * @author nabster
 */
public interface ToSendMessageListener
{
  public void modifyMessage(long id);
  public void sendNow(long id);
  public void cancelSending(long id);
  public void emptyFolder();
}
