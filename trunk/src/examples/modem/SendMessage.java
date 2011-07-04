package examples.modem;

import org.smslib.IOutboundMessageNotification;
import org.smslib.OutboundMessage;
import org.smslib.Service;
import org.smslib.modem.SerialModemGateway;

public class SendMessage
{
  public void doIt()
    throws Exception
  {
    OutboundNotification outboundNotification = new OutboundNotification();
    System.out.println("Example: Send message from a serial gsm modem.");

    Service srv = new Service();
    SerialModemGateway gateway = new SerialModemGateway("nabsterConnect", "COM6", 57600, "nabster inc", "V.1");
    gateway.setInbound(true);
    gateway.setOutbound(true);
    gateway.setSimPin("0000");
    srv.setOutboundMessageNotification(outboundNotification);
    srv.addGateway(gateway);
    srv.startService();

    OutboundMessage msg = new OutboundMessage("+306948494037", "Hello from SMSLib!");
    srv.sendMessage(msg);

    msg = new OutboundMessage("+309999999999", "Wrong number!");
    srv.queueMessage(msg, gateway.getGatewayId());
    msg = new OutboundMessage("+308888888888", "Wrong number!");
    srv.queueMessage(msg, gateway.getGatewayId());
    System.out.println("Now Sleeping - Hit <enter> to terminate.");
    System.in.read();
    srv.stopService();
  }

  public static void main(String[] args)
  {
    SendMessage app = new SendMessage();
    try
    {
      app.doIt();
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
  }

  public class OutboundNotification
    implements IOutboundMessageNotification
  {
    public OutboundNotification()
    {
    }

    public void process(String gatewayId, OutboundMessage msg)
    {
      System.out.println("Outbound handler called from Gateway: " + gatewayId);
      System.out.println(msg);
    }
  }
}