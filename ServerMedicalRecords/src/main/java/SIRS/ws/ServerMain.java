package SIRS.ws;

import java.security.Key;
import java.security.PublicKey;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.registry.JAXRException;
import javax.xml.ws.Endpoint;

import java.security.SecureRandom;

import org.jdom2.Document;

import static javax.xml.bind.DatatypeConverter.parseBase64Binary;
import static javax.xml.bind.DatatypeConverter.printBase64Binary;
import sirs.ws.ServerDB;
import SIRS.ws.uddi.UDDINaming;
import SIRS.CryptoTools.*;
public class ServerMain {
	public static String[] serverDBEndPoint=new String[3];//uddiURLDB nameDB
	public static Key keySessionDB = null;
	public static void main(String[] args) throws Exception {
	
		String password ="pAw5S3GZ";
		String serverID = "S1";
//-------------------------------------------------------------SERVERDB CONNECTION----------------------------------------------------------------
		
		serverDBEndPoint[0] =args[3];
		System.out.println("serverDBEndPoint[0]:" + serverDBEndPoint[0]);
		serverDBEndPoint[1]=args[4];
		ServerDB port = ConnectionServerDB.getPortServerDB();
		
		//1- establishing a session key with ServerDB
		DiffieHellman dh = new DiffieHellman(password,serverID);
		//1.2 sending to the Server
		byte[] result = port.loginDB(dh.sendClientParameters());
		//1.3 generating  session key		
		keySessionDB =dh.receiveServerParameters(result);
		System.out.println("KeyAgreed: " + printBase64Binary(keySessionDB.getEncoded()));
		//1.4 creating challenge
		byte[] result1= port.sendChallenge(dh.sentClientChallenge());
		//check challenge
		byte[] result2 = port.checkChallenge(dh.checkChallenge(result1));
		System.out.println(String.valueOf(result2));
		
//-----------------------------ServerPublish--------------------------------------
      // Check arguments
        if (args.length < 3) {
            System.err.println("Argument(s) missing!");
            System.err.printf("Usage: java %s uddiURL wsName wsURL%n", ServerMain.class.getName());
            return;
        }

        String uddiURL = args[0];
        String name = args[1];
        String url = args[2];

        Endpoint endpoint = null;
        SIRS.ws.uddi.UDDINaming uddiNaming = null;
        try {
            endpoint = Endpoint.create(new ServerImpl());
            System.out.println("ServerPedidos: " + String.valueOf(endpoint));
            // publish endpoint
            System.out.printf("Starting %s%n", url);
            endpoint.publish(url);

            // publish to UDDI
            System.out.printf("Publishing '%s' to UDDI at %s%n", name, uddiURL);
            uddiNaming = new UDDINaming(uddiURL);
            uddiNaming.rebind(name, url);

            // wait
            System.out.println("Awaiting connections");
            System.out.println("Press enter to shutdown");
            System.in.read();

        } catch (Exception e) {
            System.out.printf("Caught exception: %s%n", e);
            e.printStackTrace();

        } finally {
            try {
                if (endpoint != null) {
                    // stop endpoint
                    endpoint.stop();
                    System.out.printf("Stopped %s%n", url);
                }
            } catch (Exception e) {
                System.out.printf("Caught exception when stopping: %s%n", e);
            }
            try {
                if (uddiNaming != null) {
                    // delete from UDDI
                    uddiNaming.unbind(name);
                    System.out.printf("Deleted '%s' from UDDI%n", name);
                }
            } catch (Exception e) {
                System.out.printf("Caught exception when deleting: %s%n", e);
            }
        }
        
      

    }
}
