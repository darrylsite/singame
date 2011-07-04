package com.darrylsite.core;

import com.darrylsite.entite.Message;
import com.darrylsite.listener.MessageSentListener;
import com.darrylsite.log.MyLogger;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.logging.Level;
import org.smslib.GatewayException;
import org.smslib.OutboundMessage;
import org.smslib.TimeoutException;

/**
 * Permet l'envoie des messages
 * @author Kpizingui Darryl
 * @web http://www.darrylsite.com
 */
public class ModemMessageSender
{

    Modem modem;
    List<MessageSentListener> msgListener;

    public ModemMessageSender(Modem modem)
    {
        this.modem = modem;
        msgListener = new ArrayList<MessageSentListener>();
    }

    public void addMessageSentListener(MessageSentListener l)
    {
        msgListener.add(l);
    }

    public void removeMessageSentListener(MessageSentListener l)
    {
        msgListener.remove(l);
    }

    private void fireMessageSentListener(Message msg)
    {
        for (MessageSentListener l : msgListener)
        {
            l.messageSent(msg);
        }
    }

    public boolean sendMessage(String msg, String numb)
    {
        Message message = new Message();
        message.setDates(Calendar.getInstance().getTime());
        message.setMessage(msg);
        message.setNumero(numb);
        OutboundMessage mes = new OutboundMessage(numb, msg);

        try
        {
            boolean b = modem.getService().sendMessage(mes);
            if (b)
            {
                fireMessageSentListener(message);
            }
            return b;
        } catch (TimeoutException ex)
        {
            MyLogger.getLogger().log(Level.SEVERE, null, ex);
            return false;
        } catch (GatewayException ex)
        {
            MyLogger.getLogger().log(Level.SEVERE, null, ex);
            return false;
        } catch (IOException ex)
        {
            MyLogger.getLogger().log(Level.SEVERE, null, ex);
            return false;
        } catch (InterruptedException ex)
        {
            MyLogger.getLogger().log(Level.SEVERE, null, ex);
            return false;
        }

    }

    public void sendMessage(String msg, String... numb)
    {
        for (String n : numb)
        {
            sendMessage(msg, n);
        }
    }
}
