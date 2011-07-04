/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.darrylsite.listener;

import org.smslib.InboundMessage;

/**
 *
 * @author Kpizingui Darryl
 * @web http://www.darrylsite.com
*/
public interface MessageReceiveListener
{
    public void messageReceive(InboundMessage msg);
}
