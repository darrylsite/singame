package com.darrylsite.ihm;

import com.darrylsite.entite.ModemInfo;

/**
 * contient les reference des ihm utilisés par l'application pour
 * eviter de recreer une interface donnée plusieurs fois
 * @author nabster
 */
public class IHMContext
{
    private static MessageReceiveIHM ihmReceiver = null;
    private static MessageSentIHM ihmSent = null;
    private static MessageTashIHM ihmTrash = null;
    private static SenderIHM ihmSender;
    private static MessageToSentIHM ihmToSent = null;
    private static boolean _DEBUG = true;
    private static ModemInfo currentModem = null;
    
    public static MessageReceiveIHM getIhmReceiver() {
        return ihmReceiver;
    }

    public static void setIhmReceiver(MessageReceiveIHM ihmReceiver)
    {
        IHMContext.ihmReceiver = ihmReceiver;
    }

    public static MessageSentIHM getIhmSent()
    {
        return ihmSent;
    }

    public static void setIhmSent(MessageSentIHM ihmSent)
    {
        IHMContext.ihmSent = ihmSent;
    }

    public static MessageTashIHM getIhmTrash()
    {
        return ihmTrash;
    }

    public static void setIhmTrash(MessageTashIHM ihmTrash)
    {
        IHMContext.ihmTrash = ihmTrash;
    }

    public static void setIhmSender(SenderIHM ihmSender)
    {
        IHMContext.ihmSender = ihmSender;
    }

    public static SenderIHM getIhmSender()
    {
        return ihmSender;
    }

    public static MessageToSentIHM getIhmToSent()
    {
        return ihmToSent;
    }

    public static void setIhmToSent(MessageToSentIHM ihmToSent)
    {
        IHMContext.ihmToSent = ihmToSent;
    }

    public static boolean isDEBUG()
    {
        return _DEBUG;
    }

    public static void setDEBUG(boolean _DEBUG)
    {
        IHMContext._DEBUG = _DEBUG;
    }

    public static ModemInfo getCurrentModem()
    {
        return currentModem;
    }

    public static void setCurrentModem(ModemInfo currentModem)
    {
        IHMContext.currentModem = currentModem;
    }

    
    
}
