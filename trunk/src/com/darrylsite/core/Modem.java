
package com.darrylsite.core;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.smslib.GatewayException;
import org.smslib.SMSLibException;
import org.smslib.Service;
import org.smslib.TimeoutException;
import org.smslib.modem.SerialModemGateway;

/**
 * Cette classe contient la reference des objects specifiques
 * pour une instance d'un serveur donné
 * @author Kpizingui Darryl
 * @web http://www.darrylsite.com
*/
public class Modem
{
   private SerialModemGateway gateway;
   private Service service;
   private ModemMessageServer serveur;
   private static int _PORTINUSE = 0;
   private ModemMessageSender msgSender;

   /**
    * Cree un modem rattacher a un modem donne
    * @param port
    * @param frequence
    */
   public Modem(String port, int frequence, String pin)
   {
     _PORTINUSE++;
     gateway = new SerialModemGateway("com.darrylsite.singame_"+_PORTINUSE,  port, frequence, "", "AT");
     gateway.setInbound(true);
     gateway.setOutbound(true);
     gateway.setSimPin(pin);

     service = new Service();
     serveur = new ModemMessageServer(gateway, service);
     msgSender = new ModemMessageSender(this);
   }

   public void startModem() throws GatewayException, SMSLibException, TimeoutException, IOException, InterruptedException
   {
     service.addGateway(gateway);
     service.startService();
     serveur.start();
   }

   public void stopModem()
   {
        try
        {
            service.stopService();
            serveur.stop();
        }
        catch (SMSLibException ex)
        {
            Logger.getLogger(Modem.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch (IOException ex)
        {
            Logger.getLogger(Modem.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch (InterruptedException ex)
        {
            Logger.getLogger(Modem.class.getName()).log(Level.SEVERE, null, ex);
        }
   }

   public SerialModemGateway getGateway()
   {
       return gateway;
    }

   public void setGateway(SerialModemGateway gateway)
   {
        this.gateway = gateway;
    }

   public Service getService()
   {
        return service;
    }

   public void setService(Service service)
   {
        this.service = service;
   }

   /**
    * donne le nom du fabriquant du modem
    * @return
    */
   public String getManufacturer()
   {
        try
        {
            String[] manuf = gateway.getManufacturer().split(":");
            return (manuf.length > 1) ? manuf[1].replace("\"", "") : "";
        }
        catch (Exception ex)
        {
            Logger.getLogger(Modem.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "Undifined";
   }

   /**
    * indique le niveau du signal de reception
    * @return le niveau du signal en %
    */
   public double getSignalLevel()
   {
        try
        {
            return gateway.getSignalLevel();
        }
        catch (Exception ex)
        {
            Logger.getLogger(Modem.class.getName()).log(Level.SEVERE, null, ex);
        }

        return 0;
   }

   /**
    * donne le numero de serie du modem
    * @return
    */
   public String getSerialNumber()
   {
        try
        {
            String[] serie = gateway.getSerialNo().split(":");
            return serie.length > 1 ? serie[1] : "";
        }
        catch (Exception ex)
        {
            Logger.getLogger(Modem.class.getName()).log(Level.SEVERE, null, ex);
        }

        return "";
   }

    public static int getPORTINUSE()
    {
        return _PORTINUSE;
    }

    public ModemMessageSender getMsgSender()
    {
        return msgSender;
    }

    public ModemMessageServer getServeur()
    {
        return serveur;
    }

    public void setServeur(ModemMessageServer serveur)
    {
        this.serveur = serveur;
    }
    
}
