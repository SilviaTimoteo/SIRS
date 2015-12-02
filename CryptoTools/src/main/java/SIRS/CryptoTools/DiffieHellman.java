package SIRS.CryptoTools;
import java.math.BigInteger;
import java.security.AlgorithmParameterGenerator;
import java.security.AlgorithmParameters;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.spec.*;
import java.util.Arrays;

import javax.crypto.*;
import javax.crypto.spec.DHParameterSpec;
import javax.crypto.interfaces.*;

import static javax.xml.bind.DatatypeConverter.parseBase64Binary;
import static javax.xml.bind.DatatypeConverter.printBase64Binary;

import org.jdom2.Document;

public  class DiffieHellman {

	//guarda-se o clientAgreement para depois calcular a chave do cliente apos receber o yb do servidor
	private KeyAgreement clientAgreement= null;
	private Key sharekey =null;
	private PublicKey publicParamsClient = null;
	private String clientID =null;
	private Key keyAgreed = null;
	private byte[] clientChallenge = null;
	
	public DiffieHellman(String password, String clientID){
		this.sharekey=CipherFunctions.generateKeyBYPassword(password);
		this.clientID = clientID;
	}
	
	public KeyAgreement getClientAgreement() {
		return clientAgreement;
	}

	public void setClientAgreement(KeyAgreement clientAgreement, String clientID) {
		this.clientAgreement = clientAgreement;
		this.clientID=clientID;
	}
	
	public Key getKeyAgreed(){
		return this.keyAgreed;
	}
	
	public DiffieHellman(){}
	
	
	/**
	 * Generate parameters of diffie Hellman and compute Yclient
	 * @return Yclient
	 * @throws Exception
	 */
	public PublicKey clientGenerateParams(){
		//generate diffieHellman parameters	
		try{
			AlgorithmParameterGenerator paramGen = AlgorithmParameterGenerator.getInstance("DH");
		    paramGen.init(512);
		    AlgorithmParameters params = paramGen.generateParameters();
		    DHParameterSpec dhSpec = (DHParameterSpec) params.getParameterSpec(DHParameterSpec.class);
		    
		    KeyPairGenerator clientKeyGen = KeyPairGenerator.getInstance("DH");
		    clientKeyGen.initialize(dhSpec);
		    //compute Yclient
		    KeyPair clientPair =clientKeyGen.generateKeyPair();
		    KeyAgreement clientKeyAgree=KeyAgreement.getInstance("DH");
		    clientKeyAgree.init(clientPair.getPrivate());
		    this.clientAgreement =clientKeyAgree;
		    this.publicParamsClient=clientPair.getPublic();
		    return this.publicParamsClient;
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return this.publicParamsClient;
	}
	/**
	 * compute Yserver and generate secret key
	 * @param keyPublicClient
	 * @return Yserver, SecretKey
	 * @throws Exception
	 */
	public Key[] serverGenerateKey(Key keyPublicClient){
		try{
			 KeyPairGenerator serverKeyGen = KeyPairGenerator.getInstance("DH");
			 DHParameterSpec dhSpec =((DHPublicKey)keyPublicClient).getParams();
			 serverKeyGen.initialize(dhSpec, new SecureRandom());
			//compute YServer
			 KeyAgreement serverKeyAgree=KeyAgreement.getInstance("DH");
			 KeyPair serverPair =  serverKeyGen.generateKeyPair();
			 
			 serverKeyAgree.init(serverPair.getPrivate());
			 serverKeyAgree.doPhase(keyPublicClient, true);
			 //compute sharedSecret
			 Key key = CipherFunctions.generateKeyFromBytes(serverKeyAgree.generateSecret());
			 return new Key[] {serverPair.getPublic(),key};
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * Generate secretKey
	 * @param clientAgreement
	 * @param keyPublicServer
	 * @return SecretKey
	 * @throws Exception
	 */
	public Key  clientGenerateKey(Key keyPublicServer){
		 //compute sharedSecret
		try{
			this.clientAgreement.doPhase(keyPublicServer, true);
			return CipherFunctions.generateKeyFromBytes(this.clientAgreement.generateSecret());
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	
	public byte[] sendClientParameters(){
		this.clientGenerateParams();
		byte[] cipherPublic = CipherFunctions.cipher(this.publicParamsClient.getEncoded(), this.sharekey);
		ConnectionXML connXML = new ConnectionXML();
		Document doc = connXML.createDoc();
		doc=connXML.setId(doc, this.clientID);
		doc=connXML.setMessage(doc,printBase64Binary(cipherPublic));
		return FunctionsXML.XMLtoBytes(doc);
	}
	
	public Key receiveServerParameters(byte[] result){
		Document doc  = FunctionsXML.BytesToXML(result);
		byte[] keyBytes= CipherFunctions.decipher(parseBase64Binary(new ConnectionXML().getMessage(doc)),this.sharekey);
		PublicKey key = CipherFunctions.generatePublicKeyFromBytes(keyBytes);
		this.keyAgreed =this.clientGenerateKey(key);
		System.out.println(printBase64Binary(this.keyAgreed.getEncoded()));
		return this.keyAgreed;
	}
	
	public byte[] sentClientChallenge(){
		this.clientChallenge = CipherFunctions.SecureRandomNumber() ;
		System.out.println("C1 -> sent: " + printBase64Binary(this.clientChallenge));
		byte[] challengeCiphered = CipherFunctions.cipher(this.clientChallenge,this.keyAgreed);
		ConnectionXML connXML1 = new ConnectionXML();
		Document doc = connXML1.createDoc();
		doc = connXML1.setC1(doc,printBase64Binary(challengeCiphered));
		return 	FunctionsXML.XMLtoBytes(doc);	
	}
	
	public byte[] checkChallenge(byte[] result){
		Document doc  = FunctionsXML.BytesToXML(result);
		ConnectionXML connXML1 = new ConnectionXML();
		String message = null;
		byte[] c1Received =CipherFunctions.decipher(parseBase64Binary(connXML1.getC1(doc)), this.keyAgreed);
		System.out.println("C1 -> received: " + printBase64Binary(c1Received));
		
		if(Arrays.equals(this.clientChallenge,c1Received)){
			message ="Authentication Successful";
		}
		else{
			message ="Authentication Failed";
		}
		System.out.println("message: " + message);
		Document doc1 = new ConnectionXML().createDoc();
		doc1=connXML1.setC2(doc1, connXML1.getC2(doc));
		doc1=connXML1.setMessage(doc1, message);
		return FunctionsXML.XMLtoBytes(doc1);
	}
}
