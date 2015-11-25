package SIRS.WorkStation.Services;

import java.io.Console;


public class Login {
	String username;
	String password;
	
	Console input = System.console();
	SecondaryFunctions functions = new SecondaryFunctions();
	
	public boolean login(){
		functions.writeToScreen("\n\n\nLogin\n");
		
		functions.writeToScreen("\nIdUtilizador: ");
		username = input.readLine();
		
		functions.writeToScreen("Palavra-passe: ");
	    password = String.valueOf(input.readPassword());
	       
	    if(username.equals("Patrice") && password.equals("dumdum")){
	    	functions.writeToScreen("\nLogin bem sucedido\n");
	    	return true;
	    }
	    else{
	    	functions.writeToScreen("\nIdUtilizador ou palavra-passe errados!\n");
	    }
	
		return false;
	}	
	

}
