package SIRS.WorkStation.Services;

import static javax.xml.bind.DatatypeConverter.printBase64Binary;

import java.io.Console;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.Key;

import org.jdom2.Document;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

import sirs.ws.Server;
import SIRS.CryptoTools.CipherFunctions;
import SIRS.CryptoTools.ConnectionXML;
import SIRS.CryptoTools.DiffieHellman;
import SIRS.WorkStation.App;
import SIRS.exceptions.DoctorDoesntExist;

public class Login {
	String username;
	String password;
	
	Console input = System.console();
	SecondaryFunctions functions = new SecondaryFunctions();
	ConnectionXML connection = new ConnectionXML();
	Document cDoc = null;
	Server port = null;
	int docId;
	byte[] result2;
	
	public Login(Server p){
		port = p;
	}
	
	
	public boolean login() throws DoctorDoesntExist{
		functions.writeToScreen("\n\n\nLogin\n");
		
		functions.writeToScreen("\nUserId: ");
		username = input.readLine();
		
		functions.writeToScreen("Password: ");
	    password = String.valueOf(input.readPassword());
	    
	    try{
	    	docId = Integer.parseInt(username); 
	    }catch (NumberFormatException E){
	    	System.out.println(new DoctorDoesntExist().getMessage());
	    	return false;
	    }
	    try{
		//1- establishing a session key with ServerDB
		DiffieHellman dh = new DiffieHellman(password,username);
		//1.2 sending to the Server
		byte[] iv0 = CipherFunctions.ivGenerator();
		byte[] result = port.login(docId, dh.sendClientParameters(iv0), iv0);
		//1.3 generating  session key		
		App.key =dh.receiveServerParameters(result, iv0);
		System.out.println("KeyAgreed: " + printBase64Binary(App.key.getEncoded()));
		//1.4 creating challenge
		byte[] iv1 = CipherFunctions.ivGenerator();
		byte[] result1= port.sendChallenge(docId, dh.sentClientChallenge(iv1), iv1);
		//check challenge
		result2 = port.checkChallenge(docId, dh.checkChallenge(result1,iv1), iv1);
	    }catch (Exception e){
	    	System.out.println(e.getMessage());
	    	functions.writeToScreen("\nWrong UserId or password!\n");
	    	return false;
	    }
		
		try {
			if((new String(result2, "UTF-8").equalsIgnoreCase("authentication successful"))){
				functions.writeToScreen("\nSuccessful login!\n");
				return true;
			}
			else{
				functions.writeToScreen("\nWrong UserId or password!\n");
			}
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		return false;
	}	
	
	public String getUsername(){
		return username;
	}
	

}
