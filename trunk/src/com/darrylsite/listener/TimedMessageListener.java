
package com.darrylsite.listener;

import com.darrylsite.entite.Message;

/**
 *
 * @author Kpizingui Darryl
 * @web http://www.darrylsite.com
*/
public interface TimedMessageListener
{
  public void processedMsg(Message msg);
}
