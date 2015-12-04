package SIRS.CryptoTools;

import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Arrays;
import java.util.Random;

import static javax.xml.bind.DatatypeConverter.printBase64Binary;

public class CipherFunctions {
//------------------------Function to symmetricKeys------------------------------------
	
	/**
	 * Cipher the message with cipher key
	 * @param mensagem
	 * @param key
	 * @return cipher text
	 */
	public static byte[] cipher(byte[] mensagem, Key key, byte[] iv){
		byte[] output = null;
		
		try{
	        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			cipher.init(Cipher.ENCRYPT_MODE, key, new IvParameterSpec(iv) );
			output=new byte[cipher.getOutputSize(mensagem.length)];
			 int outputLen=cipher.update(mensagem,0,mensagem.length,output,0);
			 cipher.doFinal(output,outputLen);
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return output;
	}
	/**
	 * Decipher the message with symmetric key
	 * @param mensagemCifrada
	 * @param key
	 * @return  PlainText
	 */
	public static byte[] decipher(byte[] mensagemCifrada, Key key, byte[] iv){
		byte[] newPlainBytes=null;
		byte[] cipherBytes = null;
	
		try{
			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			cipher.init(Cipher.DECRYPT_MODE, key, new IvParameterSpec(iv));
			newPlainBytes = cipher.doFinal(mensagemCifrada);
	       
		}
		catch(Exception e){
			e.printStackTrace();
		}
		 return newPlainBytes;
	}
	public static byte[] ivGenerator(){
		byte[] b = new byte[16];
        new Random().nextBytes(b);
        return b;
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
	public static PublicKey generatePublicKeyFromBytes(byte[] key){
		try{
			EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(key);
			KeyFactory keyFactory = KeyFactory.getInstance("DH");
			PublicKey publicKey2 = keyFactory.generatePublic(publicKeySpec);
			return publicKey2;
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	
	public static Key generateKeyBYPassword(String password){	
		SecretKeySpec secretKeySpec = null;
		try{
			MessageDigest sha = MessageDigest.getInstance("SHA-1");
			byte[] key = sha.digest(password.getBytes());
			key = Arrays.copyOf(key, 16); // use only first 128 bit
			secretKeySpec = new SecretKeySpec(key, "AES");
			return secretKeySpec;
		 }
		 catch(Exception e){
			 e.printStackTrace();
		 }
		 return secretKeySpec;
	}
	
	public static Key generateKeyFromBytes(byte[] password){	
		SecretKeySpec secretKeySpec = null;
		try{
			MessageDigest sha = MessageDigest.getInstance("SHA-1");
			byte[] key = sha.digest(password);
			key = Arrays.copyOf(key, 16); // use only first 128 bit
			System.out.println("key:" + printBase64Binary(key));
			secretKeySpec = new SecretKeySpec(key, "AES");
			return secretKeySpec;
		 }
		 catch(Exception e){
			 e.printStackTrace();
		 }
		 return secretKeySpec;
	}
	public static byte[] SecureRandomNumber(){
		try{
			SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
	        final byte array[] = new byte[16];
	        random.nextBytes(array);
	        return array;
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
}
