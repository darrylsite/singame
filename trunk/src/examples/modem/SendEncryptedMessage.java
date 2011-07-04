package examples.modem;

import javax.crypto.spec.SecretKeySpec;
import org.smslib.Library;
import org.smslib.OutboundEncryptedMessage;
import org.smslib.Service;
import org.smslib.crypto.AESKey;
import org.smslib.modem.SerialModemGateway;

public class SendEncryptedMessage
{
  public void doIt()
    throws Exception
  {
    System.out.println("Example: Send an encrypted message from a serial gsm modem.");
    System.out.println(Library.getLibraryDescription());
    System.out.println("Version: " + Library.getLibraryVersion());
    Service srv = new Service();
    SerialModemGateway gateway = new SerialModemGateway("modem.com1", "COM5", 57600, "Nokia", "E60");
    gateway.setInbound(true);
    gateway.setOutbound(true);
    gateway.setSimPin("0000");
    srv.addGateway(gateway);
    srv.startService();
    System.out.println();
    System.out.println("Modem Information:");
    System.out.println("  Manufacturer: " + gateway.getManufacturer());
    System.out.println("  Model: " + gateway.getModel());
    System.out.println("  Serial No: " + gateway.getSerialNo());
    System.out.println("  SIM IMSI: " + gateway.getImsi());
    System.out.println("  Signal Level: " + gateway.getSignalLevel() + "%");
    System.out.println("  Battery Level: " + gateway.getBatteryLevel() + "%");
    System.out.println();

    srv.getKeyManager().registerKey("+306948494037", new AESKey(new SecretKeySpec("0011223344556677".getBytes(), "AES")));

    OutboundEncryptedMessage msg = new OutboundEncryptedMessage("+306948494037", "Hello (encrypted) from SMSLib!".getBytes());
    srv.sendMessage(msg);
    System.out.println(msg);

    System.out.println("Now Sleeping - Hit <enter> to terminate.");
    System.in.read();
    srv.stopService();
  }

  public static void main(String[] args)
  {
    SendEncryptedMessage app = new SendEncryptedMessage();
    try
    {
      app.doIt();
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
  }
}