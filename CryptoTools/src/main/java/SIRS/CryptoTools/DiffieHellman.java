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

import javax.crypto.*;
import javax.crypto.spec.DHParameterSpec;
import javax.crypto.interfaces.*;
public  class DiffieHellman {

	//guarda-se o clientAgreement para depois calcular a chave do cliente apos receber o yb do servidor
	private KeyAgreement clientAgreement= null;
	
	
	public KeyAgreement getClientAgreement() {
		return clientAgreement;
	}

	public void setClientAgreement(KeyAgreement clientAgreement) {
		this.clientAgreement = clientAgreement;
	}
	
	/**
	 * Generate parameters of diffie Hellman and compute Yclient
	 * @return Yclient
	 * @throws Exception
	 */
	public PublicKey clientGenerateParams() throws Exception{
		//generate diffieHellman parameters
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
	    return clientPair.getPublic();	    	    
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
	public Key  clientGenerateKey(Key keyPublicServer) throws Exception{
		 //compute sharedSecret
		this.clientAgreement.doPhase(keyPublicServer, true);
		return CipherFunctions.generateKeyFromBytes(this.clientAgreement.generateSecret());	
	}
	
}
