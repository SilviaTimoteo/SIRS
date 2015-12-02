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
import SIRS.CryptoTools.ConnectionXML;
import SIRS.CryptoTools.DiffieHellman;
import SIRS.WorkStation.App;

public class Login {
	String username;
	String password;
	
	Console input = System.console();
	SecondaryFunctions functions = new SecondaryFunctions();
	ConnectionXML connection = new ConnectionXML();
	Document cDoc = null;
	Server port = null;
	
	public Login(Server p){
		port = p;
	}
	
	
	public boolean login(){
		functions.writeToScreen("\n\n\nLogin\n");
		
		functions.writeToScreen("\nIdUtilizador: ");
		username = input.readLine();
		
		functions.writeToScreen("Palavra-passe: ");
	    password = String.valueOf(input.readPassword());
	    
	    int docId = Integer.parseInt(username); 
	    
		//1- establishing a session key with ServerDB
		DiffieHellman dh = new DiffieHellman(password,username);
		//1.2 sending to the Server
		byte[] result = port.login(docId, dh.sendClientParameters());
		//1.3 generating  session key		
		App.key =dh.receiveServerParameters(result);
		System.out.println("KeyAgreed: " + printBase64Binary(App.key.getEncoded()));
		//1.4 creating challenge
		byte[] result1= port.sendChallenge(docId, dh.sentClientChallenge());
		//check challenge
		byte[] result2 = port.checkChallenge(docId, dh.checkChallenge(result1));
		
		
		try {
			if((new String(result2, "UTF-8").equalsIgnoreCase("authentication successful"))){
				functions.writeToScreen("\nLogin bem sucedido\n");
				return true;
			}
			else{
				functions.writeToScreen("\nIdUtilizador ou palavra-passe errados!\n");
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
