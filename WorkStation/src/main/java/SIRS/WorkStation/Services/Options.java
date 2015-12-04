package SIRS.WorkStation.Services;

import java.util.InputMismatchException;
import java.util.Scanner;

import sirs.ws.Server;
import SIRS.WorkStation.App;

public class Options {
	Scanner input = new Scanner(System.in);
	int option;
	SecondaryFunctions functions = new SecondaryFunctions();
	boolean noBack = true;
	String user;
	int docId;
	Server port = null;
	
	public Options(Server p, String username){
		user = username;
		port = p;
		docId = Integer.parseInt(username); 
	}
	
	public boolean showOptions(){ 
		functions.writeToScreen("\n\n[WORKSTATION]\n\n");

		functions.writeToScreen("Escolha uma das opções:\n");
		functions.writeToScreen("1) Pedir registo médico\n");
		functions.writeToScreen("2) Adicionar registo médico\n");
		functions.writeToScreen("0) Terminar sessão\n");
		
		try{
		option = input.nextInt();
		input.nextLine();
		}catch(InputMismatchException e){
			functions.writeToScreen(String.valueOf(option));
			option = -1;
		}
		switch(option){
		 	case 1:
				while(noBack){
					AskMR mr = new AskMR(port, user);
					noBack = mr.showOptions();
				}
				break;
		 	case 2:
				while(noBack){
					AddMR mr = new AddMR(port, user);
					noBack = mr.showOptions();
				}
				break;
		 	case 0:
				port.logout(docId);
		 		App.key = null;
		 		functions.writeToScreen("\n\nTerminando sessão...\n\n");
				try{
		 		App.clearScreen();
				}catch(Exception ignore){}
				
				return false;
			default:
				functions.writeToScreen("\n Opção inválida!\n");


		}
		
	
		return true;
	
	}
	
	

}
