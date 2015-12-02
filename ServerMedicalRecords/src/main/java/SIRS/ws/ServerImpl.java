package SIRS.ws;
import static javax.xml.bind.DatatypeConverter.parseBase64Binary;
import static javax.xml.bind.DatatypeConverter.printBase64Binary;

import java.security.Key;

import javax.crypto.spec.SecretKeySpec;
import javax.jws.*;

import org.jdom2.Document;

import SIRS.CryptoTools.CipherFunctions;
import SIRS.CryptoTools.ConnectionXML;
import SIRS.CryptoTools.FunctionsXML;
import sirs.ws.*;

import java.util.Dictionary;
import java.util.HashMap;
import java.util.Map;

@WebService(endpointInterface="SIRS.ws.Server")
public class ServerImpl implements Server {
	Key keyServer =ServerMain.keySessionDB;
	ServerDB port = ConnectionServerDB.getPortServerDB();
	Map<String,Key>  mapKeys = new HashMap<String,Key>();

	public byte[] login(int userID, byte[] message) {
		byte[] result = port.generateSessionKeyDoctor(userID, message);//to ServerDB
		Document doc = FunctionsXML.BytesToXML(result);
		ConnectionXML conn = new ConnectionXML();
		String msg = conn.getMessage(doc);//publicParamsDoctor
		Key keyDoctor =new SecretKeySpec(CipherFunctions.decipher(parseBase64Binary(conn.getC1(doc)), keyServer), "AES");
		mapKeys.put(Integer.toString(userID),keyDoctor);
		System.out.println("key-Doctor - ServerMedical: " + printBase64Binary(keyDoctor.getEncoded()));
		Document doc1 =conn.createDoc();
		doc1= conn.setMessage(doc1, msg);
		
		return FunctionsXML.XMLtoBytes(doc1);
	}

	public byte[] getRegistries(int userID, byte[] message) {
		// TODO Auto-generated method stub
		return null;
	}

	public byte[] getRegistryByDate(int userID, byte[] message) {
		// TODO Auto-generated method stub
		return null;
	}

	public byte[] getRegistryBySpeciality(int userID, byte[] message) {
		// TODO Auto-generated method stub
		return null;
	}

	public byte[] sendChallenge(int userID, byte[] message) {
		// TODO Auto-generated method stub
		return null;
	}

	public byte[] checkChallenge(int userID, byte[] message) {
		// TODO Auto-generated method stub
		return null;
	}

	public byte[] logon(int userID) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	
	
}
