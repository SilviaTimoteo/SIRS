package SIRS.ws;

import static javax.xml.bind.DatatypeConverter.printBase64Binary;
import static javax.xml.bind.DatatypeConverter.parseBase64Binary;

import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.util.Arrays;

import javax.crypto.spec.SecretKeySpec;
import javax.jws.*;

import org.jdom2.Document;
import org.jdom2.Element;

import SIRS.CryptoTools.*;
import SIRS.DBServer.SQLVerify;
import SIRS.exceptions.*;
@WebService(endpointInterface="SIRS.ws.ServerDB")
public class ServerImplDB implements ServerDB{
	private String passwordServerRequest = "pAw5S3GZ";
	private Key serverS1Key = null;
	private byte[] challengeCreated = null;
	
	public byte[] loginDB(byte[] message) {
		Document doc = FunctionsXML.BytesToXML(message);
		String yServer= new ConnectionXML().getMessage(doc);		
		byte[] msgDecif = CipherFunctions.decipher(parseBase64Binary(yServer), CipherFunctions.generateKeyBYPassword(passwordServerRequest));	
		PublicKey key =CipherFunctions.generatePublicKeyFromBytes(msgDecif);
		Key[] keysServer = new DiffieHellman().serverGenerateKey(key);
		System.out.println(printBase64Binary(keysServer[1].getEncoded()));
		serverS1Key = keysServer[1];
		ConnectionXML connXML = new ConnectionXML();
		Document doc1 = connXML.createDoc();
		doc1 = connXML.setMessage(doc1, printBase64Binary(CipherFunctions.cipher(keysServer[0].getEncoded(), CipherFunctions.generateKeyBYPassword(passwordServerRequest))));
		return FunctionsXML.XMLtoBytes(doc1);
	}

	public byte[] getRegistriesDB(byte[] message) {
		// Decifrar a mensagem com a chave partilhada entre os dois servidores
		byte[] msgDecif = CipherFunctions.decipher(message, serverS1Key);	
		
		// Criar o documento XML a partir dos bytes
		Document doc = FunctionsXML.BytesToXML(msgDecif);
		
		// Tirar do XML os campos necessarios
		RequestsXML reqXML = new RequestsXML();
		String patient = reqXML.getPatient(doc);
		String doctor = reqXML.getDoctor(doc);
		String timestamp = reqXML.getTimestamp(doc);
		
		// Verificar se o medico tem acesso aos registos => POLICIES
		// FALTA TRY CATCH!!!!!!!!!!!!!!!!!!
		String result = SQLVerify.verifyAllReg(patient,doctor,timestamp);
		
		// Criar XML com os registos pedidos
		Document responseXML = reqXML.createDoc();
		reqXML.setEntry(responseXML, result);
		
		// Cifrar o XML
		byte[] responseBytes = CipherFunctions.cipher(FunctionsXML.XMLtoBytes(responseXML), serverS1Key);
		return responseBytes;
	}

	public byte[] getRegistryByDateDB(byte[] message) {
		// Decifrar a mensagem com a chave partilhada entre os dois servidores
		byte[] msgDecif = CipherFunctions.decipher(message, serverS1Key);	
		
		// Criar o documento XML a partir dos bytes
		Document doc = FunctionsXML.BytesToXML(msgDecif);
		
		// Tirar do XML os campos necessarios
		RequestsXML reqXML = new RequestsXML();
		String patient = reqXML.getPatient(doc);
		String doctor = reqXML.getDoctor(doc);
		String timestamp = reqXML.getTimestamp(doc);
		String beforeafter = reqXML.getBeforeAfter(doc);
		String date = reqXML.getDate(doc);
		
		// Verificar se o medico tem acesso aos registos => POLICIES
		// FALTA TRY CATCH!!!!!!!!!!!!!!!!!!
		String result = SQLVerify.verifyRegsByDate(patient, doctor, beforeafter, date, timestamp);
		
		// Criar XML com os registos pedidos
		Document responseXML = reqXML.createDoc();
		reqXML.setEntry(responseXML, result);
		
		// Cifrar o XML
		byte[] responseBytes = CipherFunctions.cipher(FunctionsXML.XMLtoBytes(responseXML), serverS1Key);
		return responseBytes;
	}

	public byte[] getRegistryBySpecialityDB(byte[] message) {
		// Decifrar a mensagem com a chave partilhada entre os dois servidores
		byte[] msgDecif = CipherFunctions.decipher(message, serverS1Key);	
		
		// Criar o documento XML a partir dos bytes
		Document doc = FunctionsXML.BytesToXML(msgDecif);
		
		// Tirar do XML os campos necessarios
		RequestsXML reqXML = new RequestsXML();
		String patient = reqXML.getPatient(doc);
		String doctor = reqXML.getDoctor(doc);
		String timestamp = reqXML.getTimestamp(doc);
		String specialty = reqXML.getSpeciality(doc);
		
		// Verificar se o medico tem acesso aos registos => POLICIES
		// FALTA TRY CATCH!!!!!!!!!!!!!!!!!!
		String result = SQLVerify.verifyRegBySpeciality(patient, doctor, specialty, timestamp);
		
		// Criar XML com os registos pedidos
		Document responseXML = reqXML.createDoc();
		reqXML.setEntry(responseXML, result);
		
		// Cifrar o XML
		byte[] responseBytes = CipherFunctions.cipher(FunctionsXML.XMLtoBytes(responseXML), serverS1Key);
		return responseBytes;
	}
	
	public byte[] addRegistry (byte [] message) {
		byte[] msgDecif = CipherFunctions.decipher(message, serverS1Key);	
		
		// Criar o documento XML a partir dos bytes
		Document doc = FunctionsXML.BytesToXML(msgDecif);
		
		// Tirar do XML os campos necessarios
		RequestsXML reqXML = new RequestsXML();
		String patient = reqXML.getPatient(doc);
		String doctor = reqXML.getDoctor(doc);
		String timestamp = reqXML.getTimestamp(doc);
		String specialty = reqXML.getSpeciality(doc);
		String date = reqXML.getDate(doc);
		String entry = reqXML.getEntry(doc);
		
		
		// Adicionar registo medico
		// FALTA TRY CATCH!!!!!!!!!!!!!!!!!!
		SQLVerify.verifyAddReg(patient, doctor, specialty, entry, date, timestamp);
		
		// Criar XML para dizer se o registo foi adicionado com sucesso
		Document responseXML = reqXML.createDoc();
		reqXML.setEntry(responseXML, "true");
		
		// Cifrar o XML
		byte[] responseBytes = CipherFunctions.cipher(FunctionsXML.XMLtoBytes(responseXML), serverS1Key);
		return responseBytes;

	}

	public byte[] sendChallenge(byte[] message) {
		Document doc = FunctionsXML.BytesToXML(message);
		String challenge= new ConnectionXML().getC1(doc);
		challengeCreated = CipherFunctions.SecureRandomNumber();
		System.out.println("C2 -> sent: " + printBase64Binary(challengeCreated));

		ConnectionXML connXML = new ConnectionXML();
		Document doc1 = connXML.createDoc();
		doc1 = connXML.setC1(doc1,challenge);
		System.out.println("c2 ciphered " + printBase64Binary(CipherFunctions.cipher(challengeCreated, serverS1Key)));
		doc1 = connXML.setC2(doc1,printBase64Binary(CipherFunctions.cipher(challengeCreated, serverS1Key)));
		System.out.println("c2 ciphered XMl " + connXML.getC2(doc1));

		return FunctionsXML.XMLtoBytes(doc1);
	}

	public byte[] checkChallenge(byte[] message)throws ConnectionCorrupted {
		Document doc = FunctionsXML.BytesToXML(message);
		ConnectionXML conn = new ConnectionXML();
		String msg = conn.getMessage(doc);
		System.out.println("msg: " + msg);
		byte[] challengeReceived = CipherFunctions.decipher(parseBase64Binary(conn.getC2(doc)), serverS1Key);
		System.out.println("C2 -> received: " + printBase64Binary(challengeReceived));

		if(msg.equals("Authentication Failed") || !(Arrays.equals(challengeReceived, challengeCreated))){
			serverS1Key= null;
			challengeCreated=null;
			throw new ConnectionCorrupted();			
		}
		return "authentication successful".getBytes();
	}

	public byte[] generateSessionKeyDoctor(int doctorID, byte[] message) {
		ConnectionXML connXML =new ConnectionXML();
		Document doc = FunctionsXML.BytesToXML(message);
		String yDoctor= connXML.getMessage(doc);
	
		String password=SQLVerify.getPassword(Integer.toString(doctorID));
		System.out.printf(password);
		
		byte[] msgDecif = CipherFunctions.decipher(parseBase64Binary(yDoctor), CipherFunctions.generateKeyBYPassword(password));//yDoc decifrado
		PublicKey key =CipherFunctions.generatePublicKeyFromBytes(msgDecif);
		//generating keys
		Key[] keysDoctor = new DiffieHellman().serverGenerateKey(key);
		Key keyDoctor = keysDoctor[1];//chave de comunicao entre doctor e client
		System.out.println("key - ServerDb: " + printBase64Binary(keyDoctor.getEncoded()));
		//generating docXML to ServerMedicalRecords
		
		Document doc1 = connXML.createDoc();
		doc1 = connXML.setMessage(doc1, printBase64Binary(CipherFunctions.cipher(keysDoctor[0].getEncoded(), CipherFunctions.generateKeyBYPassword(password)))); //public params of doctor
		doc1= connXML.setC1(doc1,printBase64Binary(CipherFunctions.cipher(keyDoctor.getEncoded(), serverS1Key)));//chave  de comunicacao entre doctor e server de pedidos
		return FunctionsXML.XMLtoBytes(doc1);
	}

	
}
