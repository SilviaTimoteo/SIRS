package SIRS.WorkStation.Services;

import java.io.Console;
import java.io.IOException;

import org.jdom2.Document;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

import SIRS.CryptoTools.ConnectionXML;

public class Login {
	String username;
	String password;
	
	Console input = System.console();
	SecondaryFunctions functions = new SecondaryFunctions();
	ConnectionXML connection = new ConnectionXML();
	Document cDoc = null;
	
	public boolean login(){
		functions.writeToScreen("\n\n\nLogin\n");
		
		functions.writeToScreen("\nIdUtilizador: ");
		username = input.readLine();
		
		functions.writeToScreen("Palavra-passe: ");
	    password = String.valueOf(input.readPassword());
	    
	    cDoc = connection.createDoc();
	    cDoc = connection.setId(cDoc, username); 
	    cDoc = connection.setMessage(cDoc, "YaCifradoComChaveSecretaPartilhada");
	    		//isto depois cifrado com a password
	    
	    
//----------IMPRIMIR O DOC CRIADO, APAGAR DEPOIS ESTE CODIGO-------------------------//	    
	    XMLOutputter xmlOutput = new XMLOutputter();

        // display ml
        xmlOutput.setFormat(Format.getPrettyFormat());
        try {
			xmlOutput.output(cDoc, System.out);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
//-----------------------------------------------------------------------------------//
	       
	    if(username.equals("Patrice") && password.equals("dumdum")){
	    	functions.writeToScreen("\nLogin bem sucedido\n");
	    	return true;
	    }
	    else{
	    	functions.writeToScreen("\nIdUtilizador ou palavra-passe errados!\n");
	    }
	
		return false;
	}	
	
	public String getUsername(){
		return username;
	}
	

}
