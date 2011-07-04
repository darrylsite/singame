package examples.modem;

import java.util.ArrayList;
import java.util.List;
import javax.crypto.spec.SecretKeySpec;
import org.smslib.AGateway;
import org.smslib.ICallNotification;
import org.smslib.IGatewayStatusNotification;
import org.smslib.IInboundMessageNotification;
import org.smslib.IOrphanedMessageNotification;
import org.smslib.InboundMessage;
import org.smslib.Library;
import org.smslib.Message;
import org.smslib.Service;
import org.smslib.crypto.AESKey;
import org.smslib.modem.SerialModemGateway;



public class ReadMessages
{
  Service srv;

  public void doIt()
    throws Exception
  {
    InboundNotification inboundNotification = new InboundNotification();

    CallNotification callNotification = new CallNotification();

    GatewayStatusNotification statusNotification = new GatewayStatusNotification();

    OrphanedMessageNotification orphanedMessageNotification = new OrphanedMessageNotification();
    try
    {
      System.out.println("Example: Read messages from a serial gsm modem.");
      System.out.println(Library.getLibraryDescription());
      System.out.println("Version: " + Library.getLibraryVersion());

      this.srv = new Service();

      SerialModemGateway gateway = new SerialModemGateway("modem.com6", "COM6", 57600, "Nabster", "AT");

      gateway.setProtocol(AGateway.Protocols.PDU);

      gateway.setInbound(true);

      gateway.setOutbound(true);

      gateway.setSimPin("0000");

      this.srv.setInboundMessageNotification(inboundNotification);
      this.srv.setCallNotification(callNotification);
      this.srv.setGatewayStatusNotification(statusNotification);
      this.srv.setOrphanedMessageNotification(orphanedMessageNotification);

      this.srv.addGateway(gateway);

      this.srv.startService();

      System.out.println();
      System.out.println("Modem Information:");
      System.out.println("  Manufacturer: " + gateway.getManufacturer());
      System.out.println("  Model: " + gateway.getModel());
      System.out.println("  Serial No: " + gateway.getSerialNo());
      System.out.println("  SIM IMSI: " + gateway.getImsi());
      System.out.println("  Signal Level: " + gateway.getSignalLevel() + "%");
      System.out.println("  Battery Level: " + gateway.getBatteryLevel() + "%");
      System.out.println();

      this.srv.getKeyManager().registerKey("+306948494037", new AESKey(new SecretKeySpec("0011223344556677".getBytes(), "AES")));

      List<InboundMessage> msgList = new ArrayList<InboundMessage>();
      this.srv.readMessages(msgList, InboundMessage.MessageClasses.ALL);
      for (InboundMessage msg : msgList) {
        System.out.println(msg);
      }

      System.out.println("Now Sleeping - Hit <enter> to stop service.");
      System.in.read(); System.in.read();
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
    finally
    {
      this.srv.stopService();
    }
  }

  public static void main(String[] args)
  {
    ReadMessages app = new ReadMessages();
    try
    {
      app.doIt();
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
  }

  public class OrphanedMessageNotification
    implements IOrphanedMessageNotification
  {
    public OrphanedMessageNotification()
    {
    }

    public boolean process(String gatewayId, InboundMessage msg)
    {
      System.out.println(">>> Orphaned message part detected from " + gatewayId);
      System.out.println(msg);

      return false;
    }
  }

  public class GatewayStatusNotification
    implements IGatewayStatusNotification
  {
    public GatewayStatusNotification()
    {
    }

    public void process(String gatewayId, AGateway.GatewayStatuses oldStatus, AGateway.GatewayStatuses newStatus)
    {
      System.out.println(">>> Gateway Status change for " + gatewayId + ", OLD: " + oldStatus + " -> NEW: " + newStatus);
    }
  }

  public class CallNotification
    implements ICallNotification
  {
    public CallNotification()
    {
    }

    public void process(String gatewayId, String callerId)
    {
      System.out.println(">>> New call detected from Gateway: " + gatewayId + " : " + callerId);
    }
  }

  public class InboundNotification
    implements IInboundMessageNotification
  {
    public InboundNotification()
    {
    }

    public void process(String gatewayId, Message.MessageTypes msgType, InboundMessage msg)
    {
      if (msgType == Message.MessageTypes.INBOUND) System.out.println(">>> New Inbound message detected from Gateway: " + gatewayId);
      else if (msgType == Message.MessageTypes.STATUSREPORT) System.out.println(">>> New Inbound Status Report message detected from Gateway: " + gatewayId);
      System.out.println(msg);
    }
  }
}