package com.darrylsite.core;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import org.smslib.helper.CommPortIdentifier;
import org.smslib.helper.SerialPort;

/**
 * cette classe assure le controle avec le materiel. Elle permet de determiner les
 * ports disponibles, et utiliser les commandes AT directement sur les materiel
 * @author Kpizingui Darryl
 * @web http://www.darrylsite.com
*/
public class HardControler
{

  /**
   * Donne la liste des noms des ports series disponibles
   * sur l'ordinateur
   * @return
   */
  public static List<String> listAllSerialPort()
  {
     List<String> ports= new ArrayList<String>();

     Enumeration portList = CommPortIdentifier.getPortIdentifiers();
     while (portList.hasMoreElements())
     {
        CommPortIdentifier portId = (CommPortIdentifier)portList.nextElement();
        if (portId.getPortType() == CommPortIdentifier.PORT_SERIAL)
          ports.add(portId.getName());
     }
     return ports;

  }

  public String getPortHolderName(String port)
  {

   Enumeration portList = getCleanPortIdentifiers();
    while (portList.hasMoreElements())
    {
      CommPortIdentifier portId = (CommPortIdentifier)portList.nextElement();
      if (!portId.getName().equals(port))
        continue;
      /**
       *  Commande AT pour prendre le nom du peripherie
       */
     break;

    }
   return "";
  }

  /**
   * 
   * @return
   */
  private static Enumeration<CommPortIdentifier> getCleanPortIdentifiers()
  {
    return CommPortIdentifier.getPortIdentifiers();
  }

  /**
 * Donne la liste des frequences sur lesquelles on peut communiquer
 * sur un port donné
 * @param port le nom du port
 * @return la liste des frequences disponibles
 */
  public static ArrayList<String> listFrequency(String port)
  {
    int[] bauds = { 2400, 9600, 14400, 19200 };
    ArrayList liste = new ArrayList();

    Enumeration portList = getCleanPortIdentifiers();
    while (portList.hasMoreElements())
    {
      CommPortIdentifier portId = (CommPortIdentifier)portList.nextElement();
      if (!portId.getName().equals(port))
        continue;
      for (int i = 0; i < bauds.length; ++i)
      {
        SerialPort serialPort = null;
        try
        {
          serialPort = portId.open("nabster", 1971);
          serialPort.setFlowControlMode(SerialPort.FLOWCONTROL_RTSCTS_IN);
          serialPort.setSerialPortParams(bauds[i], SerialPort.DATABITS_8, SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);
          InputStream inStream = serialPort.getInputStream();
          OutputStream outStream = serialPort.getOutputStream();
          serialPort.enableReceiveTimeout(500);
          int c = inStream.read();
          while (c != -1)
            c = inStream.read();
          outStream.write(65);
          outStream.write(84);
          outStream.write(13);
          Thread.sleep(100L);
          String response = "";
          c = inStream.read();
          while (c != -1)
          {
            response = response + (char)c;
            c = inStream.read();
          }
          if (response.indexOf("OK") >= 0)
          {
            liste.add("" + bauds[i]);
          }
        }
        catch (Exception e)
        {
        }
        finally
        {
          if (serialPort != null)
          {
            serialPort.close();
          }
        }
      }
    }

    return liste;
  }

}
