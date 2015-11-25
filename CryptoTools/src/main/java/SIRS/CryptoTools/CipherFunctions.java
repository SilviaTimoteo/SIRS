package SIRS.CryptoTools;

import java.security.Key;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;

import javax.crypto.Cipher;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

public class CipherFunctions {
//------------------------Function to symmetricKeys------------------------------------
	
	/**
	 * Cipher the message with cipher key
	 * @param mensagem
	 * @param key
	 * @return cipher text
	 */
	public static byte[] cipher(byte[] mensagem, Key key){
		byte[] cipherBytes = null;
		try{
	        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
			cipher.init(Cipher.ENCRYPT_MODE, key);
			cipherBytes = cipher.doFinal(mensagem);
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return cipherBytes;
	}
	/**
	 * Decipher the message with symmetric key
	 * @param mensagemCifrada
	 * @param key
	 * @return  PlainText
	 */
	public static byte[] decipher(byte[] mensagemCifrada, Key key){
		byte[] newPlainBytes=null;
		try{
			Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
			cipher.init(Cipher.DECRYPT_MODE, key);
			newPlainBytes = cipher.doFinal(mensagemCifrada);
	       
		}
		catch(Exception e){
			e.printStackTrace();
		}
		 return newPlainBytes;
	}
	
//------------------------Function to assymetricsKeys------------------------------------
	/**
	 * Generate RSAKeyPair
	 * @return publicKey, privateKey
	 * @throws NoSuchAlgorithmException
	 */
	
	public static Key[] generateRSAKeys() throws NoSuchAlgorithmException{
		KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
		kpg.initialize(2048);
		KeyPair kp = kpg.genKeyPair();
		Key publicKey = kp.getPublic();
		Key privateKey = kp.getPrivate();
		return  new Key[] {publicKey,privateKey};
	}
	
	/**
	 * Encrypt the plain text using public key
	 * @param text
	 * @param key
	 * @return cipherText
	 */
	public static byte[] encrypt(String text, PublicKey key) {
		byte[] cipherText = null;
		try {
		  // get an RSA cipher object and print the provider
		  final Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
		  // encrypt the plain text using the public key
		      cipher.init(Cipher.ENCRYPT_MODE, key);
		      cipherText = cipher.doFinal(text.getBytes());
		    } catch (Exception e) {
		      e.printStackTrace();
		    }
		    return cipherText;
	}
	
	/**
	 * Decrypt text using private key.
	 * @param text
	 * @param key
	 * @return plain text
	 */
	public static String decrypt(byte[] text, PrivateKey key) {
	    byte[] dectyptedText = null;
	    try {
	      // get an RSA cipher object and print the provider
	      final Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");

	      // decrypt the text using the private key
	      cipher.init(Cipher.DECRYPT_MODE, key);
	      dectyptedText = cipher.doFinal(text);

	    } catch (Exception ex) {
	      ex.printStackTrace();
	    }

	    return new String(dectyptedText);
	  }
}
