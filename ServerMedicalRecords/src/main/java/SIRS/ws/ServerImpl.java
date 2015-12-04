package SIRS.ws;
import static javax.xml.bind.DatatypeConverter.parseBase64Binary;
import static javax.xml.bind.DatatypeConverter.printBase64Binary;

import java.security.Key;
import java.text.SimpleDateFormat;

import javax.crypto.spec.SecretKeySpec;
import javax.jws.*;

import org.jdom2.Document;

import SIRS.CryptoTools.CipherFunctions;
import SIRS.CryptoTools.ConnectionXML;
import SIRS.CryptoTools.FunctionsXML;
import SIRS.CryptoTools.RequestsXML;
import SIRS.exceptions.ConnectionCorrupted;
import SIRS.exceptions.DoctorDoesntExist;
import SIRS.exceptions.DoctorNotOfPatient;
import SIRS.exceptions.DoctorSpecialty;
import SIRS.exceptions.EmergencyDoctor;
import SIRS.exceptions.InvalidTimestamp;
import SIRS.exceptions.PatientDoesntExist;
import sirs.ws.*;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.Map;

@WebService(endpointInterface="SIRS.ws.Server")
public class ServerImpl implements Server {
	Key keyServer =ServerMain.keySessionDB;
	ServerDB port = ConnectionServerDB.getPortServerDB();
	Map<String,Key>  mapKeys = new HashMap<String,Key>();
	Map<String,byte[]> mapChallenge = new HashMap<String,byte[]>();
	
	public byte[] login(int userID, byte[] message, byte[] iv) throws DoctorDoesntExist {
		try
		{
			byte[] result = port.generateSessionKeyDoctor(userID, message, iv);//to ServerDB
			Document doc = FunctionsXML.BytesToXML(result);
			ConnectionXML conn = new ConnectionXML();
			String msg = conn.getMessage(doc);//publicParamsDoctor
			Key keyDoctor =new SecretKeySpec(CipherFunctions.decipher(parseBase64Binary(conn.getC1(doc)), keyServer,iv), "AES");
			mapKeys.put(Integer.toString(userID),keyDoctor);
			System.out.println("key-Doctor - ServerMedical: " + printBase64Binary(keyDoctor.getEncoded()));
			Document doc1 =conn.createDoc();
			doc1= conn.setMessage(doc1, msg);
			
			return FunctionsXML.XMLtoBytes(doc1);
		}
		catch(DoctorDoesntExist e){
			throw new DoctorDoesntExist();
		}
	}

	public byte[] getRegistries(int userID, byte[] message, byte[] iv) throws  DoctorDoesntExist, PatientDoesntExist, EmergencyDoctor, InvalidTimestamp {
		try{
			//Decifrar mensagem da wokstation
			byte[] msgDecif = CipherFunctions.decipher(message, mapKeys.get(Integer.toString(userID)),iv);
			//Obter docXML
			Document doc = FunctionsXML.BytesToXML(msgDecif);
			RequestsXML reqXML = new RequestsXML();
			String timestamp = reqXML.getTimestamp(doc);
			//Validar TimeStamp
			ValidateRequests.validTimestamp(timestamp);
			//Update do timeStamp
			SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:SS");
			String now = sdf.format(Calendar.getInstance().getTime());
			doc=reqXML.setTimestamp(doc, now);
			//Cifrar doc com a chave do SERVIDORDB
			byte[] docCiphered =CipherFunctions.cipher(FunctionsXML.XMLtoBytes(doc), keyServer, iv);
			byte[] result=port.getRegistriesDB(docCiphered, iv);
			//Decifra com a chave do ServidorDB e cifra com a chave do Doctor
			byte[] docFromServerDB = CipherFunctions.decipher(result, keyServer, iv);
			byte[] docToWorkstation =CipherFunctions.cipher(docFromServerDB, mapKeys.get(Integer.toString(userID)), iv);
			return docToWorkstation;
		}
		catch(DoctorDoesntExist e){
			throw new DoctorDoesntExist();
		}
		catch(PatientDoesntExist e){
			throw new PatientDoesntExist();
		}
		catch(DoctorNotOfPatient e){
			throw new DoctorNotOfPatient();
		}
		catch(DoctorSpecialty e){
			throw new DoctorSpecialty();
		}
		catch(InvalidTimestamp e){
			throw new DoctorSpecialty();
		}		
	}

	public byte[] getRegistryByDate(int userID, byte[] message, byte[] iv) throws  DoctorDoesntExist, PatientDoesntExist, EmergencyDoctor, InvalidTimestamp {
		try{
			//Decifrar mensagem da wokstation
			byte[] msgDecif = CipherFunctions.decipher(message, mapKeys.get(Integer.toString(userID)), iv);
			//Obter docXML
			Document doc = FunctionsXML.BytesToXML(msgDecif);
			RequestsXML reqXML = new RequestsXML();
			String timestamp = reqXML.getTimestamp(doc);
			//Validar TimeStamp
			ValidateRequests.validTimestamp(timestamp);
			//Update do timeStamp
			SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:SS");
			String now = sdf.format(Calendar.getInstance().getTime());
			doc=reqXML.setTimestamp(doc, now);
			//Cifrar doc com a chave do SERVIDORDB
			byte[] docCiphered =CipherFunctions.cipher(FunctionsXML.XMLtoBytes(doc), keyServer, iv);
			byte[] result=port.getRegistryByDateDB(docCiphered, iv);
			//Decifra com a chave do ServidorDB e cifra com a chave do Doctor
			byte[] docFromServerDB = CipherFunctions.decipher(result, keyServer, iv);
			byte[] docToWorkstation =CipherFunctions.cipher(docFromServerDB, mapKeys.get(Integer.toString(userID)), iv);
			return docToWorkstation;
		}
		catch(DoctorDoesntExist e){
			throw new DoctorDoesntExist();
		}
		catch(PatientDoesntExist e){
			throw new PatientDoesntExist();
		}
		catch(DoctorNotOfPatient e){
			throw new DoctorNotOfPatient();
		}
		catch(DoctorSpecialty e){
			throw new DoctorSpecialty();
		}
		catch(InvalidTimestamp e){
			throw new DoctorSpecialty();
		}
	}

	public byte[] getRegistryBySpeciality(int userID, byte[] message, byte[] iv) throws DoctorDoesntExist, PatientDoesntExist, DoctorNotOfPatient, DoctorSpecialty, InvalidTimestamp {
		try{
			//Decifrar mensagem da wokstation
			byte[] msgDecif = CipherFunctions.decipher(message, mapKeys.get(Integer.toString(userID)), iv);
			//Obter docXML
			Document doc = FunctionsXML.BytesToXML(msgDecif);
			RequestsXML reqXML = new RequestsXML();
			String timestamp = reqXML.getTimestamp(doc);
			//Validar TimeStamp
			ValidateRequests.validTimestamp(timestamp);
			//Update do timeStamp
			SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:SS");
			String now = sdf.format(Calendar.getInstance().getTime());
			doc=reqXML.setTimestamp(doc, now);
			//Cifrar doc com a chave do SERVIDORDB
			byte[] docCiphered =CipherFunctions.cipher(FunctionsXML.XMLtoBytes(doc), keyServer, iv);
			byte[] result=port.getRegistryBySpecialityDB(docCiphered, iv);
			//Decifra com a chave do ServidorDB e cifra com a chave do Doctor
			byte[] docFromServerDB = CipherFunctions.decipher(result, keyServer, iv);
			byte[] docToWorkstation =CipherFunctions.cipher(docFromServerDB, mapKeys.get(Integer.toString(userID)), iv);
			return docToWorkstation;
		}
		catch(DoctorDoesntExist e){
			throw new DoctorDoesntExist();
		}
		catch(PatientDoesntExist e){
			throw new PatientDoesntExist();
		}
		catch(DoctorNotOfPatient e){
			throw new DoctorNotOfPatient();
		}
		catch(DoctorSpecialty e){
			throw new DoctorSpecialty();
		}
		catch(InvalidTimestamp e){
			throw new DoctorSpecialty();
		}
	}

	public byte[] sendChallenge(int userID, byte[] message, byte[] iv) {
		ConnectionXML connXML = new ConnectionXML();
		Document doc = FunctionsXML.BytesToXML(message);
		
		String challenge1= connXML.getC1(doc);
		byte[] challengeServer = CipherFunctions.SecureRandomNumber();
		mapChallenge.put(Integer.toString(userID), challengeServer);	
		System.out.println("\n Doctor <-> ServerMedical __ C2 -> sent: " + printBase64Binary(challengeServer) +"\n");
		
		Document doc1 = connXML.createDoc();
		doc1 = connXML.setC1(doc1,challenge1);
		doc1 = connXML.setC2(doc1,printBase64Binary(CipherFunctions.cipher(challengeServer, mapKeys.get(Integer.toString(userID)), iv)));
		return FunctionsXML.XMLtoBytes(doc1);		
	}

	public byte[] checkChallenge(int userID, byte[] message, byte[] iv) throws ConnectionCorrupted {
		Document doc = FunctionsXML.BytesToXML(message);
		ConnectionXML conn = new ConnectionXML();
		String msg = conn.getMessage(doc);
		System.out.println("msg: " + msg);
		byte[] challengeReceived = CipherFunctions.decipher(parseBase64Binary(conn.getC2(doc)), mapKeys.get(Integer.toString(userID)),iv);
		System.out.println("C2 -> received: " + printBase64Binary(challengeReceived));

		if(msg.equals("Authentication Failed") || !(Arrays.equals(challengeReceived, mapChallenge.get(Integer.toString(userID))))){
			mapKeys.remove(Integer.toString(userID));
			throw new ConnectionCorrupted();			
		}
		mapChallenge.remove(Integer.toString(userID));
		String msgToReturn ="Authentication Successful";
		System.out.println("msgToReturn: " + msgToReturn);
		return msgToReturn.getBytes();	
	}

	public byte[] logout(int userID) {
		mapKeys.remove(Integer.toString(userID));
		String msgToReturn = "Logout Successful";
		return msgToReturn.getBytes() ;
	}

	public byte[] addRegistryReq(int userID, byte[] message, byte[] iv)throws DoctorDoesntExist, PatientDoesntExist,  DoctorNotOfPatient, DoctorSpecialty, InvalidTimestamp {
		try{
			//Decifrar mensagem da wokstation
			byte[] msgDecif = CipherFunctions.decipher(message, mapKeys.get(Integer.toString(userID)),iv);
			//Obter docXML
			Document doc = FunctionsXML.BytesToXML(msgDecif);
			RequestsXML reqXML = new RequestsXML();
			String timestamp = reqXML.getTimestamp(doc);
			//Validar TimeStamp
			ValidateRequests.validTimestamp(timestamp);
			//Update do timeStamp
			SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:SS");
			String now = sdf.format(Calendar.getInstance().getTime());
			doc=reqXML.setTimestamp(doc, now);
			//Cifrar doc com a chave do SERVIDORDB
			byte[] docCiphered =CipherFunctions.cipher(FunctionsXML.XMLtoBytes(doc), keyServer, iv);
			byte[] result=port.addRegistry(docCiphered, iv);
			//Decifra com a chave do ServidorDB e cifra com a chave do Doctor
			byte[] docFromServerDB = CipherFunctions.decipher(result, keyServer, iv);
			byte[] docToWorkstation =CipherFunctions.cipher(docFromServerDB, mapKeys.get(Integer.toString(userID)), iv);
			return docToWorkstation;
		}
		catch(DoctorDoesntExist e){
			throw new DoctorDoesntExist();
		}
		catch(PatientDoesntExist e){
			throw new PatientDoesntExist();
		}
		catch(DoctorNotOfPatient e){
			throw new DoctorNotOfPatient();
		}
		catch(DoctorSpecialty e){
			throw new DoctorSpecialty();
		}
		catch(InvalidTimestamp e){
			throw new DoctorSpecialty();
		}
	}
	
	
	
	
}
