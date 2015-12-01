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
		DiffieHellman dh = new DiffieHellman();
		Key  paramPublic = dh.clientGenerateParams();
		byte[] cipherPublic = CipherFunctions.cipher(paramPublic.getEncoded(), CipherFunctions.generateKeyBYPassword(password));
		//1.1 generating xml with the encrypted public value
		ConnectionXML connXML = new ConnectionXML();
		Document doc = connXML.createDoc();
		doc=connXML.setId(doc, serverID);
		doc=connXML.setMessage(doc,printBase64Binary(cipherPublic));
		//1.2 sending to the Server
		byte[] result = port.loginDB(FunctionsXML.XMLtoBytes(doc));
		//1.3 generating  session key
		Document doc2  = FunctionsXML.BytesToXML(result);
		byte[] keyBytes= CipherFunctions.decipher(parseBase64Binary(new ConnectionXML().getMessage(doc2)),CipherFunctions.generateKeyBYPassword(password));
		PublicKey key = CipherFunctions.generatePublicKeyFromBytes(keyBytes);
		Key keySessionDB =dh.clientGenerateKey(key);
		System.out.println(printBase64Binary(keySessionDB.getEncoded()));
		//1.4 creating challenge
		byte[]  challenge = CipherFunctions.SecureRandomNumber() ;
		System.out.println("C1 -> sent: " + printBase64Binary(challenge));
		byte[] challengeCiphered = CipherFunctions.cipher(challenge, keySessionDB);
		//1.5 XML challenge
		ConnectionXML connXML1 = new ConnectionXML();
		Document doc1 = connXML1.createDoc();
		doc1 = connXML1.setC1(doc1,printBase64Binary(challengeCiphered));
		byte[] result1= port.sendChallenge(FunctionsXML.XMLtoBytes(doc1));
		//check challenge
		Document doc3  = FunctionsXML.BytesToXML(result1);
		byte[] c1Received =CipherFunctions.decipher(parseBase64Binary(connXML1.getC1(doc1)), keySessionDB);
		System.out.println("C1 -> received: " + printBase64Binary(c1Received));
		String message = ValidateRequests.validateChallenge(challenge, c1Received);
		System.out.println("message: " + message);
		Document doc4 = connXML.createDoc();
		System.out.println("C2-client: " + connXML1.getC2(doc3));
		doc4=connXML1.setC2(doc4, connXML1.getC2(doc3));
		doc4=connXML1.setMessage(doc4, message);
		byte[] result3 = port.checkChallenge(FunctionsXML.XMLtoBytes(doc4));
		System.out.println(String.valueOf(result3));
		
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
